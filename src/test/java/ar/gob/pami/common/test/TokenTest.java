package ar.gob.pami.common.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import ar.org.pami.jwt.token.TokenGenerator;
import ar.org.pami.jwt.token.TokenGeneratorBuilder;
import ar.org.pami.jwt.token.TokenValidator;
import ar.org.pami.jwt.token.TokenValidatorBuilder;

public class TokenTest {

  private static final String SECRET = "secret";
  private TokenValidatorBuilder builder;
  private TokenGeneratorBuilder generatorBuilder;
  private TokenValidator validator;
  private TokenGenerator generator;
  private String token;

  @Before
  public void setUp() {
    this.builder = new TokenValidatorBuilder();
    this.generatorBuilder = new TokenGeneratorBuilder();
    this.token = this.getToken();
    this.validator = builder.withSecret(SECRET).build();
    this.generator = generatorBuilder.withSecret(SECRET).withAlgorithm("HS256")
        .build();
  }

  private String getToken() {
    return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9."
        + "eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWV9."
        + "TJVA95OrM7E2cBab30RMHrHDcEfxjoYZgeFONFh7HgQ";
  }

  @Test
  public void desencodeo_el_token_y_me_da_los_datos_del_usuario() {
    Map<String, Object> claim = this.validator.validate(token);
    assertEquals(claim.get("sub"), "1234567890");
    assertEquals(claim.get("name"), "John Doe");
    assertEquals(claim.get("admin"), true);
  }

  @Test
  public void encodeo_los_datos_del_usuario_y_obtengo_el_token() {
    Map<String, Object> claim = new HashMap<String, Object>();
    claim.put("sub", "1234567890");
    claim.put("name", "John Doe");
    claim.put("admin", true);

    String generatedToken = this.generator.generate(claim);

    Map<String, Object> responseClaim = this.validator.validate(generatedToken);
    assertEquals(responseClaim.get("sub"), "1234567890");
    assertEquals(responseClaim.get("name"), "John Doe");
    assertEquals(responseClaim.get("admin"), true);

  }
}

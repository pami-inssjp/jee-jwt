/*
The MIT License (MIT)

Copyright (c) 2015 Instituto Nacional de Servicios Sociales para Jubilados y Pensionados

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package ar.org.pami.jwt.token;

import java.util.Map;

import org.apache.log4j.Logger;

import ar.org.pami.jwt.token.exception.CanNotValidateTokenException;

import com.auth0.jwt.JWTVerifier;

public class TokenValidator {

  public Logger logger = Logger.getLogger(TokenValidator.class);

  private JWTVerifier verifier;
  private String secret;

  public TokenValidator() {
  }

  public JWTVerifier getVerifier() {
    return verifier;
  }

  public void setVerifier(JWTVerifier signer) {
    this.verifier = signer;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getSecret() {
    return this.secret;
  }

  public Map<String, Object> validate(String token) {
    try {
      Map<String, Object> claim = verifier.verify(token);
      logger.info("El Token es valido");
      return claim;
    } catch (Exception e) {
      String message = String.format(
          "Error al intentar de verificar el token: %s", token);
      logger.error(message, e);
      throw new CanNotValidateTokenException(message, e);
    }
  }
}

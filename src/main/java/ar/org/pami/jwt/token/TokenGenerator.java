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

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTSigner.Options;

public class TokenGenerator {

  private Logger logger = Logger.getLogger(TokenGenerator.class);

  private String secret;

  private JWTSigner signer;

  private Options options;

  public String generate(Map<String, Object> claim) {
    String generatedToken = this.getSigner().sign(claim, this.getOptions());

    logger.debug("Generated Token = " + generatedToken);
    return generatedToken;

  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public void setSigner(JWTSigner signer) {
    this.signer = signer;
  }

  public JWTSigner getSigner() {
    return this.signer;
  }

  public Options getOptions() {
    return options;
  }

  public void setOptions(Options options) {
    this.options = options;
  }

}

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
package ar.gob.pami.jwt;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ar.gob.pami.jwt.token.TokenValidator;
import ar.gob.pami.jwt.token.TokenValidatorBuilder;

//@WebFilter(filterName = "jwt-filter")
public class JWTWebFilter implements Filter {

  private Logger logger = Logger.getLogger(JWTWebFilter.class);

  private String secret;

  public void destroy() {

  }

  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    if (logger.isDebugEnabled()) {
      logger.debug("Validando Token");
    }

    TokenValidator validator = new TokenValidatorBuilder().withSecret(secret)
        .build();

    String token = getToken(request);

    Map<String, Object> claim = validator.validate(token);

    this.setClaimToHeader(claim, request);

    chain.doFilter(request, response);
  }

  /**
   * Agrega a los atributos del httpRequest el JWTClaim.<br/>
   * <b>KEY:</b> jwt -> <b>VALUE:</b> Map(String,Object)
   * 
   * 
   * @param claim
   * @param request
   */
  private void setClaimToHeader(Map<String, Object> claim,
      ServletRequest request) {
    HttpServletRequest httpRequest = this.getHTTPRequest(request);
    httpRequest.setAttribute("jwt", claim);
  }

  /**
   * Metodo que revisa el header y extrae el token, revisando previamente que el
   * contenido del header sea correcto.<br/>
   * <b>KEY:</b> Authentication -> <b>VALUE:</b> Bearer [token]
   * 
   * @param request
   * @return
   * @throws ServletException
   */
  private String getToken(ServletRequest request) throws ServletException {
    HttpServletRequest httpRequest = getHTTPRequest(request);
    String token = null;

    String authorizationHeader = httpRequest.getHeader("authorization");

    if (authorizationHeader == null) {
      String message = "Unauthorized: No se encontro el header Authorization";
      logger.error(message);
      throw new ServletException(message);
    }

    String[] parts = authorizationHeader.split(" ");
    if (parts.length != 2) {
      String message = "Unauthorized: El formato correcto del header Authorization es: Bearer [token]";
      logger.error(message);
      throw new ServletException(message);
    }

    String scheme = parts[0];
    String credentials = parts[1];

    Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
    if (pattern.matcher(scheme).matches()) {
      token = credentials;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("Token encontrado = " + token);
    }
    return token;
  }

  private HttpServletRequest getHTTPRequest(ServletRequest request) {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    return httpRequest;
  }

  public void init(FilterConfig config) throws ServletException {
    secret = config.getInitParameter("secret");
  }

}

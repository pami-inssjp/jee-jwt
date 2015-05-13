# JWT

Este proyecto tiene como intención encapsular las implementeciones de JWT creando generadores y validadores.
Además incluye un JWTWebFilter para utilizar como Filtro en el [web.xml](#/web.xml)

## ¿Cómo utilizar este proyecto?

### Generador

Para utilizar el generador simplemente hay que utilizar el builder que va a generar el _token_. Para ello realizamos lo siguiente:

```java

//Armo el token generator en base al builder
TokenGenerator generator = new TokenGeneratorBuilder().withSecret("passwordNinja").withAlgorithm("HS256")
        .build();

//Creo el claim que voy a enviar al cliente
Map<String, Object> claim = new HashMap<String, Object>();
claim.put("sub", "1234567890");
claim.put("name", "John Doe");
claim.put("admin", true);

//Genero el token en base al claim.
String generatedToken = generator.generate(claim);

```

Observen que el builder recibe dos parametros:

* **secret:** Password de firma del token (tiene que ser la misma con la que luego voy a validar el token)
* **algorithm**  Algoritmo utilizado para la firma del token.

#### Protocolos

* HS256

### Validador

Para validar la firma debemos hacer el proceso inverso a la generación:

```java
String token = //obtener el token de algun lado;

//Armo el validador
TokenValidator validator = new TokenValidatorBuilder().withSecret("passwordNinja").build();

//Obtengo el claim. En caso de algun error va a lanzar una excepcion
Map<String,Object> claim = validator.validate(token);

```

#### Excepciones

`ar.org.pami.jwt.token.exception.CanNotValidateTokenException`

#### Protocolos

* HS256

### Web Filter

Además existe un web filter para poder utilizarlo directamente en una aplicación web. Simplemente deberemos agregar las lineas que se encuentran debajo en el **web.xml** y tendrá el validador integrado.

El **web.xml** recibe un parametro que es el **secret** con el password que valida la firma.


#### Respuesta del Web Filter

El WebFilter guarda dentro de los atributos del **request** el _claim_ obtenido.

```java
// el request es del tipo ServletRequest
HttpServletRequest httpRequest = (HttpServletRequest) request;
httpRequest.setAttribute("jwt", claim);
```

#### web.xml

```xml
	<filter>
		<filter-name>jwt-filter</filter-name>
		<filter-class>ar.org.pami.jwt.JWTWebFilter</filter-class>
		<init-param>
			<param-name>secret</param-name>
			<param-value>secret</param-value>
		</init-param>
	</filter>

	<filter-mapping>
		<filter-name>jwt-filter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```


## License

Mirar el archivo LICENSE para aprender los derechos y limitaciones de la licencia (MIT).

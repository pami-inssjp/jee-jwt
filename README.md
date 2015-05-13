# JWT

## ¿Cómo utilizar este proyecto?


## web.xml

```xml
	<filter>
		<filter-name>jwt-filter</filter-name>
		<filter-class>ar.gob.pami.jwt.JWTWebFilter</filter-class>
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

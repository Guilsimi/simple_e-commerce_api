### Config Server Configurations

Create a new `private` github repository and include these files:

</br>

**`ec-eureka-server-dev.properties`**

```bash
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

</br>

**`ec-gateway-spring-cloud-dev.properties`**

```bash
server.port=8765

eureka.client.service-url.defaultZone=http://ec-eureka-server:8761/eureka

spring.main.allow-bean-definition-overriding=true
spring.main.web-application-type=reactive

spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://ec-oauth:8765/ec-oauth/oauth2/jwks
```

</br>

**`ec-oauth-dev.properties`**

```bash
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}

eureka.client.service-url.defaultZone=http://ec-eureka-server:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

server.forward-headers-strategy=framework

ec.auth.provider-uri=http://ec-oauth:8765/ec-oauth

ec.auth.jks.keypass= > Your key password
ec.auth.jks.storepass= > Your store password
ec.auth.jks.alias= > Your alias name
ec.auth.jks.path= > keystore/YourKeyStoreName.jks

my.client.id= > Create new client id
my.client.secret= > Create new client secret
```

</br>

**`ec-cart-dev.properties`**

```bash
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
eureka.instance.lease-renewal-interval-in-seconds=30

eureka.client.service-url.defaultZone=http://ec-eureka-server:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.registry-fetch-interval-seconds=5

spring.datasource.url=jdbc:postgresql://ec-cart-pg17:5432/db_ec_cart
spring.datasource.username= > Your postgres username
spring.datasource.password= > Your postgres password

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

</br>

**`ec-orders-dev.properties`**

```bash
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
eureka.instance.lease-renewal-interval-in-seconds=30

eureka.client.service-url.defaultZone=http://ec-eureka-server:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.registry-fetch-interval-seconds=5

spring.datasource.url=jdbc:postgresql://ec-orders-pg17:5432/db_ec_orders
spring.datasource.username= > Your postgres username
spring.datasource.password= > Your postgres password

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

</br>

_Currently, the payment microservice uses the OpenPix API to generate payments, so you need to create a new API/Plugin in the OpenPix application. If you don’t have an account, create one._

> [OpenPix]("https://openpix.com.br/")

</br>

**`ec-payment-dev.properties`**

```bash
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
eureka.instance.lease-renewal-interval-in-seconds=30

eureka.client.service-url.defaultZone=http://ec-eureka-server:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.registry-fetch-interval-seconds=5

my.app.id= > Your OpenPix plugin token

spring.datasource.url=jdbc:postgresql://ec-payment-pg17:5432/db_ec_payment
spring.datasource.username= > Your postgres username
spring.datasource.password= > Your postgres password

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

</br>

**`ec-products-dev.properties`**

```bash
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}

eureka.client.service-url.defaultZone=http://ec-eureka-server:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.instance.lease-renewal-interval-in-seconds=30
eureka.client.registry-fetch-interval-seconds=5

spring.datasource.url=jdbc:postgresql://ec-products-pg17:5432/db_ec_products
spring.datasource.username= > Your postgres username
spring.datasource.password= > Your postgres password

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

</br>

**`ec-user-dev.properties`**

```bash
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
eureka.instance.lease-renewal-interval-in-seconds=30

eureka.client.service-url.defaultZone=http://ec-eureka-server:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.registry-fetch-interval-seconds=5

spring.datasource.url=jdbc:postgresql://ec-user-pg17:5432/db_ec_user
spring.datasource.username= > Your postgres username
spring.datasource.password= > Your postgres password

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

</br>

**`ec-wishlist-dev.properties`**

```bash
server.port=${PORT:0}

eureka.instance.instance-id=${spring.application.name}:${spring.application.instance_id:${random.value}}
eureka.instance.lease-renewal-interval-in-seconds=30

eureka.client.service-url.defaultZone=http://ec-eureka-server:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.registry-fetch-interval-seconds=5

spring.datasource.url=jdbc:postgresql://ec-wishlist-pg17:5432/db_ec_wishlist
spring.datasource.username= > Your postgres username
spring.datasource.password= > Your postgres password

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

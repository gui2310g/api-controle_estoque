<h1>Api de Controle de Estoque</h1>

Este é uma api desenvolvido com Spring Boot e Docker do projeto full-stack de controle de estoque, a api consiste em gerenciar o estoque de produtos com um estoque atual e minimo com sku gerado automaticamente, podendo fazer movimentações de entrada ou saida, recebendo alertas automáticos sobre níveis baixos de estoque, categorização de produtos e registro de fornecedores e controlando pedidos de compra e seus respectivos itens.

## Ferramentas Utilizadas

#### Java
#### Spring Boot
#### Spring Security
#### Spring Boot JPA
#### Swagger 
#### JWT (JSON Web Token)
#### Junit
#### Mockito
#### Maven
#### Redis
#### Lombok
#### MapStruct
#### PostgreSQL


## Requisitos

#### Java 21
#### PostgresSQL
#### Spring Boot 3.4.4

## Configurações 
1 - Clona o projeto

```bash
https://github.com/gui2310g/api-controle_estoque.git  
```

2 - Para executar o projeto, execute:

```bash
mvn spring-boot:run 
```

**Obs: certifique de configurar seu application.properties e banco de dados (no caso o projeto usa PostgreSQL)**

3 - Para acessar o Swagger, acesse:

```bash
http://localhost:8080/swagger-ui/index.html
```

## Contribuir

Caso queria contribuir para o projeto, abre uma pull request apontando erros ou features.
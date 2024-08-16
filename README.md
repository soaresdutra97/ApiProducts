![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

This API is a part of an BFF (Back-End for Front-End) system made by me using Java Spring Boot and more.

Technologies: Spring Boot, JPA, Feign Client, PostgreSQL, Swagger, Spring Security with JWT, Kafka and JUnit + Mockito tests.

This BFF Contains: 
A communication with 2 others APis (BFF API, Payment API) using Feign Client.
This API has a Product CRUD.

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Authentication](#authentication)
- [Database](#database)

- ## Installation

1. Clone all necessary repositories to run the project:

BFF API - This contains a communication with API Products and Payment API - It runs in port 8484

```bash
git clone https://github.com/soaresdutra97/loja-virtual-bff.git
```

Products API - This contains the CRUD of Products - It runs in port 8383

```bash
git clone https://github.com/soaresdutra97/ApiProducts.git
```

Payment API - This contains to BFF a logic that returns True or False for payment. - It runs in port 8282

```bash
git clone https://github.com/soaresdutra97/ApiPayment.git
```


2. Install dependencies with Gradle

3. Install [PostgresSQL](https://www.postgresql.org/) and create a database called postgres

4.  Open the application-dev.yaml and change the spring: datasource: url: to jdbc:postgresql://YOUR_DATABASE_IP:5432/postgres

5. Run all Apis (BFF, Products and Payment)

6. Install [Kafka](https://kafka.apache.org/downloads) and [Zookeeper](https://zookeeper.apache.org/) and run both

7. Create a Kafka Cluster and a Topic called atualizaCarrinhoeWishList


## Usage

1. After starts, the API BFF will be accessible at http://localhost:8484/swagger-ui/index.html#/

## API Endpoints

Users:
```markdown
PUT /usuarios/atualizar - Update User by ID

GET /usuarios/usr - Retrieve info of authenticated JWT User

GET /usuarios/buscaremail - Find User by email (ADMIN access required)

GET /usuarios/all - Returns all Users (ADMIN access required)

DELETE /usuarios/deletar - Delete user by email (ADMIN access required)
```

Products:
```markdown
PUT /produtos - Update Product by ID (only admin can put)

POST /produtos - Register a new product (ADMIN access required).

DELETE /produtos - Delete Product by ID (ADMIN access required).

GET /produtos/produtos - Returns all Products

GET /produtos/search - Find Product by ID

GET /produtos/exists - Returns boolean if Product exists

```

Wishlist:
```markdown
POST /wishlist/adicionar - Register a new product to the Wishlist

GET /wishlist/listar - Returns all Products in the Wishlist

DELETE /wishlist/remover - Delete a Product in the Wishlist by User ID and Product ID
```

Payments:
```markdown
POST /pagamentos/compra - Returns a Boolean if the payment is approved or not.
If you send a credit card number "numeroCartao": "string" ending with 8080 it will returns true, else false.
```

Cart:
```markdown
POST /carrinho/adicionar - Register a new product to the Cart

GET /carrinho/listar - Returns all Products in the Cart

DELETE /carrinho/remover - Delete a Product in the Cart by User ID and Product ID
```

Authentication
```markdown
POST /auth/register - Register a new user into the App.

Atention: The API will create a User with USER role by default, to access protected endpoints as an ADMIN user, you should create a new "Register" and go to the Database (With Dbeaver, for example) and change Role to 0 if you want a ADMIN user.

POST /auth/login - Login into the App returns a JWT Token, and you have to put it in "Authorize" section
to permit you make http requests. 
```

## Authentication
The API uses Spring Security for authentication control. The following roles are available:

```
USER -> Standard user role for logged-in users.
ADMIN -> Admin role for managing partners (registering new partners).
```

To access protected endpoints as an ADMIN user, you should create a new "Register" and go to the Database (With Dbeaver, for example) and change Role to 0 if you want a ADMIN user.

## Database
The project utilizes [PostgresSQL](https://www.postgresql.org/) as the database.

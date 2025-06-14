[![CircleCI](https://circleci.com/gh/piotr-grosicki/project-jdp-2505-01.svg?style=svg)](https://app.circleci.com/pipelines/github/piotr-grosicki/project-jdp-2505-01?branch=main)
 [![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

# E-Commerce API

Comprehensive, production-ready RESTful API for an online store, built with Spring Boot and Java. Provides endpoints for managing products, product groups, shopping carts, orders, and users.

---

## Table of Contents

* [Features](#features)
* [Tech Stack](#tech-stack)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Gradle Build Configuration](#gradle-build-configuration)
* [Configuration](#configuration)
* [Running the Application](#running-the-application)
* [API Documentation](#api-documentation)
    * [Products](#products)
    * [Groups](#groups)
    * [Carts](#carts)
    * [Orders](#orders)
    * [Users](#users)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)

---

## Features

* **CRUD** operations for Products, Groups, Orders, Users
* **Shopping Cart** management
* **Order creation** from Cart
* **User registration** and **access control**
* **Time-limited keys** for secure operations

---

## Tech Stack

* Java 17
* Spring Boot 3.2.2
* Spring MVC / Spring Data JPA
* Hibernate
* Lombok
* MySQL / H2 (in-memory for tests)
* Gradle

---

## Prerequisites

* Java 17 or higher
* Gradle 7+
* MySQL (or compatible) for production; H2 for testing
* Git

---

## Installation

1. **Clone the repository**

   ```bash
   git clone https://github.com/your-org/your-repo.git
   cd your-repo
   ```

2. **Configure database**
   Edit `src/main/resources/application.yml` with your database credentials.

3. **Build the project**

   ```bash
   ./gradlew clean build
   ```

---

## Gradle Build Configuration

```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.2'
    id 'io.spring.dependency-management' version '1.1.4'
}

group = 'org.example'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = '17'
}

repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'

    implementation 'org.projectlombok:lombok:1.18.38'
    annotationProcessor 'org.projectlombok:lombok:1.18.38'
    runtimeOnly 'com.mysql:mysql-connector-j'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testRuntimeOnly 'com.h2database:h2'
    testAnnotationProcessor 'org.projectlombok:lombok:1.18.38'
}

tasks.named('test') {
    useJUnitPlatform()
}
```

---

## Configuration

```yaml
spring:
  datasource:
    url: 'jdbc:mysql://localhost:3306/kodilla_project?allowPublicKeyRetrieval=true&useSSL=false'
    username: kodilla_user
    password: kodilla_password
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
```

---

## Running the Application

```bash
./gradlew bootRun
```

The API will be accessible at `http://localhost:8080/api`.

---

## API Documentation

Base URL: `http://localhost:8080/api`

### Products

| Method | Endpoint                       | Description               |
|--------|--------------------------------|---------------------------|
| GET    | `/api/v1/products`             | List all products         |
| GET    | `/api/v1/products/{productId}` | Retrieve a single product |
| POST   | `/api/v1/products`             | Create a new product      |
| PUT    | `/api/v1/products`             | Update product data       |
| DELETE | `/api/v1/products/{productId}` | Delete a product          |

#### Example: Create Product

```http
POST /api/v1/products
Content-Type: application/json

{
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse",
  "price": 29.99,
  "groupId": 2
}
```

#### Response (201 Created)

```json
{
  "id": 101,
  "name": "Wireless Mouse",
  "description": "Ergonomic wireless mouse",
  "price": 29.99,
  "groupId": 2,
  "createdAt": "2025-06-11T14:25:43Z"
}
```

---

### Groups

| Method | Endpoint                   | Description             |
|--------|----------------------------|-------------------------|
| GET    | `/api/v1/groups`           | List all product groups |
| POST   | `/api/v1/groups`           | Create a new group      |
| GET    | `/api/v1/groups/{groupId}` | Retrieve a group by ID  |
| PUT    | `/api/v1/groups/{groupId}` | Update a specific group |

#### Example: Create Group

```http
POST /api/v1/groups
Content-Type: application/json

{
  "name": "Accessories",
  "description": "Gadgets and peripherals"
}
```

#### Response (200 OK)

```json
{
  "id": 5,
  "name": "Accessories",
  "description": "Gadgets and peripherals",
  "createdAt": "2025-06-11T14:30:00Z"
}
```

---

### Carts

| Method | Endpoint                                      | Description                             |
|--------|-----------------------------------------------|-----------------------------------------|
| POST   | `/api/v1/carts`                               | Create an empty cart                    |
| GET    | `/api/v1/carts/{cartId}`                      | Retrieve items in the cart              |
| POST   | `/api/v1/carts/{cartId}/products/{productId}` | Add a product to the cart               |
| DELETE | `/api/v1/carts/{cartId}/products/{productId}` | Remove a specific product from the cart |
| POST   | `/api/v1/carts/{cartId}/orders`               | Convert cart to an order                |

#### Example: Create Empty Cart

```http
POST /api/v1/carts
```

#### Response (200 OK)

```json
{
  "id": 42,
  "products": [],
  "createdAt": "2025-06-11T14:45:00Z"
}
```

---

### Orders

| Method | Endpoint                         | Description                           |
|--------|----------------------------------|---------------------------------------|
| GET    | `/api/v1/orders`                 | List all orders                       |
| GET    | `/api/v1/orders/status/{status}` | List orders by status (e.g., CREATED) |
| GET    | `/api/v1/orders/{orderId}`       | Retrieve an order by ID               |
| POST   | `/api/v1/orders`                 | Create a new order directly           |
| PUT    | `/api/v1/orders`                 | Update an existing order              |
| DELETE | `/api/v1/orders/{orderId}`       | Delete an order                       |

#### Example: Get Orders by Status

```http
GET /api/v1/orders/status/CREATED
```

#### Response (200 OK)

```json
[
  { "id": 201, "cartId": 42, "status": "CREATED", "createdAt": "2025-06-11T15:00:00Z" },
  { "id": 202, "cartId": 43, "status": "CREATED", "createdAt": "2025-06-11T15:05:00Z" }
]
```

---

### Users

| Method | Endpoint                       | Description                                           |
|--------|--------------------------------|-------------------------------------------------------|
| GET    | `/api/v1/users`                | List all users                                        |
| GET    | `/api/v1/users/{userId}`       | Retrieve a user by ID                                 |
| POST   | `/api/v1/users`                | Create a new user                                     |
| PUT    | `/api/v1/users`                | Update an existing user                               |
| DELETE | `/api/v1/users/{userId}`       | Delete a user                                         |
| PUT    | `/api/v1/users/block/{userId}` | Block a user                                          |
| POST   | `/api/v1/users/{userId}/keys`  | Generate a one-hour key for the user after validation |

#### Example: Generate Temporary Key

```http
POST /api/v1/users/77/keys
```

#### Response (200 OK)

```json
{
  "id": 77,
  "username": "jdoe",
  "email": "jdoe@example.com",
  "key": "a1b2c3d4e5f6g7h8",
  "keyExpiresAt": "2025-06-11T16:15:00Z"
}
```

---

## Contributing

Contributions are welcome! Please read our [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on code style, pull requests, and issue reporting.

---

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## Contact

Project Maintainer – [Your Name](mailto:your.email@example.com)
GitHub: [your-org/your-repo](https://github.com/your-org/your-repo)

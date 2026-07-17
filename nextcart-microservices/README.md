# NEXTCART E-Commerce Microservices

This repository contains the microservices architecture for the NEXTCART e-commerce platform, migrated from a monolithic Spring Boot application.

## Microservices Overview
- **API Gateway**: Spring Cloud Gateway, handles routing, JWT validation and routing
- **Authentication Service**: User authentication, JWT, roles management
- **Product Service**: Products, categories, brands, inventory management
- **Order Service**: Cart, wishlist, orders, coupons, returns management
- **CMS Service**: Homepage sections, banners, menus, theme settings
- **Notification Service**: Email, SMS, order notifications
- **Analytics Service**: Sales, revenue, reports, KPIs

## Technologies Used
- Java 17
- Spring Boot 3.4.x
- Spring Security
- JPA/Hibernate
- MySQL
- Flyway
- Docker & Docker Compose

## Getting Started

### Prerequisites
- Java 17 or later
- Maven
- Docker and Docker Compose

### Building the Project
1. Clone the repository
2. Navigate to the `nextcart-microservices` directory
3. Run `mvn clean install` to build all services

### Running with Docker
1. Run `docker-compose up -d`
2. Access the API gateway at http://localhost:8080

## Contributing
Feel free to contribute!

# Multi-Branch Point of Sale (POS) System Backend

A robust, enterprise-grade **Multi-Branch Point of Sale (POS) & Analytics Backend System** built with **Java 21**, **Spring Boot 3.5.4**, **Spring Security (JWT)**, **Spring Data JPA / MySQL**, and integrated payment gateways (**Stripe** & **Razorpay**).

The system supports multi-tenant store management, branch-level operations, cashier shift reports with cash drawer balancing, product cataloging, inventory stock alerts, customer loyalty tracking, refund workflows, and real-time store-wide / branch-wide analytics dashboards.

---

## Table of Contents
1. [Key Features](#key-features)
2. [Technology Stack](#technology-stack)
3. [Prerequisites](#prerequisites)
4. [Database Setup (Docker MySQL)](#database-setup-docker-mysql)
5. [Application Configuration (`application.properties`)](#application-configuration-applicationproperties)
6. [How to Build & Run](#how-to-build--run)
7. [OpenAPI 3 & Swagger Documentation](#openapi-3--swagger-documentation)
8. [API Endpoints Overview](#api-endpoints-overview)
9. [Frontend Configuration & Integration Guide](#frontend-configuration--integration-guide)

---

## Key Features

- **Multi-Tenant & Role-Based Access Control (RBAC)**:
  - `ROLE_ADMIN` (Super Admin): Manage platform stores, view global platform analytics, and moderate store status.
  - `ROLE_STORE_ADMIN`: Manage store profile, multi-branch store operations, inventory, employees, and high-level analytics.
  - `ROLE_STORE_MANAGER` / `ROLE_BRANCH_MANAGER`: Manage branch inventory, staff, sales, and daily performance metrics.
  - `ROLE_BRANCH_CASHIER`: Open/close shift reports, process orders, handle customer checkouts, apply discounts, and issue refunds.
- **Cashier Shift Reports & Drawer Balancing**:
  - Track shift start/end times, opening cash, closing cash, total sales, and detect discrepancies automatically.
- **Inventory & Low Stock Alerts**:
  - Product cataloging with SKU, barcode, category, and real-time inventory tracking per branch with threshold alerts.
- **Payment Processing**:
  - Multiple payment options including Cash, Card, UPI, Stripe, and Razorpay.
- **Store & Branch Analytics**:
  - Real-time aggregation of sales trends, daily/monthly revenue graphs, top-selling products, cashier rankings, and refund spike alerts.

---

## Technology Stack

- **Java**: 21 (LTS)
- **Framework**: Spring Boot 3.5.4
- **Security**: Spring Security 6 with JWT (JSON Web Token) authentication
- **Database**: MySQL 8.0 with Spring Data JPA & Hibernate ORM
- **API Documentation**: Springdoc OpenAPI 3.0 (`swagger-ui.html`)
- **Build Tool**: Apache Maven (`mvnw`)
- **Payment SDKs**: Stripe Java SDK (v33.1.0), Razorpay Java SDK (v1.4.9)
- **Utilities**: Lombok, Jackson

---

## Prerequisites

- **Java Development Kit (JDK 21)** installed and added to your `PATH`.
- **Docker Desktop** installed (to run MySQL database).
- **Maven** (optional, wrapper `./mvnw` is included in the project).

---

## Database Setup (Docker MySQL)

To start the MySQL database instance required by the backend, run the following Docker command in your terminal:

```bash
docker run --detach \
  --env MYSQL_ROOT_PASSWORD=dummypassword \
  --env MYSQL_USER=user \
  --env MYSQL_PASSWORD=dummypassword \
  --env MYSQL_DATABASE=pos-system-database \
  --name mysql \
  --publish 3306:3306 \
  mysql:8-oracle
```

### Database Credentials Summary:
- **Host**: `localhost`
- **Port**: `3306`
- **Database Name**: `pos-system-database`
- **Username**: `user`
- **Password**: `dummypassword`
- **Root Password**: `dummypassword`

---

## Application Configuration (`application.properties`)

The application configuration file is located at `src/main/resources/application.properties`. You can override variables via environment variables or direct property edits.

```properties
# Spring Application Name
spring.application.name=POS-System

# Server Port
server.port=8080

# JPA & Hibernate Settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Datasource Configuration
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/pos-system-database
spring.datasource.username=user
spring.datasource.password=dummypassword

# JWT Security Configuration
jwt.secret=my-super-secret-key-for-jwt-signing-123456789
jwt.expiration=86400000

# OpenAPI / Swagger UI Configuration (Springdoc)
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs
```

### Environment Variable Overrides (Optional):
- `MYSQL_HOST`: Set database host IP or container name (default: `localhost`).

---

## How to Build & Run

### 1. Compile the Project
```bash
./mvnw clean test-compile
```

### 2. Run the Application
```bash
./mvnw spring-boot:run
```
Alternatively, build a JAR file and execute it:
```bash
./mvnw clean package -DskipTests
java -jar target/POS-System-0.0.1-SNAPSHOT.jar
```

The server will start at `http://localhost:8080`.

---

## OpenAPI 3 & Swagger Documentation

Interactive OpenAPI 3 documentation is automatically available when the application is running:

- **Swagger UI Interactive Interface**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Raw OpenAPI JSON Spec**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)
- **Standalone OpenAPI YAML File**: Located in the root directory as [`openapi.yaml`](openapi.yaml).

> **Authentication in Swagger UI**:
> 1. Call `POST /auth/signin` or `POST /auth/signup` to get a JWT token.
> 2. Click the **Authorize 🔓** button in Swagger UI.
> 3. Enter your token in the format: `Bearer <your_jwt_token>`.

---

## API Endpoints Overview

| Module | HTTP Method | Endpoint Path | Description | Input Parameters / Body | Output / Response |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Auth** | `POST` | `/auth/signup` | Register new user | `UserDTO` JSON body | `AuthResponse` (JWT + Role) |
| **Auth** | `POST` | `/auth/signin` | Login user | `UserDTO` JSON body | `AuthResponse` (JWT + Role) |
| **User** | `GET` | `/api/user/profile` | Get current user profile | `Authorization` Bearer header | `UserDTO` |
| **User** | `GET` | `/api/user/{id}` | Get user by ID | Path variable `id`, `Authorization` header | `UserDTO` |
| **Store** | `POST` | `/api/store` | Create store | `StoreDTO` JSON body, `Authorization` header | `StoreDTO` |
| **Store** | `GET` | `/api/store` | Get all stores | None | `List<StoreDTO>` |
| **Store** | `GET` | `/api/store/{id}` | Get store by ID | Path variable `id` | `StoreDTO` |
| **Store** | `GET` | `/api/store/admin` | Get store for logged-in admin | None | `StoreDTO` |
| **Store** | `PUT` | `/api/store/{id}/moderate` | Moderate store status | Path `id`, Query `status` | `StoreDTO` |
| **Branch** | `POST` | `/api/branch` | Create branch | `BranchDTO` JSON body | `BranchDTO` |
| **Branch** | `GET` | `/api/branch/store/{storeId}` | Get store branches | Path variable `storeId` | `List<BranchDTO>` |
| **Branch** | `PUT` | `/api/branch/{id}` | Update branch | Path `id`, `BranchDTO` JSON body | `BranchDTO` |
| **Branch** | `DELETE` | `/api/branch/{id}` | Delete branch | Path `id` | `ApiResposne` |
| **Employee** | `POST` | `/api/employee/store/{storeId}` | Create store employee | Path `storeId`, `UserDTO` JSON body | `UserDTO` |
| **Employee** | `POST` | `/api/employee/branch/{branchId}` | Create branch employee | Path `branchId`, `UserDTO` JSON body | `UserDTO` |
| **Category** | `POST` | `/api/categories` | Create category | `CategoryDTO` JSON body | `CategoryDTO` |
| **Category** | `GET` | `/api/categories/store/{storeId}` | Get store categories | Path variable `storeId` | `List<CategoryDTO>` |
| **Product** | `POST` | `/api/products` | Create product catalog item | `ProductDTO` JSON body, `Authorization` header | `ProductDTO` |
| **Product** | `GET` | `/api/products/store/{storeId}` | Get store products | Path variable `storeId` | `List<ProductDTO>` |
| **Product** | `GET` | `/api/products/store/{storeId}/search` | Search store products | Path `storeId`, Query `keyword`, `Authorization` | `List<ProductDTO>` |
| **Inventory** | `POST` | `/api/inventories` | Initialize inventory | `InventoryDTO` JSON body | `InventoryDTO` |
| **Inventory** | `GET` | `/api/inventories/branch/{branchId}` | Get branch inventory | Path variable `branchId` | `List<InventoryDTO>` |
| **Customer** | `POST` | `/api/customers` | Register customer | `CustomerCreateDTO` JSON body | `Customer` |
| **Customer** | `GET` | `/api/customers/search` | Search customers | Query `keyword`, Pagination `Pageable` | `Page<Customer>` |
| **Order** | `POST` | `/api/orders` | Checkout / Create order | `OrderDTO` JSON body | `OrderDTO` |
| **Order** | `GET` | `/api/orders/branch/{branchId}` | Get branch orders | Path `branchId`, Query filters (customer, cashier, status) | `List<OrderDTO>` |
| **Refund** | `POST` | `/api/refunds` | Process refund | `RefundDTO` JSON body | `RefundDTO` |
| **Refund** | `GET` | `/api/refunds/branch/{branchId}` | Get branch refunds | Path variable `branchId` | `List<RefundDTO>` |
| **Shift** | `POST` | `/api/shift-reports/start` | Start cashier shift | None | `ShiftReportDTO` |
| **Shift** | `PATCH` | `/api/shift-reports/end` | Close cashier shift | None | `ShiftReportDTO` |
| **Shift** | `GET` | `/api/shift-reports/current` | Get shift progress | None | `ShiftReportDTO` |
| **Branch Analytics** | `GET` | `/api/branch-analytics/daily-sales` | Branch daily sales chart | Query `branchId`, Query `days` | `List<DailySalesDTO>` |
| **Branch Analytics** | `GET` | `/api/branch-analytics/top-products` | Top products breakdown | Query `branchId` | `List<ProductPerformanceDTO>` |
| **Store Analytics** | `GET` | `/api/store-analytics/{storeAdminId}/overview` | Multi-branch overview | Path variable `storeAdminId` | `StoreOverviewDTO` |
| **Store Analytics** | `GET` | `/api/store-analytics/{storeAdminId}/alert` | Store operational alerts | Path variable `storeAdminId` | `StoreAlertDTO` |
| **Super Admin** | `GET` | `/api/super-admin/dashboard/summary` | Platform dashboard stats | None | `DashboardSummaryDTO` |

---

## Frontend Configuration & Integration Guide

*This section provides complete setup guidelines and placeholders for your frontend client (React, Next.js, Vue, Angular, etc.).*

### 1. Backend Server Base URL
By default, the backend runs on:
```text
http://localhost:8080
```

### 2. CORS Configuration
CORS is pre-configured in [`SecurityConfig.java`](src/main/java/com/sknoor07/pos_system/security_configuration/SecurityConfig.java) for common frontend development ports:
- `http://localhost:3000` (React default)
- `http://localhost:5173` (Vite default)

If your frontend runs on a different port (e.g. `4200` for Angular or `3000` for Next.js), add your origin in `SecurityConfig.java`:
```java
configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173", "http://localhost:4200"));
```

### 3. JWT Authentication Header Setup
All requests to `/api/**` endpoints require an HTTP `Authorization` header with a valid JWT token:
```text
Authorization: Bearer <your_jwt_token_here>
```

#### Example Axios Configuration (React / Vue / Next.js)
```javascript
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080',
});

// Interceptor to inject JWT token automatically
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export default api;
```

### 4. Recommended Frontend Environment Variables (`.env`)
```env
# Frontend API Configuration
VITE_API_BASE_URL=http://localhost:8080
VITE_SWAGGER_URL=http://localhost:8080/swagger-ui.html

# Payment Gateway Public Keys (Placeholders)
VITE_STRIPE_PUBLIC_KEY=pk_test_placeholder_key
VITE_RAZORPAY_KEY_ID=rzp_test_placeholder_key
```

### 5. API Binding & Code Generation
Because OpenAPI 3 spec is included in [`openapi.yaml`](openapi.yaml), you can automatically generate TypeScript API client code using tools like `openapi-generator-cli` or `orval`:
```bash
npx @openapitools/openapi-generator-cli generate \
  -i openapi.yaml \
  -g typescript-axios \
  -o src/api-client
```

---

## License

Distributed under the Apache 2.0 License. See `LICENSE` for more information.

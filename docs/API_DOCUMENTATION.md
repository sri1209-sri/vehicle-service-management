# Vehi-Serve: API Specifications & Team Contribution Guide

Welcome to the official developer documentation for the **Vehicle Service Management System (Vehi-Serve)**. This guide outlines the project's backend API specifications, security framework, local documentation generation instructions, and the collaborative contribution matrix of the development team.

---

## 👥 Project Team & Contribution Matrix

This project was built collaboratively by a team of two developers. The responsibilities were divided equally between backend, frontend, testing, and DevOps components to ensure symmetric, high-quality contributions:

### 1. Backend Java Packages (Javadoc `@author` annotations)
* **Devadarshini M**
  * `config/` (Security Configurations & OpenApi mappings)
  * `controller/` (REST endpoints and payload mapping boundaries)
  * `dto/` (Data Transfer Objects & validation limits)
* **Sridevi Srikumar**
  * `service/` (Core business logic implementations)
  * `repository/` (Spring Data JPA interfaces & database persistence)
  * `util/` (JSON tokens, seeding logic, response templates)
  * `exception/` (Global handlers & custom exception definitions)
* **Both Developers (Co-authored)**
  * `entity/` (Database mappings, relationships, and schema layouts)
  * Main application entry (`VehicleServiceManagementApplication.java`)

### 2. Frontend HTML Dashboard & Templates (Header Comments)
* **Devadarshini M**
  * `index.html` (Landing page / layout entry)
  * `dashboard.html` (Revenue analytics & charts)
  * `invoices.html` (Billing & invoice listings)
  * `services.html` (Service catalog interface)
  * `static/js/app.js` (Thymeleaf dynamic API requests & ChartJS handlers)
  * `static/css/style.css` (Design, theme styling, layout classes)
* **Sridevi Srikumar**
  * `login.html` (Security authentication page)
  * `layout.html` (Modular navigation headers, footers & imports)
  * `customers.html` (Customer registration, grid profiles & validators)
  * `vehicles.html` (Customer vehicle registration grids)
  * `booking.html` (Service booking requests & multi-select logic)
  * `servicehistory.html` (Service completion status logs)

### 3. DevOps, CI/CD, & Deployments (Header Comments)
* **Devadarshini M**
  * `Dockerfile` (Multi-stage compilation)
  * `docker-compose.yml` (Local application & MySQL orchestration)
  * `k8s/configmap.yaml` (Database host environments)
  * `k8s/mysql-deployment.yaml` (Database deployment, persistent volume configuration)
  * `k8s/mysql-service.yaml` (Database cluster IP access services)
* **Sridevi Srikumar**
  * `k8s/secrets.yaml` (Base64 credential security configs)
  * `k8s/pms-deployment.yaml` (Spring Boot container replicas, probes & variables)
  * `k8s/pms-service.yaml` (Exposed application endpoint services)
* **Both Developers (Co-authored)**
  * `Jenkinsfile` (Cross-platform CI/CD automation pipeline build script)
  * `VehicleServiceManagement.postman_collection.json` (API payload test suites)

### 4. Code Quality & Test Suites (Javadoc `@author` annotations)
* **Devadarshini M**
  * `BookingServiceImplTest.java` (Mock validations for booking logic)
  * `CustomerServiceImplTest.java` (Mock validations for customer logic)
* **Sridevi Srikumar**
  * `CustomerControllerTest.java` (MockMVC controller security validations)
* **Both Developers (Co-authored)**
  * `CustomerDeletionIntegrationTest.java` (Complete integration test suite)

---

## 🔒 Security & Authentication (JWT Flow)

All REST APIs located under `/api/**` are protected statelessly using **JSON Web Tokens (JWT)**. 

### JWT Flow Steps:
1. **Authentication request**: Call `POST /api/auth/login` containing the administrator credentials.
2. **Token generation**: The server checks the database (passwords are encrypted using BCrypt) and, if valid, returns a signed JWT.
3. **Authorized calls**: Include the token in the header of all subsequent API calls:
   `Authorization: Bearer <your_jwt_token>`

---

## 📮 API Endpoints Specifications

### 1. Authentication Controller (`/api/auth`)
* **`POST /api/auth/login`**
  * **Description**: Exchanges valid credentials for a stateless JWT token.
  * **Request Body**:
    ```json
    {
      "username": "admin",
      "password": "admin123"
    }
    ```
  * **Response (200 OK)**:
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9..."
    }
    ```

### 2. Customer Controller (`/api/customer`)
* **`POST /api/customer/create`**
  * **Description**: Registers a new customer profile.
  * **Request Body**:
    ```json
    {
      "name": "Jane Doe",
      "email": "jane.doe@example.com",
      "phone": "9876543210"
    }
    ```
  * **Response (200 OK)**: Saved `Customer` object.
* **`GET /api/customer/all`**
  * **Description**: Retrieves a list of all registered customer profiles.
* **`GET /api/customer/{id}`**
  * **Description**: Retrieves details for a specific customer.
* **`PUT /api/customer/update/{id}`**
  * **Description**: Updates profile details of an existing customer.
* **`DELETE /api/customer/delete/{id}`**
  * **Description**: Deletes a customer and their records.

### 3. Vehicle Controller (`/api/vehicle`)
* **`POST /api/vehicle/create`**
  * **Description**: Registers a new vehicle under a customer.
  * **Request Body**:
    ```json
    {
      "make": "Toyota",
      "model": "Camry",
      "year": 2022,
      "licensePlate": "DL1CA1234",
      "customerId": 1
    }
    ```
* **`GET /api/vehicle/all`**
  * **Description**: Retrieves all registered vehicles.
* **`GET /api/vehicle/{id}`**
  * **Description**: Retrieves details of a specific vehicle.
* **`DELETE /api/vehicle/{id}`**
  * **Description**: Removes a vehicle record.

### 4. Service Catalog Controller (`/api/service`)
* **`POST /api/service/create`**
  * **Description**: Adds a service offering to the active catalog list.
  * **Request Body**:
    ```json
    {
      "serviceName": "Wheel Alignment",
      "price": 750.00
    }
    ```
* **`GET /api/service/all`**
  * **Description**: Lists all active catalog service types and prices.
* **`PUT /api/service/update/{id}`**
  * **Description**: Updates catalog details.

### 5. Booking Controller (`/api/booking`)
* **`POST /api/booking/create`**
  * **Description**: Creates a new booking with support for *multiple services*.
  * **Request Body**:
    ```json
    {
      "vehicleId": 1,
      "bookingDate": "2026-06-30",
      "serviceIds": [1, 2]
    }
    ```
* **`GET /api/booking/all`**
  * **Description**: Retrieves all service booking orders.
* **`GET /api/booking/{id}`**
  * **Description**: Retrieves details of a specific booking.

### 6. Service History / Completion Controller (`/api/history`)
* **`POST /api/history/complete`**
  * **Description**: Marks a booking as complete and files a status report.
  * **Request Body**:
    ```json
    {
      "bookingId": 1,
      "serviceDate": "2026-06-23",
      "serviceDetails": "Engine oil replaced, tires rotated and aligned."
    }
    ```
* **`GET /api/history/all`**
  * **Description**: Retrieves all completed historical service logs.

### 7. Invoice Controller (`/api/invoice`)
* **`GET /api/invoice/booking/{bookingId}`**
  * **Description**: Generates or retrieves the consolidated invoice for a booking, automatically aggregating the cost of all booking services.
  * **Response (200 OK)**:
    ```json
    {
      "id": 1,
      "invoiceNumber": "INV-17182947192",
      "totalAmount": 1250.00,
      "invoiceDate": "2026-06-23",
      "bookingId": 1,
      "services": "Engine Oil Change, Wheel Alignment"
    }
    ```

---

## 📖 Accessing Generated Documentation

### 1. Interactive Swagger UI
* Start the Spring Boot application.
* Open your browser and navigate to:
  [http://localhost:8086/swagger-ui/index.html](http://localhost:8086/swagger-ui/index.html)
* Use the **Authorize** lock button in the top-right to authorize with your JWT token.

### 2. Compiling Local JavaDocs HTML
To generate clean, navigable HTML documentation for all packages:
1. Open a terminal in the project root directory.
2. Run:
   ```cmd
   .\mvnw.cmd javadoc:javadoc
   ```
3. Once compilation finishes, navigate to:
   `target/site/apidocs/`
4. Double-click **`index.html`** to browse package structures, developer `@author` headers, parameters, and classes in a web portal format.

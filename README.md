# Vehicle Service Management System (Vehi-Serve)
### Capstone Project Documentation

This is a comprehensive, production-ready Spring Boot web application designed for a **Vehicle Service Management System**. It manages the complete vehicle service workflow, including Customer Registration, Vehicle Registration, Service Booking, Completion logs, Invoice generation, and secure billing.

---

## 🛠️ Technology Stack
* **Backend**: Spring Boot 3.x (Java 17), Spring Security, Hibernate JPA.
* **Frontend**: HTML5, Vanilla CSS, JS, Thymeleaf.
* **Database**: MySQL 8.0.
* **Monitoring**: Spring Boot Actuator, Micrometer Prometheus.
* **DevOps & CI/CD**: Docker, Docker Compose, Kubernetes (K8s), Jenkins.
* **Testing**: JUnit 5, Mockito, Spring Security Test, MockMVC.

---

## 🔒 Security & Validation Details
We have implemented robust security and validation layers to protect data integrity and secure endpoints:
* **Spring Security & Session Authentication**: Secures all Thymeleaf dashboard views and JSON REST API calls. 
* **Password Encryption**: All password entries (including the administrator) are encrypted using the **BCrypt** algorithm.
* **Input Validation**: Enforces payload checks using Jakarta Validation Constraints (e.g. `@NotBlank`, `@Email`, `@Pattern`, `@NotNull`, `@Min`, `@Max`) at Controller boundaries.
* **Exception Handling**: Global exception interceptor captures validation failures and returns clear structured JSON error logs (HTTP 400).
* **Seeded Administrator Account**:
  - **Username**: `admin`
  - **Password**: `admin123`
  - **Email**: `admin@vehiserve.com`
  *(Note: Password is auto-seeded and encrypted on first startup if the `admin` table is empty).*

---

## 🧪 Testing Suite (JUnit 5 & Mockito)
The codebase includes unit and integration tests located under `src/test/java`:
1. **`CustomerServiceImplTest.java`**: Validates registration logic, mock mappings, and duplication checks.
2. **`BookingServiceImplTest.java`**: Tests mock creations and state transitions (e.g., BOOKED -> IN_PROGRESS -> COMPLETED).
3. **`CustomerControllerTest.java`**: WebMvc mock tests validating authentication access (unauthenticated redirects) and input validation responses.

Run tests using:
```bash
./mvnw test
```

---

## 🐳 Dockerization & Local Run (Docker Compose)
A multi-stage `Dockerfile` is provided in the project root to compile the code and serve the application on a minimal JRE container.

### Step 1: Spin up App and MySQL Database
Run the following command in the project root to build and run the services:
```bash
docker-compose up --build -d
```

### Step 2: Access the Application
* **Thymeleaf Dashboard UI**: [http://localhost:8086](http://localhost:8086)
* **Default Login**: `admin` / `admin123`
* **Swagger/OpenAPI Documentation**: [http://localhost:8086/swagger-ui/index.html](http://localhost:8086/swagger-ui/index.html)
* **Prometheus Metrics Actuator**: [http://localhost:8086/actuator/prometheus](http://localhost:8086/actuator/prometheus)

---

## ☸️ Kubernetes (Minikube) Deployment
Kubernetes manifests are organized under the `k8s/` directory.

### Step 1: Deploy Configurations & Secrets
Deploy the config map (containing database URLs) and secrets (containing base64 encoded db passwords):
```bash
kubectl apply -f k8s/config-secrets.yaml
```

### Step 2: Deploy MySQL Database
```bash
kubectl apply -f k8s/mysql.yaml
```
Verify that the database pods are running and ready:
```bash
kubectl get pods -l tier=database
```

### Step 3: Build & Deploy Spring Boot Application
Point your terminal to Minikube's Docker daemon so it can locate your local build:
```bash
# For Windows PowerShell
minikube docker-env | Invoke-Expression

# For Linux/macOS
eval $(minikube -p minikube docker-env)
```
Build the local docker image:
```bash
docker build -t vehiserve-app:latest .
```
Deploy the app deployment and service:
```bash
kubectl apply -f k8s/app.yaml
```

### Step 4: Access the Application in Minikube
Expose the app service to access it locally:
```bash
minikube service vehiserve-app-service --url
```
Or forward the port directly:
```bash
kubectl port-forward svc/vehiserve-app-service 8086:8086
```

---

## 🛠️ Jenkins CI/CD Pipeline
The declarative `Jenkinsfile` in the root workspace automates:
1. **Checkout**: Fetches repository changes.
2. **Build**: Compiles and jars package utilizing Maven wrapper.
3. **Test**: Runs the Mockito/JUnit 5 test suite and records test report results.
4. **Docker Build & Tag**: Packages the Docker image.
5. **Docker Push**: Pushes the compiled image to your repository.
6. **Kubernetes Rollout**: Automatically deploys manifests and restarts pods to perform a rolling update.

---

## 📮 API Testing (Postman Collection)
The project includes a ready-to-import Postman Collection file in the root directory:
* **Filename**: `VehicleServiceManagement.postman_collection.json`
* **Features Included**:
  - Administrative login session handler (captures session cookies).
  - Complete REST CRUD collection folders (Customers, Vehicles, Catalog, Bookings, Completed Logs, Invoices).
  - Pre-defined payload validation test cases.

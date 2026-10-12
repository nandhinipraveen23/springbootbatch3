# Employee Management Microservices (Spring Boot 3)

Five Maven modules: Eureka Server, Employee Service, Department Service, Notification Service, API Gateway.

## Stack and ports
- Java 17; Spring Boot 3.5.5; Spring Cloud 2025.0.0; JJWT 0.12.6
- Eureka: 8761; Gateway: 8080; Employee: 8081; Department: 8082; Notification: 8083
- MySQL schemas: `employee_db`, `department_db`, `notification_db` (auto-created by JDBC URL if MySQL user has privileges)

## Before running
1. Install JDK 17+, Maven 3.9+, MySQL 8+.
2. Create a MySQL user or set environment variables `DB_USERNAME` and `DB_PASSWORD`. Defaults are `root` / `change_me`; change these for your machine.
3. Set a strong JWT secret of at least 32 UTF-8 bytes, identically for Employee Service and Gateway. Example PowerShell for the current terminal:
   ```powershell
   $env:JWT_SECRET="replace-with-a-long-random-secret-at-least-32-bytes"
   $env:DB_USERNAME="root"
   $env:DB_PASSWORD="your_mysql_password"
   ```
4. From this root folder run `mvn clean package`.

## Start order
Open five terminals in this folder and run:
```powershell
mvn -pl eureka-server spring-boot:run
mvn -pl department-service spring-boot:run
mvn -pl notification-service spring-boot:run
mvn -pl employee-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```
Check `http://localhost:8761` and wait until the three services and gateway register. Internal services use ports 8081-8083, so in production restrict them to the private network/firewall and expose only port 8080.

## Postman test sequence
All normal API requests go through `http://localhost:8080`. Use JSON content type.

### 1. Bootstrap the first user (one time, local development only)
Because all routes except login require a token, create the first employee directly against the private Employee Service before using the Gateway:
`POST http://localhost:8081/employees`
```json
{"name":"Asha Kumar","email":"asha@example.com","password":"Passw0rd!"}
```
Do this only on a trusted local machine; never expose port 8081 publicly. Password is stored as a BCrypt hash and is never returned in the response.

### 2. Log in through Gateway
`POST http://localhost:8080/api/auth/login`
```json
{"email":"asha@example.com","password":"Passw0rd!"}
```
Copy the `token` from the response. In Postman, set Authorization type **Bearer Token** and paste it. The token expires after one hour.

### 3. Create a department (requires JWT)
`POST http://localhost:8080/api/departments`
```json
{"departmentName":"Engineering","description":"Software development"}
```
Save the returned `departmentId`.

### 4. Test employee APIs (requires JWT)
- `GET http://localhost:8080/api/employees`
- `GET http://localhost:8080/api/employees/1`
- `PUT http://localhost:8080/api/employees/1` with `{"name":"Asha Kumar","email":"asha@example.com","password":"Passw0rd!","departmentId":1}` (replace `1` with the actual IDs)
- `POST http://localhost:8080/api/employees` with `{"name":"Bala Kumar","email":"bala@example.com","password":"Passw0rd!","departmentId":1}`
- `DELETE http://localhost:8080/api/employees/2` (use the actual employee ID)

### 5. Test other services (requires JWT)
- `GET http://localhost:8080/api/departments`
- `GET http://localhost:8080/api/notifications`
- `GET http://localhost:8080/api/notifications/recipient/asha@example.com`
- `POST http://localhost:8080/api/notifications` with `{"recipient":"asha@example.com","message":"Welcome!","type":"GENERAL"}`

Creating or deleting an employee attempts a REST call to Notification Service. Employee responses call Department Service through Eureka using a load-balanced `RestTemplate`. If Department Service is unavailable, the employee response shows `Department service unavailable` rather than failing the employee request.

## Design notes
- Gateway validates signed JWT and expiry; `/api/auth/login` is the only public route.
- `StripPrefix=1` removes `/api` before forwarding. For example `/api/employees/1` reaches Employee Service as `/employees/1`.
- Employee Service and Gateway must use the same `JWT_SECRET`.
- Internal service endpoints do not implement their own JWT filter because this requested design trusts the private network. Restrict direct access to ports 8081-8083 with firewall/security-group rules in deployments.
- Notification REST call is best-effort so an unavailable notification service does not roll back the employee operation. This is not a distributed transaction.
- The sample uses `ddl-auto=update` for learning. Use Flyway/Liquibase migrations in production.

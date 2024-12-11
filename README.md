# TrainBookingApp-Backend

# 🚄 **Train Booking System**

This is a **Train Booking System** built using **Java**, **Spring Boot**, **MySQL**, and **Spring Security with JWT Tokens**. The system allows users to register, log in, book train tickets, check seat availability, and handle administrative operations like adding and deleting trains. It includes concurrency handling to ensure race conditions are avoided during seat booking and manages overlapping and non-overlapping train routes.

---

##  **Key Features**

- **Secure Admin Operations**: Admin endpoints are protected with a secure **API key** to prevent unauthorized access.  
- **Secure Booking with JWT Tokens**: Users must pass the **JWT token** (received on login) to access booking endpoints.  
- **Concurrency Handling**: Ensures no race conditions during seat booking.  
- **Overlapping & Non-Overlapping Train Logic**: Efficiently handles overlapping or non-overlapping seat availability logic.
- **Exception Handling**: Comprehensive exception handling for invalid input, unauthorized access, resource not found, and other edge cases.

---

## **APIs**

Here is the description of the available APIs:

1. **User Registration**:  `POST /api/users/register`: Register a new user.

2. **User Login**:   `POST /api/users/login`: Authenticate a user and return a JWT token.

3. **Book Tickets**:  `POST /api/trains/{trainId}/book`: Book tickets between a source and a destination station using the train's route. **Requires Authorization Token.**

4. **Book Tickets by Station Name**: `GET /api/trains/{trainId}/bookByName`: Book tickets by providing source and destination station names. **Requires Authorization Token.**

5. **Check Availability**:  `GET /api/trains/availability`: Check seat availability for trains between specific source and destination stations.

6. **Admin - Add Train**:  `POST /api/admin/addTrain`: Admin can add new train details (requires `x-api-key` authentication).

7. **Admin - Delete All Trains**:  `DELETE /api/admin/deleteTrain`: Admin can remove all train and route data (requires `x-api-key` authentication).

8. **View All Bookings**:  `GET /api/trains/showAllBookings`: View all bookings in the system.

9. **User-Specific Booking Data**:  `GET /api/trains/showUserBooking`: Fetch bookings for a specific user. **Requires Authorization Token.**

---

## ⚙️ **Setup Instructions**

### Minimum Requirements
- Java 21  
- Maven  
- MySQL database  

---

### Steps to Run the Project
1. Clone the repository:  
   ```bash
   git clone https://github.com/adtiio/RailwayBookingApp-Workindia.git
2. Set up the local port, admin.api.key and MySQL database and configure connection credentials in application.properties.
    ```properties
        server.port=8080
        spring.datasource.url=jdbc:mysql://localhost:3306/<db_name>
        spring.datasource.username=<username>
        spring.datasource.password=<password>
        spring.jpa.hibernate.ddl-auto=update
        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
        spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
        spring.jpa.show-sql=true

        admin.api.key=adityaadityaaditya
3. Build the application:  
   ```bash
   mvn clean install
4. Start the server:  
   ```bash
   mvn spring-boot:run

--

## 🎥 Demonstration

Below is a description of each API endpoint and example of usage:

---
## 1️  User Registration
**Endpoint:**  
`POST /api/users/register`  

**Description:**   Registers a new user with email, password, and role (`ROLE_USER` for normal users, `ROLE_ADMIN` for admin users).  

  ```json
            {
              "username": "Aditya",
              "email": "adi@example.com",
              "password": "password",
              "role": "ROLE_USER"
            }
```
          
![image](https://github.com/user-attachments/assets/c74a0fac-8b3d-4c3f-81ae-b85beefce7fa)


## 2  User Login 
**Endpoint:**  
`POST /api/users/login`  

**Description:**  
 Logs in a user using their email and password, returning a JWT token. This token must be used for authentication in other API endpoints by including it in the Authorization header as Bearer <jwt-token>.

   ```json
            {
              "email": "adi@example.com",
              "password": "password"
            }
   ```
          
![image](https://github.com/user-attachments/assets/c68efc6b-0184-44b4-9d0d-984377e09358)


## 3  View All Trains 
**Endpoint:**  
`GET /api/trains`  

**Description:**  
 After adding JWT token you can fetch a list of all available trains in the system (if any trains are added).

  ```
     GET /api/trains
     Authorization: Bearer <jwt-token>
  ```
          
![Screenshot 2024-12-07 134800](https://github.com/user-attachments/assets/2c2d708e-d00b-41c0-92a8-87f5dadf2326)


## 4 Admin - Add Train
**Endpoint:**  
`POST /api/admin/addTrain`  

**Description:**  
 Admin-only endpoint for adding a new train. Access requires a valid API key and JWT token.
   Authentication Required:
     JWT Token in` Authorization -> Bearer Token.`
     API Key in header: `x-api-key: adityaadityaaditya.`

  ```json
            {
              "trainName": "Superfast Express",
              "totalTickets": 100,
              "routes": [
                {
                  "stationNumber": 1,
                  "stationName": "City A"
                },
                {
                  "stationNumber": 2,
                  "stationName": "City B"
                },
                {
                  "stationNumber": 3,
                  "stationName": "City C"
                },
                {
                  "stationNumber": 4,
                  "stationName": "City D"
                },
                {
                  "stationNumber": 5,
                  "stationName": "City E"
                }
              ]
            }

  ```
          
![Screenshot 2024-12-07 134636](https://github.com/user-attachments/assets/b54b62cb-9c7c-4411-bcf1-10c0a5759c78)

## 5 Admin - Delete All Trains and Routes 
**Endpoint:**  
`GET /api/admin/deleteTrain`  

**Description:**  
 Deletes all trains and routes from the database. Access requires a valid API key and JWT token.

  ```
         GET /api/admin/deleteTrain
        Header: x-api-key: adityaadityaaditya
  ```

## 6 Book Tickets Using Station Index 
**Endpoint:**  
`POST /api/trains/{trainId}/book`  

**Description:**  
 Allows users to book tickets by providing source, destination, and authentication token.
  Authentication Required:
  JWT Token in `Authorization -> Bearer Token.`

  ```
    POST /api/trains/1/book?src=1&dest=3
  ```
![image](https://github.com/user-attachments/assets/90588657-8e63-42e5-bc28-89c69151d643)
![image](https://github.com/user-attachments/assets/8a7a3c4b-1831-4fb2-9277-9d8b2e4d654b)
![Screenshot 2024-12-07 134314](https://github.com/user-attachments/assets/e610beec-3295-4596-b1f4-b7c30fde5e35)
![Screenshot 2024-12-07 134327](https://github.com/user-attachments/assets/3aad17a2-c948-4ae3-a5d7-e9f1e69a8789)


## 7  Book Tickets Using Station Name 
**Endpoint:**  
`POST /api/trains/{trainId}/bookByName`  

**Description:**  
 Allows users to book tickets by providing source, destination, and authentication token.
  Authentication Required:
  JWT Token in `Authorization -> Bearer Token.`

  ```
    POST /api/trains/1/bookByName?source=City A&destination=City C
  ```
![image](https://github.com/user-attachments/assets/9c74fccf-76eb-483f-a7c8-e876736d09d1)

## 8  Check Availability 
**Endpoint:**  
`GET /api/trains/availability`  

**Description:**  
 Allows users to check available seats between source and destination stations across trains.
  Authentication Required:
  JWT Token in `Authorization -> Bearer Token.`

  ```
    GET /api/trains/availability?source=StationA&destination=StationB
  ```
![image](https://github.com/user-attachments/assets/a0d49199-cdf0-41c8-8f25-45502713a2f2)

## 9  Check Total Bookings  
**Endpoint:**  
`GET /api/trains/showAllBookings`  

**Description:**  
 Allows users to check bookings.
  Authentication Required:
  JWT Token in `Authorization -> Bearer Token.`

  ```
    GET /api/trains/showAllBookings
  ```
![Screenshot 2024-12-07 134927](https://github.com/user-attachments/assets/b70ae388-a802-4309-a718-0d165f6eca16)


## 10  Check Bookings for Particular User
**Endpoint:**  
`GET /api/trains/showUserBooking`  

**Description:**  
 Allows users to check bookings for particular user.
  Authentication Required:
  JWT Token in `Authorization -> Bearer Token.`

  ```
    GET /api/trains/showUserBooking?username=Aditya
  ```

![Screenshot 2024-12-07 135219](https://github.com/user-attachments/assets/f21cd0ea-1175-446d-8b21-32756be02ef6)

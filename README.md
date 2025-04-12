## User Service - Metro Transportation System

The User Service is a core microservice in the metro transportation system that manages user accounts, metro cards, and travel history through a comprehensive REST API. It handles user registration and profile management with email/mobile validation, enables metro card operations including purchase, balance management, and activation status, while maintaining complete travel histories with station details, fare amount, and journey status tracking. The service maintains relationships between users and their metro cards (one-to-many) and travel records (one-to-many), ensuring data integrity. Built with Spring Boot and JPA/Hibernate, it provides the foundational identity and transaction management for the transportation ecosystem, supporting fare deductions, penalty applications, and travel analytics while enforcing business rules like single-active-card-per-user and journey status workflows.

### Contributors

* [@Sonupatel](https://github.com/Sonupatel15)
* [@Harsha](https://github.com/harsha188-codes)

### DB Schema

**user table**

![user table](https://github.com/user-attachments/assets/a9ddb266-3a06-4c4a-acd2-af98d1efa21b)

**metro-card table**

![metro card table](https://github.com/user-attachments/assets/cfee2454-2fdf-4f9f-b0d4-0f0cb800c0b6)

**travel history table**

![travel history table](https://github.com/user-attachments/assets/9887c94b-4258-4d6f-b979-b9f54d67b9a5)

### User Related Actions

#### Creating a user:

**Create User**

* **Method:** POST
* **Endpoint:** /api/users
* **Description:** Creates a new user account
* **Body:**

    ```json
    {
      "name": "Sonupatel",
      "email": "19358sonupatel@gmail.com",
      "mobile": "8861220135",
      "password": "password123",
      "isActive": true,
      "address": "Bengaluru",
      "dob": "1990-01-01"
    }
    ```

* **Response:**

    ![Create User Response](https://github.com/user-attachments/assets/614164d9-0544-4998-b333-1b974068ba7e)

#### Retrieve User Details

**Retrieve User Details**

* **Method:** GET
* **Endpoint:** /api/users/{userId}
* **Description:** Fetches user details by ID
* **Response:**

    ![Retrieve User Details Response](https://github.com/user-attachments/assets/f6448faf-7e0c-4e54-b22e-073840ad2a66)

#### Update User Information

**Update User Information**

* **Method:** PUT
* **Endpoint:** /api/users/{userId}
* **Description:** Updates existing user data
* **Body:**

    ```json
    {
      "name": "Sonupatel",
      "email": "19358sonupatel@gmail.com",
      "mobile": "8861220135",
      "isActive": true,
      "address": "Bengaluru",
      "dob": "1990-01-01",
      "password": "updated"
    }
    ```

* **Response:**

    ![Update User Information Response](https://github.com/user-attachments/assets/808ceb42-4560-4a43-af56-09334da962b6)

#### Delete User Account

**Delete User Account**

* **Method:** DELETE
* **Endpoint:** /api/users/{userId}
* **Description:** Removes user account permanently
* **Response:**

    ![Delete User Account Response](https://github.com/user-attachments/assets/ca95e839-e229-44f4-9a28-5dc93ec6be22)

### Metro Card Related Actions

#### Purchase Metro Card

**Purchase Metro Card**

* **Method:** POST
* **Endpoint:** /api/metro-cards/buy
* **Description:** Purchases a new metro card for a user
* **Body:**

    ```json
    {
      "userId": "3320debf-15a4-4327-81e6-62753969fe39",
      "initialBalance": 500.00
    }
    ```

* **Response:**

    ![Purchase Metro Card Response](https://github.com/user-attachments/assets/a3dca57e-68ee-4ae6-a5eb-b40bd258cae0)

#### Retrieve Metro Card by ID

**Retrieve Metro Card by ID**

* **Method:** GET
* **Endpoint:** /api/metro-cards/{cardId}
* **Description:** Fetches metro card details by card ID
* **Response:**

    ![Retrieve Metro Card by ID Response](https://github.com/user-attachments/assets/8cfd60b9-830b-4304-bc0c-deaaf5c85521)

#### Retrieve Metro Card by User ID

**Retrieve Metro Card by User ID**

* **Method:** GET
* **Endpoint:** /api/metro-cards/user/{userId}
* **Description:** Gets the active metro card for a specific user
* **Response:**

    ![Retrieve Metro Card by User ID Response](https://github.com/user-attachments/assets/690509db-3287-4dd4-8b2c-1d492677e297)

#### Deactivate Metro Card

**Deactivate Metro Card**

* **Method:** DELETE
* **Endpoint:** /api/metro-cards/deactivate/{cardId}
* **Response:**

    ![Deactivate Metro Card Response](https://github.com/user-attachments/assets/fa097c46-ad3e-42fa-8646-f933b9170ea6)

#### Deduct Fare from Metro Card Balance

**Deduct Fare from Metro Card Balance**

* **Method:** PUT
* **Endpoint:** /api/metro-cards/deduct
* **Description:** Deducts fare from a metro card balance
* **Response:**

    ![Deduct Fare from Metro Card Balance Response](https://github.com/user-attachments/assets/660363b3-3b1d-4200-bb9e-5b8005a6ca2c)

### Travel History Actions

#### Validate Travel ID

* **Method:** GET
* **Endpoint:** /api/travel-history/exists/{travelId}
* **Description:** Validates a travel ID
* **Response:**

    ![Validate Travel ID Response](https://github.com/user-attachments/assets/c8757e59-793f-4af1-8bc6-6aee07543bdb)

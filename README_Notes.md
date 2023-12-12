## Topics covered

- controller
- service
- repository
- entity
- dao
- dto
- enums
- package structure
- config
- exception
- validation
- error codes
- trace id
- logging (Slf4j)
- unit test case all layer (controller, service, repository) and at least 90% test coverage.
- check style
- sonar qube
- feign
- kafka
- camunda
- karate
- registration captcha validation
` - uuid
  - captcha id
  - ip
  - created date
  - config for expiration
- rate limiting service
- Auditing (individual audit history table with respect to entities.)
    - User : UserAudit
    - Customer : CustomerAudit
    - Order : OrderAudit
    - Payment : PaymentAudit, etc.
- Application timezone and DB timezone as "UTC" and display timezone in "Asia/Kolkata"
- Two factor authentication

## controller and service methods

- createResource()
- updateResource()
- getResourceById()
- getAllResources()
- deleteResourceById()
- getResourcesByPage()

**Synchronous Communication: (Microservice communication - Feign)**

1. Product Details: When a client requests product details, the product catalog microservice responds with the requested
   information synchronously. The client waits for the response before proceeding.
2. Cart Update: When a account adds or removes items from their shopping cart, the cart microservice is notified
   synchronously to update the cart. The client waits for the confirmation before displaying the updated cart.
3. Payment Processing: When a account initiates a payment, the payment microservice handles the transaction synchronously.
   The client waits for the payment confirmation or status update before proceeding.
4. User Authentication: When a account logs in or registers, the authentication microservice verifies the credentials
   synchronously and responds with the authentication status or account details.

**Asynchronous Communication: (Microservice communication - Kafka)**

1. Order Placed: When a customer places an order, the order microservice publishes an event asynchronously to indicate
   the order placement. Other microservices, such as inventory management, shipping, or notifications, can subscribe to
   this event and handle their respective tasks independently.
2. Inventory Update: When an order is placed, the order microservice emits an event asynchronously to update the
   inventory status. The inventory management microservice, subscribed to this event, can consume it and update the
   stock levels accordingly.
3. Shipping Status Update: As the shipping process progresses, the shipping microservice publishes asynchronous events
   to notify other microservices, such as order tracking or notifications, about the shipping status updates. Subscribed
   microservices can handle these events independently.
4. Email Notification: When an order is delivered, the notification microservice can asynchronously publish an event to
   trigger an email notification to the customer. The email service, subscribed to this event, can consume it and send
   the email asynchronously.


**List of common processes typically found in an ecommerce domain:**

1. Product Catalog Management: This process involves managing the product catalog, including adding, updating, and
   deleting products. It includes storing product information such as name, description, pricing, and images.
2. User Registration and Authentication: This process involves allowing users to register and create accounts on the
   ecommerce platform. It includes account authentication mechanisms such as username/password or social login.
3. Product Search and Browsing: This process enables users to search for products based on criteria such as keywords,
   categories, or filters. It involves providing a account-friendly interface to browse and explore the product catalog.
4. Shopping Cart Management: This process allows users to add products to a shopping cart, modify quantities, and remove
   items. It ensures the proper handling of the account's selected products throughout the shopping session.
5. Checkout and Payment: This process involves facilitating the checkout process, including entering shipping and
   billing information, selecting a payment method, and processing the payment securely.
6. Order Processing: This process handles the creation, validation, and fulfillment of customer orders. It involves
   inventory management, order status tracking, and generating invoices or receipts.
7. Shipping and Delivery: This process manages the shipment of products to customers, including logistics, tracking, and
   coordinating with shipping providers. It ensures timely and accurate delivery of orders.
8. Customer Reviews and Ratings: This process allows customers to provide feedback, ratings, and reviews for products
   and sellers. It helps in building trust and guiding future purchasing decisions.
9. Order Tracking and Customer Support: This process enables customers to track their orders, view shipment status, and
   access customer support services. It includes order history, return/exchange management, and resolving customer
   queries or issues.
10. Promotions and Marketing: This process involves running marketing campaigns, discounts, promotional offers, and
    personalized recommendations to attract and retain customers.
11. Reporting and Analytics: This process includes gathering and analyzing data related to sales, customer behavior,
    inventory management, and other key metrics to derive insights for business decision-making.

**Kafka Events DTO**

1. OrderPlacedEventDTO: This DTO represents an event indicating that an order has been placed by a customer. It
   typically includes information such as the order ID, customer details, products ordered, and any additional relevant
   metadata.
2. InventoryUpdateEventDTO: This DTO represents an event indicating an update to the inventory status of a product. It
   may include details such as the product ID, stock quantity, and any other relevant information related to the
   inventory update.
3. ShippingStatusUpdateEventDTO: This DTO represents an event indicating the status update of a shipment. It includes
   details such as the shipment ID, tracking number, current status, and any other relevant information related to the
   shipping process.
4. UserRegisteredEventDTO: This DTO represents an event indicating that a new account has registered on the ecommerce
   platform. It may contain information such as the account ID, username, email, and any additional relevant details.
5. PaymentProcessedEventDTO: This DTO represents an event indicating that a payment has been successfully processed for
   an order. It typically includes details such as the order ID, payment transaction ID, payment amount, and any other
   relevant information related to the payment process.
6. ProductUpdatedEventDTO: This DTO represents an event indicating that a product's details or attributes have been
   updated. It may include information such as the product ID, updated attributes, and any additional relevant details.

**e-commerce where Camunda can be utilized:**

1. Order Management: Camunda can handle order management processes such as order validation, inventory management,
   payment processing, and order fulfillment. Workflows can be designed to ensure that orders go through the appropriate
   steps and are handled efficiently.
2. Returns and Refunds: When handling returns and refunds, Camunda can help automate the process. Workflows can be
   created to manage return requests, validate returns, process refunds, update inventory, and track the status of each
   return.
3. Customer Support: Camunda can be used to manage customer support processes, such as handling customer inquiries,
   managing support tickets, escalating issues to appropriate teams, and tracking the resolution status.
4. Shipping and Logistics: Camunda can automate shipping and logistics processes by orchestrating tasks such as
   generating shipping labels, tracking shipments, coordinating with carriers, and managing delivery notifications.
5. Inventory Management: Camunda can help streamline inventory management processes, including stock updates,
   replenishment requests, inventory audits, and automated notifications for low-stock or out-of-stock items.
6. Vendor Management: Camunda can facilitate vendor management processes, such as onboarding new vendors, managing
   contracts and agreements, tracking vendor performance, and processing payments.
7. Fraud Detection and Prevention: Camunda can integrate with fraud detection systems and help automate the process of
   identifying and handling potentially fraudulent activities, such as suspicious transactions, account validations, and
   fraud investigations.


**Postgre entity id annotation**
```text
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
```

**MySql entity id annotation**
```text
 @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
```

## Roles
In an e-commerce system, various roles may exist to manage different aspects of the platform and its functionalities. Here's a list of common roles in an e-commerce application, along with brief descriptions of their responsibilities:

1. **Customer:**
   - Role: `ROLE_CUSTOMER`
   - Responsibilities: Registered users who can browse products, add items to their cart, place orders, and manage their profiles.

2. **Guest Customer:**
   - Role: `ROLE_GUEST`
   - Responsibilities: Users who are not registered but can browse products and add items to their cart. They may be prompted to register during the checkout process.

3. **Administrator:**
   - Role: `ROLE_ADMIN`
   - Responsibilities: Users with full access to the admin panel, allowing them to manage products, categories, orders, customer data, and other system configurations.

4. **Store Manager:**
   - Role: `ROLE_STORE_MANAGER`
   - Responsibilities: Users responsible for managing specific aspects of the product catalog, such as adding, updating, or removing products within their assigned categories.

5. **Order Fulfillment:**
   - Role: `ROLE_ORDER_FULFILLMENT`
   - Responsibilities: Users responsible for processing and fulfilling customer orders, managing inventory, and coordinating shipping and logistics.

6. **Customer Support:**
   - Role: `ROLE_CUSTOMER_SUPPORT`
   - Responsibilities: Users who handle customer inquiries, provide support, and resolve issues related to orders, products, and general inquiries.

7. **Marketing Manager:**
   - Role: `ROLE_MARKETING_MANAGER`
   - Responsibilities: Users responsible for creating and managing marketing campaigns, promotions, discounts, and analyzing customer behavior to optimize marketing strategies.

8. **Analytics and Reporting:**
   - Role: `ROLE_ANALYTICS`
   - Responsibilities: Users responsible for analyzing data, generating reports, and providing insights into customer behavior, sales trends, and overall system performance.

9. **Finance Manager:**
   - Role: `ROLE_FINANCE_MANAGER`
   - Responsibilities: Users responsible for managing financial aspects of the e-commerce platform, including budgeting, financial reporting, and reconciliation.

10. **Security Manager:**
   - Role: `ROLE_SECURITY_MANAGER`
   - Responsibilities: Users responsible for implementing and managing security measures to protect the e-commerce platform from cyber threats, ensuring compliance with security standards.

11. **Content Manager:**
   - Role: `ROLE_CONTENT_MANAGER`
   - Responsibilities: Users responsible for managing and updating content on the website, including product descriptions, images, and other informational content.

12. **Vendor:**
   - Role: `ROLE_VENDOR`
   - Responsibilities: Users representing external vendors who manage their product listings, inventory, and order fulfillment processes within the e-commerce platform.


## Pre-deployment steps

- CheckStyle : local development code smell
- Karate : Automated Testing
- Git : Version Control
- Apache Maven : Build
- SonarQube : Code smell
- Apache JMeter : Load Testing
- Snyk : scan, prioritize, and fix security vulnerabilities in your code, open source dependencies, container images,
  and Infrastructure as Code (IaC) configurations.
- Jenkins : CI/CD
- Nexus : Artifacts
- Jacco : Code Coverage
- maven: final build
- Docker: Containerization
- kubernetes : container orchestration platforms
- Elastic : logging
- Kafka: Message broker
- Spring Cloud Config or external configuration files


## Postgres
```sql
DROP USER chat_user;
DROP DATABASE chat_db;
CREATE USER chat_user WITH PASSWORD 'chat_password';
CREATE DATABASE chat_db;
GRANT ALL PRIVILEGES ON DATABASE chat_db TO chat_user;
```

## Docker
```shell
# run container in detached mode
docker run --name demo-container -p 8080:8080 -d demo-image 
# stop container
docker stop demo-container 
# remove container
docker rm demo-container 
# set container volume from local `pwd` to container directory `/code`
docker run --name demo-container -p 8080:8080 -d -v $(pwd):/code demo-image
#Check the created images
docker images
#Check the created containers
docker ps -a
#Check the logs
docker container logs CONTAINER_ID

```
## Docker compose

Let change `docker run --name config-server -p 8080:8080 -d -v $(pwd):/code demo-image` to docker compose file

docker-compose.yml

```yaml
version: '3.9'

services:
  config-server:
    build: *
    container_name: config-server
    command: java -jar app.jar --host 0.0.0.0 --port 8080 --reload
    ports:
      - "8080:8080"
    volumes:
      - .:/code
```

```shell
 docker-compose -f .\common.yml -f .\docker-compose.yml build
 docker-compose -f .\common.yml -f .\docker-compose.yml up -d
 docker-compose down
```
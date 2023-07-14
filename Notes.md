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
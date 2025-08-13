<<<<<<< HEAD
# ProductApp

A Spring Boot application for managing products with CRUD operations: add, delete, update, search, and view all products.

## Features
- Add a product
- Delete a product
- Update a product
- Search products by name
- View all products

## Getting Started
1. Ensure you have Java and Maven installed.
2. Build and run the app using Maven:
   ```
   mvn spring-boot:run
   ```
3. Access the API endpoints at `http://localhost:8080/products`.

## API Endpoints
- `POST /products` - Add product
  Example body:
  ```json
  {
    "name": "Sample Product",
    "price": 99.99,
    "quantity": 10
  }
  ```
- `DELETE /products/{id}` - Delete product
- `PUT /products/{id}` - Update product
  Example body:
  ```json
  {
    "name": "Updated Product",
    "price": 123.45,
    "quantity": 20
  }
  ```
- `GET /products/search?name=...` - Search products
- `GET /products` - View all products
- `GET /products/{id}` - View product by ID
=======
# ProductApplication
>>>>>>> 54f02aada0c8b2cc577c6602d0652aff6ec54152

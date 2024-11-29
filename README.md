# Bakery Stock Management API

A REST API built with Kotlin and Spring Boot for managing bakery product stock.

## Prerequisites

- Java JDK 17 or later
- Gradle (optional, you can use the Gradle wrapper)

## Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application using:
   ```bash
   ./gradlew bootRun
   ```

## API Endpoints

### Products

- `GET /api/products` - List all products
- `GET /api/products/{id}` - Get a specific product
- `GET /api/products/search?name={name}` - Search products by name
- `GET /api/products/low-stock` - Get products with low stock
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a product
- `PATCH /api/products/{id}/stock` - Update product stock quantity
- `DELETE /api/products/{id}` - Delete a product

### Documentation
- Swagger UI: `/swagger-ui.html` - Interactive API documentation
- H2 Console: `/h2-console` - Database management interface

## Features

- Complete CRUD operations for bakery products
- Stock level monitoring
- Low stock alerts
- Product search functionality
- H2 in-memory database (can be easily switched to production database)
- OpenAPI documentation with Swagger UI
- RESTful API design
- Built with Kotlin and Spring Boot

## Project Structure

```
src/main/kotlin/com/example/api/
├── Application.kt              # Main application file
├── controller/                 # REST controllers
│   └── ProductController.kt
├── service/                    # Business logic
│   └── ProductService.kt
├── repository/                 # Data access
│   └── ProductRepository.kt
├── model/                      # Data models
│   └── Product.kt
└── dto/                       # Data Transfer Objects
    └── ProductDTO.kt
```

## Sample Requests

### Create Product
```json
POST /api/products
{
    "name": "Chocolate Croissant",
    "description": "Buttery croissant filled with chocolate",
    "price": 3.50,
    "quantity": 50,
    "minimumStock": 10
}
```

### Update Stock
```json
PATCH /api/products/1/stock
{
    "quantity": 45
}

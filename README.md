# Inventory Tracking System

A Java-based inventory tracking system developed with a graphical user interface, database connectivity, and stock management features.

## About the Project

This project is an inventory management application designed to manage products, users, and stock information.

The application uses a database for persistent data storage and follows a modular structure by separating database operations, business logic, and graphical user interface components.

## Technologies Used

- Java
- Java Swing
- Maven
- SQL / Database Connectivity
- JDBC
- Object-Oriented Programming (OOP)
- DAO (Data Access Object) Pattern

## Features

- User login system
- Product management
- Stock tracking
- Database connectivity
- Adding and managing products
- Different product types
- Graphical user interface
- Persistent data storage

## Project Structure

- `Main.java` - Entry point of the application
- `Product.java` - Base product model
- `ElectronicProduct.java` - Represents electronic products
- `FoodProduct.java` - Represents food products
- `InventoryManager.java` - Handles inventory operations
- `DBConnection.java` - Manages database connection
- `ProductDAO.java` - Handles product database operations
- `StockDAO.java` - Handles stock database operations
- `UserDAO.java` - Handles user database operations
- `FileManager.java` - Handles file-related operations
- `LoginScreen.java` - Login interface
- `MainScreen.java` - Main application interface
- `ProductScreen.java` - Product management interface
- `StockScreen.java` - Stock management interface
- `pom.xml` - Maven project configuration

## Software Architecture

The project uses the **DAO (Data Access Object) pattern** to separate database operations from the rest of the application logic.

This structure makes the application easier to maintain and separates responsibilities between the user interface, business logic, and data access layers.

## Project Report

The repository includes:

`InventoryTrackingSystem_Report.pdf`

The report contains additional information about the project design, implementation, and UML diagrams.

## Author

**Tarık Emir Yılmaz**

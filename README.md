# Currency List Application

This project is a **Currency List App** built using modern Android development practices. It follows the **MVI (Model-View-Intent)** design pattern, leverages **coroutines** for concurrency handling, and adheres to **Google's Clean Architecture** principles. The app uses **Room** for local database management, **Jetpack Compose** for UI development, and **Koin** for dependency injection.

---

## **Technologies Used**

### **Architecture**
- **MVI (Model-View-Intent):** Ensures unidirectional data flow, making the application easier to manage and test.
- **Clean Architecture:** Separates the codebase into layers for better scalability and maintainability.

### **Concurrency**
- **Kotlin Coroutines:** Handles asynchronous tasks like database operations and filtering currencies efficiently.

### **UI**
- **Jetpack Compose:** Provides a declarative and modern approach to building UI.

### **Database**
- **Room:** Manages local storage of currency data.

### **Dependency Injection**
- **Koin:** A lightweight Dependency Injection framework to manage dependencies.

### **Testing**
- Includes **unit tests** to validate critical features, such as search functionality and state transitions.

---

## **Modules**

The project is modularized into core and feature modules to maintain separation of concerns and scalability.

### **Core Module**
- **`coreResources`:**
    - Contains reusable components and utilities shared across feature modules.
    - Includes models like `CurrencyType` and other core resources.

### **Feature Module**
- **`featureCurrency`:**
    - Implements the Currency List feature, including:
        - **UI components** built with Jetpack Compose.
        - **State management** using the MVI pattern.
        - **Business logic** handled using ViewModel and UseCase.
        - **Data layer** integrated with Room database.

---

## **Branching Strategy**

The project follows a clear branching strategy for production and feature development:

- **Production Branch:**
    - `master`: Stable branch for production-ready code.

- **Feature Branch:**
    - `feature/currency`: Active development branch for the Currency List feature.

---

## **Application Features**

### **Currency List**
- Displays a list of currencies, including both crypto and fiat currencies.
- Uses `LazyColumn` for efficient rendering of large datasets.

### **Search Functionality**
- Allows users to search for currencies by name or symbol.
- Implements **debounce** (300ms) to optimize search performance.

### **Filter by Currency Type**
- Filters the list by:
    - **All Currencies**
    - **Crypto Currencies**
    - **Fiat Currencies**

### **Database Operations**
- Supports:
    - **Inserting data** into the database.
    - **Clearing data** from the database.
    - **Fetching all currencies** stored in the database.

---

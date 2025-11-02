# 🌐 Translation Management System (TMS)

A **Spring Boot**-based Translation Management System that helps manage translation keys and their localized content.  
This project provides a scalable foundation for applications that require internationalization (i18n) and localization (L10n) support.

---

## 🚀 Features

- Manage translation keys and localized values
- RESTful API for CRUD operations
- Modular and scalable architecture
- Adheres to **SOLID** design principles
- Uses popular design patterns for maintainability and testability
- Built with **Spring Boot**, **Spring Data JPA**, and **Lombok**

---

## 🏗️ Architecture & Design

The application is designed with **clean architecture** principles and a **layered structure** that separates concerns.

### ⚙️ SOLID Principles Applied

1. **Single Responsibility Principle (SRP)**  
   - Each class focuses on a single concern.  
   - **Controllers** handle HTTP requests  
   - **Services** encapsulate business logic  
   - **Repositories** handle persistence  
   - **Mappers** convert between entities and DTOs  

2. **Open/Closed Principle (OCP)**  
   - Components are open for extension but closed for modification.  
   - New functionality can be added via interfaces without changing existing code.  

3. **Liskov Substitution Principle (LSP)**  
   - Service implementations can be replaced through their interfaces without breaking functionality.  

4. **Interface Segregation Principle (ISP)**  
   - Interfaces are small and specific to their domain.  
   - Example: Separate repository interfaces for different entities.  

5. **Dependency Inversion Principle (DIP)**  
   - High-level modules depend on abstractions, not concrete classes.  
   - Implemented via **Spring’s dependency injection**.  

---

### 🧩 Design Patterns Used

| Pattern | Description |
|----------|--------------|
| **MVC** | Separates application layers into Controller, Service, and Model. |
| **Repository** | Abstracts database access through repository interfaces. |
| **DTO (Data Transfer Object)** | Transfers data between layers (e.g., `TranslationKeyRequest`, `TranslationKeyResponse`). |
| **Builder** | Simplifies object creation using Lombok’s `@Builder`. |
| **Strategy** | Supports different search and filtering strategies. |
| **Facade** | The service layer provides a simplified interface to complex business logic. |
| **Dependency Injection** | Promotes modularity and testability. |
| **Observer** | Utilizes Spring’s event system for auditing and notifications. |

---

## 📁 Project Structure


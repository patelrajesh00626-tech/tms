# Translation Management System (TMS)

A Spring Boot-based Translation Management System that helps manage translation keys and their localized content.

## Architecture & Design

### SOLID Principles Applied

1. **Single Responsibility Principle (SRP)**
   - Each class has a single responsibility
   - Controllers handle HTTP requests
   - Services contain business logic
   - Repositories manage data access
   - Mappers handle object transformations

2. **Open/Closed Principle (OCP)**
   - Open for extension but closed for modification
   - Interfaces define contracts (e.g., `TranslationKeyService`,
      - New functionality can be added by implementing interfaces without modifying existing code

3. **Liskov Substitution Principle (LSP)**
   - Derived classes can be substituted for their base classes
   - All service implementations can be replaced with their interface types

4. **Interface Segregation Principle (ISP)**
   - Specific interfaces for different functionalities
   - Example: Separate repository interfaces for different entities

5. **Dependency Inversion Principle (DIP)**
   - High-level modules don't depend on low-level modules
   - Both depend on abstractions
   - Dependency injection is used throughout the application

### Design Patterns Used

1. **MVC (Model-View-Controller)**
   - Controllers handle HTTP requests and responses
   - Services contain business logic
   - Models represent the data structure

2. **Repository Pattern**
   - Abstracts data access layer
   - Provides a collection-like interface for accessing domain objects
   - Used in all `*Repository` interfaces

3. **DTO (Data Transfer Object)**
   - Used to transfer data between processes
   - Example: `TranslationKeyRequest`, `TranslationKeyResponse`

4. **Builder Pattern**
   - Used via Lombok's `@Builder` for object creation
   - Provides a clear and flexible way to create complex objects

5. **Strategy Pattern**
   - Different strategies for searching and filtering
   - Example: Multiple search methods in repositories

6. **Facade Pattern**
   - Service layer acts as a facade to the complex business logic
   - Simplifies the interface to the subsystem

7. **Dependency Injection**
   - Used throughout the application
   - Promotes loose coupling and testability

8. **Observer Pattern**
   - Used in Spring's event handling
   - Example: Application events for auditing or notifications

### Project Structure

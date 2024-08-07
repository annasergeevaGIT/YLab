# CarShop-Service

# Description
A simple console application for managing a car dealership. Supports user registration and authorization with role management, viewing the list of registered clients and employees, and adding and editing employee information.

# Project Structure
- **model**: Contains classes for data representation.
- **service**: Contains the application logic.
- **in**: Controllers for handling input.
- **out**: Repositories for data storage. Currently empty
- **main**: The entry point of the application.

## Build and Run
1. Clone the repository.
2. Navigate to the project directory.
3. Compile the project: `mvn compile`
4. Run the project: `java -cp .\target\classes org.example.Main`

## Пример использования:

Регистрация и авторизация пользователей:

Пользователь с ролью ADMIN с логином "root" и паролем "root" создается автоматически при запуске приложения .

Авторизация пользователей:
Пользователь может войти в систему с использованием имени пользователя и пароля, автоматически присвоена роль CUSTOMER, которую может изменить ADMIN.

Управление автомобилями и заказами:
ADMIN и MANAGER могут просматривать, добавлять, редактировать и удалять автомобили.
ADMIN и MANAGER могут управлять заказами.
CUSTOMER может просматривать доступные автомобили и создавать заказы.

Экспорт журналов аудита:
ADMIN может экспортировать журналы аудита в файл.

# Features
User registration and authentication (admin, manager, customer).
Car management (add, edit, delete, view list).
Processing car purchase orders.
View and manage orders (search, change status, cancel).
User action logging.

# Requirements
Java 17
Maven (for dependency management and project build)
JUnit5 (for testing)

# Project Structure
CarShopService/
│	└──	src/
│		├──	main/
│		│	└──java/
│		│		├── in/
│		│		│   ├── AuthController.java
│		│		│   ├── CarController.java
│		│		│   ├── OrderController.java
│		│		│   ├── SearchController.java
│		│		│   └── UserController.java
│		│		├── model/
│		│		│	├── AuditLog.java
│		│		│   ├── Car.java
│		│		│	├── CarStatus.java
│		│		│   ├── Order.java
│		│		│	├── OrderStatus.java
│		│		│   ├── User.java
│		│		│   └── UserRole.java
│		│		├── repository/
│		│		│   ├── AuditRepository.java
│		│		│   ├── CarRepository.java
│		│		│   ├── OrderRepository.java
│		│		│   └── UserRepository.java
│		│		├── service/
│		│		│   ├── AuditService.java
│		│		│   ├── AuthService.java
│		│		│   ├── CarService.java
│		│	    │	├── OrderService.java
│		│		│   ├── SearchService.java
│		│		│   └── UserService.java
│		│		└── main/			
│		│			└── Main.java
│		└──	test/
│			└──java/
│				└── service/
│					├── AuditServiceTest.java
│					├── AuthServiceTest.java
│					├── CarServiceTest.java
│					├── OrderServiceTest.java
│					├── SearchServiceTest.java
│					└── UserServiceTest.java
├── pom.xml
└── README.md


# Classes and Methods

  "projectClassification": {
    "searchMode": "OpenProject", // OpenProject, AllProjects
    "includedProjects": "",
    "pathEndKeywords": "*.impl",
    "isClientPath": "",
    "isClientName": "",
    "isTestPath": "",
    "isTestName": "",
    "isMappingPath": "",
    "isMappingName": "",
    "isDataAccessPath": "",
    "isDataAccessName": "",
    "isDataStructurePath": "",
    "isDataStructureName": "",
    "isInterfaceStructuresPath": "",
    "isInterfaceStructuresName": "",
    "isEntryPointPath": "",
    "isEntryPointName": "",
    "treatFinalFieldsAsMandatory": false
  },
  "graphRestriction": {
    "classPackageExcludeFilter": "",
    "classPackageIncludeFilter": "",
    "classNameExcludeFilter": "",
    "classNameIncludeFilter": "",
    "methodNameExcludeFilter": "",
    "methodNameIncludeFilter": "",
    "removeByInheritance": "", // inheritance/annotation based filtering is done in a second step
    "removeByAnnotation": "",
    "removeByClassPackage": "", // cleanup the graph after inheritance/annotation based filtering is done
    "removeByClassName": "",
    "cutMappings": false,
    "cutEnum": true,
    "cutTests": true,
    "cutClient": true,
    "cutDataAccess": true,
    "cutInterfaceStructures": true,
    "cutDataStructures": true,
    "cutGetterAndSetter": true,
    "cutConstructors": true
  },
  "graphTraversal": {
    "forwardDepth": 3,
    "backwardDepth": 3,
    "classPackageExcludeFilter": "",
    "classPackageIncludeFilter": "",
    "classNameExcludeFilter": "",
    "classNameIncludeFilter": "",
    "methodNameExcludeFilter": "",
    "methodNameIncludeFilter": "",
    "hideMappings": false,
    "hideDataStructures": false,
    "hidePrivateMethods": true,
    "hideInterfaceCalls": true, // indirection: implementation -> interface (is hidden) -> implementation
    "onlyShowApplicationEntryPoints": false, // root node is included
    "useMethodCallsForStructureDiagram": "ForwardOnly" // ForwardOnly, BothDirections, No
  },
  "details": {
    "aggregation": "GroupByClass", // ByClass, GroupByClass, None
    "showClassGenericTypes": true,
    "showMethods": true,
    "showMethodParameterNames": true,
    "showMethodParameterTypes": true,
    "showMethodReturnType": true,
    "showPackageLevels": 2,
    "showDetailedClassStructure": true
  },
  "rootClass": "main.Main",

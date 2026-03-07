# EchoBazaar

JavaFX desktop application combining vendor inventory management with customer shopping in a unified marketplace, featuring automated restocking and inter-vendor product sourcing.

## Table of Contents

- [About](#about)
- [Features](#features)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Tech Stack](#tech-stack)
- [License](#license)

## About

Traditional inventory management systems often lack seamless integration between vendors and customers, making it difficult to create a unified marketplace where vendors can efficiently manage their storage while customers can easily discover and purchase products.

EchoBazaar solves this by providing a combined platform where vendors manage inventory and customers shop, with automated restocking, inter-vendor sourcing, and a shared "Global Bazaar" marketplace accessible to all participants.

## Features

- **Authentication:** Secure login with encrypted password storage and account recovery
- **Dual Accounts:** Separate vendor and customer registration and workflows
- **Account Management:** Username and password modification, balance management with deposit/withdraw
- **Vendor Features:**
  - Product registration with detailed specifications and pricing
  - Inventory search, modification, and restocking management
  - Automated restocking with configurable minimum stock thresholds
  - Inter-vendor trading: source products directly from other vendors
- **Customer Features:**
  - Browse Global Bazaar marketplace shared across all vendors
  - Shopping cart with add, remove, and checkout
  - Order processing with integrated payment system
- **Global Bazaar:** Unified marketplace accessible to vendors and customers for product discovery

## Project Structure

```text
EchoBazaar/
├── src/
│   └── main/
│       ├── java/
│       │   ├── auth/                   # Password encryption utilities
│       │   ├── controllers/            # JavaFX MVC controllers
│       │   ├── model/                  # Data models and business logic
│       │   │   └── users/              # Vendor and Customer models
│       │   └── Main.java               # Application entry point
│       └── resources/
│           ├── css/                    # UI stylesheets
│           ├── data/                   # Persistent text-file storage
│           ├── fxml/                   # FXML view layouts
│           └── images/                 # Application icons and assets
├── docs/
│   ├── classdiagOf_4_pinguinoReale_echoBazaar.uxf    # Class diagram
│   ├── usecaseOf_4_pinguinoReale_echoBazaar.uxf      # Use case diagram
│   └── images/                         # UI screenshots
├── LICENSE
└── README.md
```

The project follows a clean layered architecture:
- **auth/**: Password encryption and security utilities
- **controllers/**: JavaFX controller classes implementing MVC pattern
- **model/**: Data models, business logic, and user types
- **resources/**: Runtime assets (layouts, styles, data storage)
- **docs/**: Documentation and design diagrams

## Installation

### Prerequisites

- Java 21 or newer
  - [Oracle](https://www.java.com/it/download/manual.jsp) or [OpenJDK](https://openjdk.org/)
- JavaFX runtime libraries
  - [Gluon JavaFX](https://gluonhq.com/products/javafx/)
- A Java IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

### Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/LorenBll/EchoBazaar.git
   cd EchoBazaar
   ```

2. **Open in your IDE**

3. **Configure JavaFX SDK:**
   Add the JavaFX SDK to your IDE's classpath/module path settings

4. **Run the application:**
   Execute `src/main/java/Main.java`

### Manual Execution

1. **Ensure Java 21 and JavaFX are available:**
   - Add both to your system PATH
   - Add JavaFX libraries to project classpath

2. **Set module path for JavaFX**

3. **Run from terminal:**
   ```bash
   java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp out Main
   ```

## Usage

1. Launch the application via `Main.java`
2. Log in with existing credentials or register a new account (vendor or customer)
3. **For Vendors:**
   - Register products with specifications and pricing
   - Manage inventory and configure automated restocking
   - Browse and source products from other vendors
4. **For Customers:**
   - Browse the Global Bazaar to discover products
   - Add items to shopping cart and complete purchases

## Tech Stack

- **Language:** Java 21
- **UI Framework:** JavaFX with FXML
- **Architecture:** MVC (Model-View-Controller)
- **Build Tool:** IDE-based (IntelliJ, Eclipse, or similar)
- **UI Design:** SceneBuilder, CSS styling
- **Data Storage:** Plain text files

## License

This project is licensed under the terms specified in [LICENSE](LICENSE).

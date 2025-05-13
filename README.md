# ✈️ Flight Booking Management System

A console-based Flight Booking Management System developed as part of the Programming II course at FCDS. This Java application simulates the essential operations of a travel agency, including flight search, booking management, and role-based user access. The project applies core Object-Oriented Programming (OOP) principles and uses text files for data persistence.

---

## 📚 Table of Contents

- [Features](#features)
- [Object-Oriented Design](#object-oriented-design)
- [Data Storage](#data-storage)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Usage](#usage)
- [License](#license)

---

## ✅ Features

### 👤 User Authentication
- Secure login system using username and password
- Role-based access: Customer, Agent, Administrator
- User profile creation and session management

### ✈️ Flight Management
- Add and update flights
- Search by origin, destination, date, etc.
- View flight schedules and seat availability

### 📦 Booking Management
- Create, modify, and cancel bookings
- Add passengers and manage seat selections
- Generate booking references and travel itineraries

### 💳 Payment Simulation
- Simulated payment process
- Track payment status
- Generate and display e-tickets

---

## 🧠 Object-Oriented Design

- **Inheritance**: Base `User` class extended by `Customer`, `Agent`, and `Administrator`
- **Encapsulation**: All class attributes are private with public accessors and mutators
- **Polymorphism**: Overridden methods and shared interfaces
- **Abstraction**: Use of abstract classes and interfaces for key system components
- **Relationships**: Composition and aggregation (e.g. a `Booking` has `Passengers`, a `Customer` has `Bookings`)

---

## 🗃️ Data Storage

All system data is stored in structured `.txt` files:

- `users.txt`: Stores user credentials and roles
- `flights.txt`: Flight details including schedule and pricing
- `bookings.txt`: Booking data with references and status
- `passengers.txt`: Passenger personal and travel details

All file operations are handled with proper error handling using Java's `try-catch` blocks.

---

## 🏗️ Project Structure (Key Classes)

- `User` (abstract): Common properties and methods for all users
- `Customer`, `Agent`, `Administrator`: Inherit from `User` and implement role-specific behavior
- `Flight`: Holds flight details, seat availability, and pricing
- `Booking`: Manages flight bookings and ticket generation
- `Passenger`: Stores traveler details and special requests
- `Payment`: Simulates and verifies payments
- `BookingSystem`: Core controller for all operations
- `FileManager`: Handles loading/saving to/from text files

---

## 🛠 Technologies Used

- Programming Language: Java
- Paradigm: Object-Oriented Programming
- Data Storage: File I/O (Text Files)
- Interface: Console-based menu system

---

## ▶️ Usage

1. Clone this repository:
   ```bash
   git clone https://github.com/AbdoXCode/flight-booking-system.git

# Call A Guy – Customer App

The **Customer App** of the Call A Guy service booking system.  
This Android application allows customers to register, browse services, book appointments, manage their profile, and raise support tickets.  
Built with **modern Android development practices** and a clean architecture approach.

---

## 📱 Features

- **User Authentication** – Sign up & log in with secure session handling.
- **Service Browsing** – Explore available services and subservices with intuitive navigation.
- **Service Booking** – Select subservices, fill in details (time, address, notes) and book.  
  *(Payment gateway not yet implemented – mock system in place).*
- **Service Status Tracking** – View ongoing and completed services with a toggle switch.
- **Support Tickets** – Raise a ticket for service-related issues and chat with the admin until resolved.
- **Profile Management** – View and update profile details, upload profile picture.
- **Notifications** – Local notifications for booking status and ticket updates.
- **Offline Profile Access** – Profile details stored locally using Room.
- **Enhanced UX** –  
  - Custom shimmer loading screens.  
  - Dedicated error screens with retry functionality.  
  - Smooth navigation using type-safe Compose Navigation.

---

## 🛠 Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Architecture**: MVVM-inspired (UI → ViewModel → UseCase → Repository → API/Database)
- **Dependency Injection**: Dagger Hilt
- **Networking**: Retrofit, OkHttp3, Kotlinx Serialization
- **Local Storage**: Room Database
- **Image Loading**: Coil
- **Navigation**: Compose type-safe navigation
- **Notifications**: Local notifications (Android)
- **Other**: Shimmer effect for loading states

---

## 🏗 Architecture Overview

UI (Jetpack Compose)
↓
ViewModel
↓
UseCase
↓
Repository
↓
API Service (Retrofit) / Local Database (Room)


---

## 📸 Screens & Modules

- **Splash Screen**
- **Main Service Screen** – List of services.
- **Subservice Screen** – Contextual list based on chosen service.
- **Service Request Screen** – Fill booking details.
- **Service List Screen** – Toggle between ongoing & completed services.
- **Ticket List Screen** – Toggle between open & resolved tickets.
- **Chat Screen** – Communicate with admin (message sending only if ticket is open).
- **Profile Screen** – View/update profile & upload profile picture.

---

## 📽 Demo

*

https://github.com/user-attachments/assets/2a37648f-87e3-4176-8ab3-6bc629047ccb

*

---

## 🚀 Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Shahbazsam/CallAGuyCustomerApp.git

2. Open the project in Android Studio.

3. Sync Gradle and build the project.

4. Run on an emulator or connected Android device (minSdk = 26).

📌 Future Plans

Migration to Kotlin Multiplatform for iOS support.

Extended offline capabilities (service browsing & ticket history).

👤 Author
Mohammad Shahbaz



# Finance Tracker App

![Splash Screen](https://i.postimg.cc/3dLbRFqg/splash-screen.png)

A **Finance Tracker App** built with **Android Kotlin**, **XML**, and **MVVM Architecture**. This app helps users manage their finances by tracking income, expenses, and visualizing their financial data through interactive graphs.

---

## 📸 Screenshots

| Splash Screen | Login Screen | Signup Screen |
|---------------|--------------|---------------|
| <img src="https://i.postimg.cc/3dLbRFqg/splash-screen.png" width="250"/> | <img src="https://i.postimg.cc/jwdszXsR/a11.png" width="250"/> | <img src="https://i.postimg.cc/TKgYG17v/a12.png" width="250"/> |

| Home Screen | Transactions Screen | Profile Screen |
|-------------|---------------------|----------------|
| <img src="https://i.postimg.cc/1gSRzS2n/a4.png" width="250"/> | <img src="https://i.postimg.cc/p9BWh7M0/a8.png" width="250"/> | <img src="https://i.postimg.cc/mzXTVHDC/a7.png" width="250"/> |

| Income/Expense Graph | Transaction Details | Profile Update Currency Option |
|----------------------|---------------------|----------------|
| <img src="https://i.postimg.cc/Th1d1C79/a9.png" width="250"/> | <img src="https://i.postimg.cc/F169L3wQ/a10.png" width="250"/> | <img src="https://i.postimg.cc/H8rdNkcK/a6.png" width="250"/> |

---

## 📱 Features

### **1. Splash Screen**
- A visually appealing splash screen with animations to welcome users.

### **2. Authentication**
- **Login & Signup**: Secure user authentication to manage personal budgets.

### **3. Home Screen**
- **Budget Overview**: Displays total budget, income, and expenses.
- **Income/Expense Breakdown**: Interactive pie chart to visualize financial data.

### **4. Transactions**
- **Grouped by Date**: View all transactions organized by date for easy tracking.

### **5. Profile Management**
- **Update Profile**: Users can update their personal information.

---

## 🛠️ Technologies Used

- **Programming Languages**: Kotlin, XML
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Firebase Firestore
- **Charts**: MPAndroidChart (for graphs)
- **Authentication**: Firebase Authentication

---

## 🚀 Getting Started

### **Prerequisites**
- Android Studio (latest version)
- Kotlin plugin installed
- Android device/emulator with API level 21 or higher

### **Installation**
1. Clone the repository:
   ```bash
   git clone https://github.com/Progambler227788/Finance_Tracker_App.git
   ```
2. Open the project in Android Studio.
3. Build and run the app on your device/emulator.

---

## 🧩 Project Structure

```
Finance_Tracker_App/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/talhaatif/financeapk/
│   │   │   │   ├── adapters/          # Adapters for RecyclerView
│   │   │   │   ├── dialog/            # Custom dialogs
│   │   │   │   ├── firebase/          # Firebase-related classes
│   │   │   │   ├── models/            # Data models
│   │   │   │   ├── repositories/      # Repository classes
│   │   │   │   ├── viewmodel/         # ViewModel classes
│   │   │   ├── res/
│   │   │   │   ├── drawable/          # Images and icons
│   │   │   │   ├── color/             # Color resources
│   │   │   │   ├── font/              # Custom fonts
│   │   │   │   ├── layout/            # XML layouts
│   │   │   │   ├── menu/              # Menu resources
│   │   │   │   ├── mipmap/            # Launcher icons
│   │   │   │   ├── anim/              # Animation files
│   │   │   │   ├── values/            # Colors, strings, styles
│   ├── build.gradle                   # App-level Gradle file
├── build.gradle                       # Project-level Gradle file
```

---

## 🧑‍💻 Contributing

Contributions are welcome! If you'd like to contribute:
1. Fork the repository.
2. Create a new branch (`git checkout -b feature/YourFeatureName`).
3. Commit your changes (`git commit -m 'Add some feature'`).
4. Push to the branch (`git push origin feature/YourFeatureName`).
5. Open a pull request.

---

## 🙏 Acknowledgments

- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) for interactive graphs.
- [Firebase](https://firebase.google.com/) for authentication (if used).
- [Android Developers](https://developer.android.com/) for official documentation.

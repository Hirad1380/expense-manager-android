# 💰 Expense Manager — Android App

A native Android application for managing personal expenses, built with **Kotlin** and **SQLite**. Features full user authentication, CRUD operations, real-time search, and a Dark/Light mode toggle — all with a clean Material Design interface.

---



## ✨ Features

- 🔐 **User Authentication** — Register and login with username & password stored locally
- ➕ **Add Expenses** — Log expenses with title, description, price, and date
- ✏️ **Edit & Delete** — Update or remove any expense entry
- 🗑️ **Delete All** — Clear all data with a confirmation dialog
- 🔍 **Real-time Search** — Instantly filter expenses by title as you type
- 🌙 **Dark / Light Mode** — Toggle theme from the toolbar menu
- 📋 **Empty State UI** — Friendly illustration shown when no data exists
- 💾 **Local Storage** — All data persists offline using SQLite

---

## 🗂️ Project Structure

```
app/src/main/java/com/example/remainingapplication/
│
├── MainActivity.kt       # Home screen — displays expense list with search & theme toggle
├── AddActivity.kt        # Add new expense form
├── UpdateActivity.kt     # Edit existing expense
├── LoginActivity.kt      # Login screen
├── SignupActivity.kt     # Register new user
├── DatabaseHelper.kt     # SQLite DB — manages Users & Expenses tables
├── CustomAdapter.kt      # RecyclerView adapter with search filter
└── DataModel.kt          # Data class for expense entries
```

---

## 🗄️ Database Schema

### Table: `users`
| Column     | Type    |
|------------|---------|
| id         | INTEGER (PK, AUTO) |
| username   | TEXT    |
| password   | TEXT    |

### Table: `my_Data`
| Column           | Type    |
|------------------|---------|
| id               | INTEGER (PK, AUTO) |
| Data_Title       | TEXT    |
| Data_Description | TEXT    |
| Data_Price       | INTEGER |
| Data_Date        | TEXT    |

---

## 🛠️ Tech Stack

| Category       | Technology                        |
|----------------|-----------------------------------|
| Language       | Kotlin                            |
| UI             | XML Layouts, Material Design 3    |
| Database       | SQLite (SQLiteOpenHelper)         |
| Architecture   | Activity-based                    |
| RecyclerView   | CustomAdapter with Filterable     |
| Build Tool     | Gradle                            |
| Min SDK        | Android 7.0 (API 24)              |

---

## 🚀 Getting Started

### Prerequisites
- Android Studio (latest version)
- Android SDK installed

### Run the project
```bash
# 1. Clone the repository
git clone https://github.com/Hirad1380/expense-manager-android.git

# 2. Open in Android Studio
File → Open → select the project folder

# 3. Run on emulator or physical device
Click ▶ Run
```

---

## 👨‍💻 Author

**Hirad Bayat**  
M.Sc. Applied Computer Science — University of Duisburg-Essen  
📧 Bayathirad7@gmail.com  

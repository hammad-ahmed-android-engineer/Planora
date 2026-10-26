# 📝 Planora - Offline Task & Productivity Manager

**Planora** is a modern, feature-rich, 100% offline task management and productivity Android application. Built natively with **Kotlin**, **Jetpack Compose**, **Material 3**, and **Clean Architecture**, Planora helps users organize daily tasks, set time-critical reminders, track productivity streaks, and manage focus sessions with a Pomodoro timer—all without needing an internet connection or user account.

---

## 🌟 What is Planora?

Planora combines task management, smart scheduling, focus timers, and productivity analytics into a unified, privacy-focused experience. Every piece of data remains strictly on your device.

Key highlights:
- **100% Offline & Private**: Zero cloud tracking, external API calls, or account sign-ins required.
- **Smart Views**: Organized workflows for Inbox, Today, Upcoming, High Priority, Overdue, Archived, and Trash.
- **Natural Language Parsing**: Create tasks quickly using syntax like `"Meeting tomorrow at 3 PM !urgent #Work"`.
- **Pomodoro Focus Timer**: Built-in focus session tracking with ambient modes and log histories.
- **Productivity Streaks & Analytics**: Visual charts for completion rates, category distributions, and daily/longest streaks.
- **Home Screen Widget**: Quick task oversight directly from your Android home screen.

---

## ✨ Key Features

### 📋 1. Comprehensive Task & Subtask Management
- Create tasks with title, rich description, start/due dates, due times, deadlines, and priorities (**Urgent, High, Medium, Low, None**).
- Interactive subtask checklists with visual progress indicators and auto-completion tracking.
- Favorite and pin important tasks to keep them at the top of your list.

### ⚡ 2. Quick Entry with Natural Language Parsing (NLP)
- Fast-input bar available throughout the app.
- Automatically parses date, time, priority, and tags from plain text input.
  - **Priorities**: `!urgent`, `!high`, `!medium`, `!low`
  - **Tags**: `#Work`, `#Personal`, `#Fitness`
  - **Relative Dates**: `today`, `tomorrow`, `tonight`, `next monday`
  - **Times**: `at 5 PM`, `10:30am`, `17:00`

### 🔁 3. Smart Recurrence Engine
- Automated recurring task rules: **Daily, Weekdays, Weekends, Weekly, Monthly, Yearly, and Custom intervals**.
- Automatically schedules the next occurrence upon completing a recurring task, preserving completed history.

### ⏳ 4. Pomodoro Focus Timer
- Integrated focus sessions: **Focus (25 min)**, **Short Break (5 min)**, and **Long Break (15 min)**.
- Tracks daily and weekly focus minutes and saves session history to local database logs.

### 📊 5. Productivity Analytics & Streak Tracking
- Detailed productivity statistics dashboard:
  - Daily, weekly, and monthly completion counts.
  - Overall task completion rate percentage.
  - Category and priority distribution breakdown.
  - Active productivity streak and longest streak counter.

### 📅 6. Interactive Calendar & Search
- Day-by-day and monthly calendar views to plan ahead.
- Advanced multi-filter search engine: filter by text query, priority, category, tag, date range, or completion status.

### 📝 7. Task Templates
- Save reusable workflows (e.g., Weekly Review, Workout Routine, Onboarding) into templates and spawn tasks with a single tap.

### 🔔 8. Reliable Alarm & Notification System
- Time-exact alerts using Android `AlarmManager` and `FullScreenAlertActivity`.
- Direct notification actions: **Mark Complete** and **Snooze (15 mins)**.
- **Boot & Timezone Receiver**: Reschedules all active reminders automatically upon device reboot or timezone change.

### 💾 9. Local Backup & Restore
- Full JSON-based data backup and restore mechanism using `Gson` with schema validation.

### 🎨 10. Themes & Customization
- Dynamic **Light**, **Dark**, and **System** themes with customizable accent color palettes.

---

## 🛠 Tech Stack & Libraries

| Layer / Concern | Technology / Library | Version |
| :--- | :--- | :--- |
| **Language** | [Kotlin](https://kotlinlang.org/) | `2.4.21` |
| **UI Framework** | [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3) | BOM `2026.09.00` |
| **Architecture** | Clean Architecture + MVVM + Unidirectional Data Flow | - |
| **Dependency Injection** | [Hilt](https://dagger.dev/hilt/) | `2.60.1` |
| **Local Database** | [Room Database](https://developer.android.com/training/data-storage/room) | `2.8.5` |
| **Preferences Storage** | [Jetpack DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) | `1.3.0` |
| **Async & Flow** | [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines) & StateFlow | `1.11.0` |
| **Navigation** | [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) | `2.10.2` |
| **Annotation Processing**| [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html) | `2.3.12` |
| **JSON Serialization** | [Gson](https://github.com/google/gson) | `2.14.0` |
| **Background Tasks** | [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) & `AlarmManager` | `2.12.0` |
| **Unit Testing** | [JUnit 4](https://junit.org/), [MockK](https://mockk.io/), Coroutines Test | `4.13.2` / `1.14.11` |
| **Build System** | Gradle (`.gradle.kts`), Android Gradle Plugin | AGP `9.4.1` |

---

## 📐 Project Architecture & Package Structure

Planora follows **Clean Architecture** principles separated into **Data**, **Domain**, and **UI/Presentation** layers:

```
com.example.planora
├── PlanoraApplication.kt              # App Entry Point & Hilt Application Setup
├── MainActivity.kt                    # Single Activity Host with Edge-to-Edge & Theme setup
│
├── data/                              # DATA LAYER
│   ├── local/                         # Room Database, DAOs, and Entities
│   │   ├── PlanoraDatabase.kt
│   │   ├── dao/                       # TaskDao, CategoryDao, TagDao, FocusSessionDao, etc.
│   │   └── entity/                    # TaskEntity, SubtaskEntity, CategoryEntity, TagEntity, etc.
│   ├── mapper/                        # Entity <-> Domain Model Mappers
│   ├── preferences/                   # DataStore User Preferences Repository
│   └── repository/                    # Repository Implementations & BackupRestoreManager
│
├── di/                                # DEPENDENCY INJECTION
│   ├── DatabaseModule.kt              # Room & DataStore Hilt Provider Module
│   └── RepositoryModule.kt            # Repository Bindings Hilt Module
│
├── domain/                            # DOMAIN LAYER (Pure Kotlin Business Logic)
│   ├── model/                         # Domain Entities (Task, Category, Tag, Priority, etc.)
│   ├── repository/                    # Repository Interfaces
│   └── usecase/                       # Business Use Cases & Utilities
│       ├── NaturalLanguageTaskParser.kt
│       ├── RecurrenceCalculator.kt
│       ├── CalculateProductivityStatsUseCase.kt
│       ├── SaveTaskUseCase.kt
│       ├── DeleteTaskUseCase.kt
│       ├── GetTasksUseCase.kt
│       └── ToggleTaskCompletionUseCase.kt
│
├── notification/                      # NOTIFICATION & ALARM SYSTEM
│   ├── AlarmScheduler.kt              # Schedule / Cancel Exact Alarms
│   ├── AlarmAudioPlayer.kt            # Alarm Ringtone Player
│   ├── AlarmVibratorHelper.kt         # Haptic Feedback Helper
│   ├── FullScreenAlertActivity.kt     # Lockscreen Full-Screen Alert Activity
│   ├── TaskReminderReceiver.kt        # Alarm BroadcastReceiver
│   ├── TaskActionReceiver.kt          # Quick Notification Actions ("Complete", "Snooze")
│   ├── StreakNotificationReceiver.kt  # Streak Reminder Receiver
│   └── BootReceiver.kt                # Reschedule Alarms on System Reboot
│
├── ui/                                # PRESENTATION / UI LAYER (Jetpack Compose)
│   ├── components/                    # Reusable UI Composables (TaskItemCard, QuickTaskInputBar, etc.)
│   ├── navigation/                    # Bottom Navigation, NavRail & Screen Destinations
│   ├── screens/                       # App Screens & ViewModels
│   │   ├── home/                      # Home Dashboard & Task List
│   │   ├── calendar/                  # Calendar Screen
│   │   ├── categories/                # Category Management
│   │   ├── focus/                     # Pomodoro Focus Timer Screen
│   │   ├── search/                    # Advanced Search & Filter
│   │   ├── settings/                  # Settings, Theme, Backup & Management Shortcuts
│   │   ├── splash/                    # Animated Splash Screen
│   │   ├── stats/                     # Productivity Analytics Dashboard
│   │   ├── taskdetail/                # Task Details Screen
│   │   ├── taskeditor/                # Task Creation & Editing Screen
│   │   ├── taskview/                  # Filtered Views (Inbox, Today, Upcoming, Archived, Trash)
│   │   └── templates/                 # Task Template Management
│   └── theme/                         # Color Schemes, Typography, Shapes, Dynamic Theming
│
├── util/                              # UTILITIES & EXTENSIONS
│   └── AppUtils.kt                    # Date/Time Formatters, Color Converters
│
└── widget/                            # APP WIDGET
    └── PlanoraWidgetProvider.kt       # Home Screen Glance Task Widget
```

---

## ⚡ Natural Language Syntax Reference

When typing in the Quick Task Input Bar, Planora automatically parses attributes on the fly:

| Input Example | Parsed Attributes |
| :--- | :--- |
| `Buy groceries tomorrow at 5 PM !high #Shopping` | **Title**: Buy groceries<br>**Due Date**: Tomorrow<br>**Due Time**: 17:00<br>**Priority**: High<br>**Tag**: Shopping |
| `Submit project report next monday at 10am !urgent #Work` | **Title**: Submit project report<br>**Due Date**: Next Monday<br>**Due Time**: 10:00<br>**Priority**: Urgent<br>**Tag**: Work |
| `Gym workout tonight !medium #Fitness` | **Title**: Gym workout<br>**Due Date**: Today<br>**Priority**: Medium<br>**Tag**: Fitness |

---

## 🚀 Building & Running the Application

### Prerequisites
- **Android Studio**: Android Studio Ladybug (2024.2+) or higher.
- **JDK**: Java 17.
- **Android SDK**: `compileSdk = 37`, `minSdk = 26` (Android 8.0+).

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/Planora.git
   cd Planora
   ```
2. **Open in Android Studio** and allow Gradle to sync dependencies.
3. **Run Unit Tests**:
   ```bash
   ./gradlew testDebugUnitTest
   ```
4. **Build Debug APK**:
   ```bash
   ./gradlew assembleDebug
   ```
5. **Install on Device / Emulator**:
   Select the `:app` target in Android Studio and click **Run** (`Shift + F10`).

---

## 🔒 Permissions & Privacy

Planora requires minimal system permissions to deliver exact alerts and offline features:

- `POST_NOTIFICATIONS`: Required on Android 13+ (API 33+) to display reminder notifications.
- `SCHEDULE_EXACT_ALARM` & `USE_EXACT_ALARM`: Required for precise time-based task reminders.
- `RECEIVE_BOOT_COMPLETED`: Automatically restores scheduled alarms after device restarts.
- `USE_FULL_SCREEN_INTENT`: Displays full-screen alarm alerts on lock screen for high-priority reminders.
- `VIBRATE`: Provides tactile haptic feedback during timer and alarm alerts.

> [!NOTE]
> Planora requires **NO internet permission** (`android.permission.INTERNET`). Your data stays 100% local and safe on your device.

---

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

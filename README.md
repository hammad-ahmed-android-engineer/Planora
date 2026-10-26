# 📝 Planora - Smart Offline Task & Productivity Planner

[![Kotlin](https://img.shields.io/badge/Kotlin-2.4.21-purple.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Android Gradle Plugin](https://img.shields.io/badge/AGP-9.4.1-blue.svg?style=flat&logo=android)](https://developer.android.com/studio/releases/gradle-plugin)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-BOM%202026.09.00-green.svg?style=flat&logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material%203-Expressive-orange.svg?style=flat&logo=materialdesign)](https://m3.material.io/)
[![Clean Architecture](https://img.shields.io/badge/Architecture-Clean%20%2B%20MVVM%20%2B%20UDF-brightgreen.svg?style=flat)](#-architecture--design-patterns)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](LICENSE)

**Planora** is an offline-first, native Android task management and productivity application. Built with **Kotlin**, **Jetpack Compose (Material 3)**, and strict **Clean Architecture**, Planora empowers users to organize daily responsibilities, schedule time-critical reminders, build consistent productivity streaks, and complete distraction-free focus sessions—all with zero cloud tracking and no account registration.

---

## 🌟 Overview & Philosophy

Planora operates on three core principles:
1. **100% Offline & Privacy-First**: All personal data, checklists, statistics, and focus logs stay strictly on your local device. Zero tracking, zero telemetry, zero cloud dependencies.
2. **Seamless Productivity Flow**: From smart quick-add task entry to custom Pomodoro timers and interactive calendar agendas, every workflow is designed for frictionless interaction.
3. **Engineering Excellence**: Built with strict Clean Architecture, state hoisting, responsive dual-orientation layouts, and an explicit **1:1 file-to-composable decomposition** across every UI screen and component.

---

## ✨ Core Features & Capabilities

### 📋 1. Comprehensive Task & Checklist Management
- Create tasks with rich descriptions, due dates, due times, recurrence rules, priority levels, and category assignments.
- **Priority Classification**: `Urgent`, `High`, `Medium`, `Low`, and `None` with distinct color accents.
- **Interactive Subtasks**: Visual checklist progress tracking with completion percentages and one-tap toggling.
- **Task Organization**: Dedicated views for `Today`, `Pinned`, `Overdue`, `All Tasks`, `Favorites`, `Categories`, `Archive`, and `Trash Bin` (with restore & empty bin support).

### ⚡ 2. Smart Quick Add & Auto-Detection
- Instant task input bar supporting shorthand commands and auto-detected attributes:
  - **Priorities**: `!urgent`, `!high`, `!medium`, `!low`
  - **Tags**: `#Work`, `#Personal`, `#Health`
  - **Due Dates**: `today`, `tomorrow`, `next monday`, `tonight`
  - **Times**: `at 5 PM`, `10:30am`, `18:00`
- Example: `"Review pull request tomorrow at 4pm !urgent #Engineering"` automatically extracts the title, due date, time, priority, and tags in real time.

### 🔁 3. Smart Recurrence Engine
- Recurring task schedules: **Daily**, **Weekdays (Mon–Fri)**, **Weekends**, **Weekly**, **Monthly**, **Yearly**, and **Custom Intervals**.
- Preserves historical completions while scheduling the next occurrence upon task completion.

### ⏳ 4. Pomodoro Focus Timer
- Integrated focus session management with three modes:
  - **Work Session** (customizable, default 25 min)
  - **Short Break** (customizable, default 5 min)
  - **Long Break** (customizable, default 15 min)
- Circular animated progress dial with haptic feedback, session counter, and daily/weekly focus minutes tracking.

### 📊 5. Productivity Streaks & Analytics
- Visual analytics dashboard:
  - Overall completion rate percentage.
  - Active daily streak and all-time best record streak.
  - Focus session volume for today and this week.
  - Distribution breakdown by task category and priority.

### 📅 6. Calendar Agenda & Smart Search
- Monthly calendar grid with color-coded task indicator dots and day-by-day agenda schedules.
- Multi-faceted search engine filtering by text query, priority chips, and active categories with instant debounced updates.

### 📝 7. Reusable Task Templates
- Create custom routine blueprints (e.g., *Weekly Review*, *Gym Workout*, *Sprint Planning*) with pre-configured subtasks, priority, and relative due offsets.
- Instantiate new tasks from templates in one tap.

### 🔔 8. Dual Reminder & Alarm System
- **Standard Notification Bar**: High-priority alert banner with quick action buttons: **Mark Complete** and **Snooze (15m)**.
- **Full-Screen Alert Window**: Instant wake-up alert window displayed over the lock screen for time-critical alarms (`FullScreenAlertActivity`).
- **Android 14+ Ready**: Built-in verification and direct shortcut to grant `USE_FULL_SCREEN_INTENT` Special App Access.
- **Device Reboot Resilience**: `BootReceiver` automatically reschedules all active alarms upon reboot or timezone updates.

### 🎨 9. Material 3 Personalization
- Dynamic **Light Mode**, **Dark Mode**, and **System Default** themes.
- Selectable accent colors (Indigo, Emerald, Violet, Amber, Rose, Cyan).
- Responsive portrait and landscape layouts with dedicated split-pane configurations.

### 📱 10. Home Screen Widget
- Glanceable task widget (`PlanoraWidgetProvider`) providing direct overview of pending tasks from the Android launcher.

---

## 📐 Architecture & Design Patterns

Planora follows modern Android architectural standards and **Clean Architecture**:

```
com.example.planora/
├── data/                              # DATA LAYER
│   ├── local/                         # Room Database, DAOs, Entities
│   │   ├── dao/                       # TaskDao, CategoryDao, TagDao, TaskTemplateDao
│   │   └── entity/                    # TaskEntity, CategoryEntity, TagEntity, etc.
│   ├── mapper/                        # Entity <-> Domain Model bidirectional mappers
│   ├── preferences/                   # Jetpack DataStore & direct SharedPreferences
│   └── repository/                    # Repository implementations
│
├── di/                                # DEPENDENCY INJECTION (Hilt)
│   ├── DatabaseModule.kt              # Room DB, DAOs, and CoroutineDispatchers providers
│   └── RepositoryModule.kt            # Clean @Binds interface-to-implementation bindings
│
├── domain/                            # DOMAIN LAYER (Pure Kotlin)
│   ├── model/                         # Domain models (Task, Category, Priority, RecurrenceType, etc.)
│   ├── repository/                    # Clean Repository interfaces
│   └── usecase/                       # Single-responsibility use cases
│       ├── CalculateProductivityStatsUseCase.kt
│       ├── DeleteTaskUseCase.kt
│       ├── GetTasksUseCase.kt
│       ├── ManageCategoriesUseCase.kt
│       ├── ManageFocusUseCase.kt
│       ├── ManagePreferencesUseCase.kt
│       ├── ManageTagsUseCase.kt
│       ├── ManageTemplatesUseCase.kt
│       ├── NaturalLanguageTaskParser.kt
│       ├── RecurrenceCalculator.kt
│       ├── SaveTaskUseCase.kt
│       └── ToggleTaskCompletionUseCase.kt
│
├── notification/                      # ALARMS & NOTIFICATIONS
│   ├── AlarmScheduler.kt              # Exact AlarmManager scheduler
│   ├── FullScreenAlertActivity.kt     # Lockscreen alert host activity
│   ├── components/                    # FullScreenAlertContent composable
│   ├── TaskReminderReceiver.kt        # Alarm broadcast handler
│   ├── TaskActionReceiver.kt          # Inline notification action receiver
│   ├── StreakNotificationReceiver.kt  # Daily streak reminder receiver
│   └── BootReceiver.kt                # Reboot & timezone recovery receiver
│
├── ui/                                # PRESENTATION LAYER (Jetpack Compose)
│   ├── components/                    # Shared design system components (AppTypography, TaskItemCard, etc.)
│   ├── navigation/                    # NavGraph, NavHostContainer, Screen destinations
│   ├── screens/                       # 11 Modular Screen Packages
│   │   ├── calendar/                  # CalendarRoute, CalendarScreen, components/
│   │   ├── categories/                # CategoryRoute, CategoryScreen, components/
│   │   ├── focus/                     # FocusTimerRoute, FocusTimerScreen, components/
│   │   ├── home/                      # HomeRoute, HomeScreen, components/
│   │   ├── search/                    # SearchFilterRoute, SearchFilterScreen, components/
│   │   ├── settings/                  # SettingsRoute, SettingsScreen, components/
│   │   ├── splash/                    # SplashScreen, components/
│   │   ├── stats/                     # StatsRoute, StatsScreen, components/
│   │   ├── taskdetail/                # TaskDetailRoute, TaskDetailScreen, components/
│   │   ├── taskeditor/                # TaskEditorRoute, TaskEditorScreen, components/
│   │   ├── taskview/                  # TaskViewRoute, TaskViewScreen, components/
│   │   └── templates/                 # TemplatesRoute, TemplatesScreen, components/
│   └── theme/                         # Theme, Color, Dimensions, Typography
│
├── util/                              # CATEGORIZED KOTLIN EXTENSIONS
│   ├── DateTimeExtensions.kt          # Date/time formatting, ISO conversion, greeting
│   ├── ColorExtensions.kt             # Color hex parsing, fallback, priority color
│   ├── StringExtensions.kt            # Title validation, recurrence labels
│   └── UiExtensions.kt                # Debounced clicks, fullscreen permissions
│
└── widget/                            # APP WIDGET
    └── PlanoraWidgetProvider.kt       # AppWidgetProvider implementation
```

### Key Engineering Practices:
- **Strict 1:1 Composable Rule**: Every composable function lives in its own dedicated `.kt` file matching the function name. No file contains multiple composables.
- **Route vs. Screen Pattern**: Each screen is segregated into a stateful `*Route` (handles ViewModel injection via Hilt and event dispatching) and a stateless `*Screen` (pure UI receiving state and lambda callbacks).
- **Unidirectional Data Flow (UDF)**: ViewModels expose immutable `StateFlow<UiState>` collected with `collectAsStateWithLifecycle()`.
- **Debounced Interactions**: User actions use a 500ms debounce guard (`clickableDebounced` / `rememberDebouncedClick`) to prevent duplicate triggers.
- **Clean Extension Architecture**: All utilities in `util/` are organized into 4 domain-focused extension files without arbitrary `object` singleton classes.

---

## 🛠 Tech Stack & Dependencies

All dependencies are centrally managed via Gradle Version Catalog ([`libs.versions.toml`](gradle/libs.versions.toml)):

| Category | Library / Dependency | Version | Purpose |
| :--- | :--- | :--- | :--- |
| **Language** | [Kotlin](https://kotlinlang.org/) | `2.4.21` | Modern, expressive, type-safe programming |
| **Build System** | [Android Gradle Plugin (AGP)](https://developer.android.com/build) | `9.4.1` | Project build configuration |
| **Annotation Processing** | [KSP (Kotlin Symbol Processing)](https://kotlinlang.org/docs/ksp-overview.html) | `2.3.12` | High-performance code generation for Room & Hilt |
| **UI Framework** | [Jetpack Compose BOM](https://developer.android.com/develop/ui/compose/bom) | `2026.09.00` | Declarative UI toolkit |
| **Design System** | [Material 3](https://developer.android.com/develop/ui/compose/components) | BOM Managed | Expressive Material Design components |
| **Icons** | Material Icons Extended | BOM Managed | Comprehensive icon set |
| **Dependency Injection** | [Dagger Hilt](https://dagger.dev/hilt/) | `2.60.1` | Dependency injection framework |
| **Hilt Navigation** | Hilt Navigation Compose | `1.4.0` | ViewModel injection in Navigation destinations |
| **Navigation** | [Navigation Compose](https://developer.android.com/guide/navigation) | `2.10.2` | Single-activity Compose navigation |
| **Local Database** | [Room Database (Runtime + KTX)](https://developer.android.com/training/data-storage/room) | `2.8.5` | SQLite object mapping with Flow support |
| **Preferences** | [Jetpack DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) | `1.3.0` | Asynchronous key-value storage |
| **Lifecycle** | Lifecycle Runtime Compose & ViewModel | `2.11.0` | Lifecycle-aware UI state management |
| **Concurrency** | Kotlin Coroutines Android | `1.11.0` | Asynchronous background execution |
| **Background Work** | [WorkManager KTX](https://developer.android.com/topic/libraries/architecture/workmanager) | `2.12.0` | Deferrable background tasks |
| **Serialization** | [Google Gson](https://github.com/google/gson) | `2.14.0` | Local JSON backup and restore |
| **Responsive Units** | [SDP & SSP Android](https://github.com/intuit/sdp) | `1.1.1` | Scalable size and font dimensions |
| **Testing** | JUnit 4, AndroidX JUnit, Espresso, MockK, Coroutines Test | `4.13.2` / `1.14.11` | Unit and UI integration testing |

---

## ⚡ Smart Quick Add Syntax Reference

When typing into the Quick Task Input Bar, Planora parses attributes automatically:

| Example Input | Parsed Output |
| :--- | :--- |
| `Buy groceries tomorrow at 5 PM !high #Shopping` | **Title**: Buy groceries<br>**Due Date**: Tomorrow<br>**Due Time**: 17:00<br>**Priority**: High<br>**Tag**: Shopping |
| `Team standup meeting next monday at 10am !urgent #Work` | **Title**: Team standup meeting<br>**Due Date**: Next Monday<br>**Due Time**: 10:00<br>**Priority**: Urgent<br>**Tag**: Work |
| `Evening yoga session tonight !medium #Fitness` | **Title**: Evening yoga session<br>**Due Date**: Today<br>**Priority**: Medium<br>**Tag**: Fitness |

---

## 🚀 Getting Started & Git Instructions

### Prerequisites
- **Android Studio**: Android Studio Ladybug (2024.2+) or newer.
- **Java Development Kit (JDK)**: JDK 17, JDK 21, or JDK 25.
- **Android SDK Requirements**:
  - `compileSdk`: `37`
  - `targetSdk`: `37`
  - `minSdk`: `26` (Android 8.0 Oreo or higher)

### 1. Clone the Repository
```bash
git clone https://github.com/hammad-ahmed-android-engineer/Planora.git
cd Planora
```

### 2. Open Project in Android Studio
1. Launch Android Studio.
2. Select **File > Open...** and navigate to the cloned `Planora` directory.
3. Allow Gradle to synchronize dependencies.

### 3. Build & Run via Command Line

#### Compile Kotlin Sources:
```bash
# Windows
.\gradlew.bat compileDebugKotlin

# macOS / Linux
./gradlew compileDebugKotlin
```

#### Run Unit Tests:
```bash
# Windows
.\gradlew.bat test

# macOS / Linux
./gradlew test
```

#### Assemble Debug APK:
```bash
# Windows
.\gradlew.bat assembleDebug

# macOS / Linux
./gradlew assembleDebug
```
The output APK is generated at:
```
app/build/outputs/apk/debug/app-debug.apk
```

#### Install Directly onto a Connected Device / Emulator:
```bash
# Windows
.\gradlew.bat installDebug

# macOS / Linux
./gradlew installDebug
```

---

## 🔒 Permissions & Security

Planora requests only the minimal permissions necessary for time-exact notifications and alarm triggers:

- `android.permission.POST_NOTIFICATIONS`: Prompts for notification permission on Android 13+ (API 33+).
- `android.permission.SCHEDULE_EXACT_ALARM`: Ensures exact-time alarms for scheduled task deadlines.
- `android.permission.USE_EXACT_ALARM`: Enables exact alarms for offline task reminders.
- `android.permission.USE_FULL_SCREEN_INTENT`: Displays the alarm window over the lock screen for high-priority reminders.
- `android.permission.RECEIVE_BOOT_COMPLETED`: Automatically reschedules reminders after device reboots.
- `android.permission.VIBRATE`: Provides haptic feedback during alarms and timers.

> [!IMPORTANT]
> Planora requires **NO internet permission** (`android.permission.INTERNET`). Your tasks, notes, checklists, streaks, and analytics remain strictly on your physical device.

---

## 🤝 Contributing

Contributions, feedback, and issue reports are welcome!
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/amazing-feature`).
3. Commit your changes (`git commit -m "Add amazing feature"`).
4. Push to the branch (`git push origin feature/amazing-feature`).
5. Open a Pull Request.

---

## 📄 License

This project is open-source and distributed under the terms of the [Apache License 2.0](LICENSE).

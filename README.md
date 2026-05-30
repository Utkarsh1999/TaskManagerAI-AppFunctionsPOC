# TaskManager AI

TaskManager AI is an Android task manager built with Jetpack Compose, Room, and Android AppFunctions. It keeps task data on-device and exposes a small set of AI-friendly actions so a supported assistant can add tasks, complete tasks, and summarize today’s workload.

## What it does

- Add tasks from the app UI or through supported AI voice commands
- Mark tasks as completed
- View all tasks in a simple Compose dashboard
- Store tasks locally with Room
- Expose AppFunctions for assistant-driven actions such as:
  - add a task
  - complete a task
  - summarize today’s tasks

## Tech Stack

- Kotlin
- Jetpack Compose
- Material 3
- Room
- Kotlin Coroutines
- Android AppFunctions

## Project Structure

- `app/src/main/java/dev/utkarsh/taskmanagerai/ui/` - Compose UI, screen state, and ViewModel
- `app/src/main/java/dev/utkarsh/taskmanagerai/data/` - Room entity, DAO, database, and repository
- `app/src/main/java/dev/utkarsh/taskmanagerai/appfunctions/` - AppFunctions exposed to supported assistants
- `app/src/main/res/` - app resources, theme, and AppFunctions metadata

## Requirements

- Android Studio with Kotlin and Android SDK support
- JDK 17
- Android SDK 36
- An Android device or emulator running API 26 or newer for the app UI

## Build and Run

1. Open the project in Android Studio.
2. Let Gradle sync and download dependencies.
3. Run the `app` configuration on a device or emulator.

Or from the command line:

```bash
./gradlew assembleDebug
```

The debug APK is generated under:

```text
app/build/outputs/apk/debug/
```

## How It Works

The app uses a single-activity Compose setup. `MainActivity` hosts `TaskListScreen`, which talks to `TaskViewModel`. The ViewModel reads and writes tasks through `TaskRepository`, which wraps Room’s `TaskDao`.

AppFunctions are registered through the Android manifest and metadata in `app/src/main/res/xml/app_functions_metadata.xml`. This lets a supported assistant invoke task actions without opening the app.

## Notes on Voice and AppFunctions

The AppFunctions layer is intended for supported assistant integrations on compatible devices. The command parsing is intentionally simple, so the best results come from direct phrases like:

- “Add buy groceries to my tasks”
- “Complete buy groceries”
- “Summarize my tasks for today”

See `KNOWN_ISSUES.md` for current limitations around exact title matching and assistant parsing.

## Known Limitations

- Task completion uses exact title matching
- Similar task names are not disambiguated
- Assistant phrasing can affect how titles are parsed
- AppFunctions availability depends on device and platform support

## License

This project is licensed under the MIT License. See `LICENSE` for details.

# ChicksEvent - Event Management App

A comprehensive Android application for managing events, waiting lists, and participant registration. Built with Firebase Realtime Database, featuring QR code generation, geolocation tracking, and admin/organizer management capabilities.

## Features

### Core Functionality
- **Event Management**: Create, update, and manage events with detailed information
- **Waiting List System**: Join events with automatic lottery/pooling for participant selection
- **QR Code Integration**: Generate and scan QR codes for quick event access
- **Geolocation Support**: Optional location tracking for event participants
- **Notifications**: Real-time notifications for event updates and status changes
- **Admin Panel**: Comprehensive admin interface for managing users, events, and content

### User Roles
- **Regular Users**: Browse events, join waiting lists, receive notifications
- **Organizers**: Create and manage events, run lotteries, export participant data
- **Admins**: Full system access including user management and content moderation

## Tech Stack

- **Language**: Java
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Build System**: Gradle with Kotlin DSL
- **Backend**: Firebase Realtime Database
- **Storage**: Firebase Storage
- **Maps**: OSMDroid
- **QR Codes**: ZXing
- **Image Loading**: Glide
- **Architecture**: Fragment-based with Navigation Component

## Project Structure

```
code/app/src/main/java/com/example/chicksevent/
├── adapter/          # RecyclerView/ListView adapters
├── enums/            # Enumeration classes
├── fragment/         # User-facing fragments
├── fragment_admin/   # Admin panel fragments
├── fragment_org/     # Organizer fragments
├── misc/             # Domain models (Event, User, Entrant, etc.)
└── util/             # Utility classes (QRCodeGenerator, DateFormatter, etc.)
```

## Setup Instructions

### Prerequisites
- Android Studio (latest version recommended)
- JDK 11 or higher
- Firebase project with Realtime Database enabled
- Google Services JSON file (`google-services.json`)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd event-management-app
   ```

2. **Add Firebase Configuration**
   - Place your `google-services.json` file in `code/app/`
   - Ensure Firebase Realtime Database is enabled in your Firebase console

3. **Build the project**
   ```bash
   cd code
   ./gradlew build
   ```

4. **Run the app**
   - Open the project in Android Studio
   - Connect an Android device or start an emulator
   - Click "Run" or use `./gradlew installDebug`

## Building

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

The release build includes:
- ProGuard/R8 code shrinking and obfuscation
- Optimized APK size
- Minified resources

## Architecture

### Data Models
- **Event**: Represents an event with dates, limits, and metadata
- **User**: User profile and preferences
- **Entrant**: Participant in an event's waiting list
- **Organizer**: Event creator/manager
- **Notification**: User notifications
- **Admin**: Administrative user with elevated permissions

### Key Components
- **FirebaseService**: Wrapper for Firebase Realtime Database operations
- **DateFormatter**: Thread-safe date formatting utilities
- **StringUtils**: String manipulation helpers
- **QRCodeGenerator**: QR code generation and deep linking
- **AppConstants**: Application-wide constants

## Testing

The project includes comprehensive unit tests:
```bash
./gradlew test
```

For instrumented tests:
```bash
./gradlew connectedAndroidTest
```

## Configuration

### Constants
Application-wide constants are defined in `AppConstants.java`:
- Date formats
- Time formats
- Maximum input lengths
- Timeout values
- Permission request codes

### String Resources
User-facing strings are defined in `res/values/strings.xml` for internationalization support.

## Permissions

The app requires the following permissions:
- `ACCESS_FINE_LOCATION`: For geolocation features
- `ACCESS_COARSE_LOCATION`: For approximate location
- `CAMERA`: For QR code scanning

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

### Code Style
- Follow Android Kotlin/Java style guide
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods focused and single-purpose

## Known Issues

See [IMPROVEMENTS.md](IMPROVEMENTS.md) for a comprehensive list of known issues and improvement suggestions.

## License

See [LICENSE](LICENSE) file for details.

## Authors

- Jordan Kwan
- Jinn Kasai
- Juan Rea
- ChicksEvent Team

## Acknowledgments

- Firebase for backend services
- OSMDroid for map functionality
- ZXing for QR code support
- Material Design Components

---

For detailed technical documentation, see the `javadoc/` directory.

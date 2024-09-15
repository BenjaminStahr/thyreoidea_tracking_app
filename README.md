# Thyreoidea Tracker
Thyreoidea Tracker is a therapy support app developed during my bachelor's thesis to help individuals monitor Hashimoto's Thyroiditis, a chronic autoimmune disease. The app assists users in tracking medication intake, symptoms, and supplements, while providing data visualization to support informed health decisions.

## Functionality

Thyreoidea Tracker includes three main features:
- **Medicine Intake Logging**: Track and log daily medication usage.
- **Symptom Tracking**: Record symptoms over time for analysis.
- **Supplement Tracking**: Log food supplements and their effects.
## Technology

## Architecture

The whole app is developed for the Android operating system and is implemented in Java. 
Thyreoidea Tracker follows the MVC (Model-View-Controller) architecture:
- **Controller**: `MainActivity` acts as the controller, handling user input and app navigation.
- **View**: Fragments and dialogs make up the view, providing the UI for interacting with data.
- **Model**: `DataHolder` stores all user data, serialized to JSON for persistence.

![App Architecture Diagram](https://github.com/BenjaminStahr/hashimoto_app_2020/blob/images/hashimoto_app_architecture.png)

## Setup

- Android Studio 3.6 (newer versions are not compatible)
- Android Gradle Plugin: 3.5.2
- Gradle Version: 5.4.1
- Java: 8

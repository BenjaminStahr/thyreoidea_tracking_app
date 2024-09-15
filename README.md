# Thyreoidea Tracker
Thyreoidea Tracker is a therapy support app developed during my bachelor's thesis to help individuals monitor Hashimoto's Thyroiditis, a chronic autoimmune disease. It is designed to assist individuals with Hashimoto's Thyroiditis by helping them monitor their symptoms, thyroid hormone levels, and supplement intake. The app provides a detailed disease progression view through easy-to-use tracking and data visualization features.

## Setup

- Android Studio 3.6 (newer versions are not compatible)
- Android Gradle Plugin: 3.5.2
- Gradle Version: 5.4.1
- Java: 8

## Functionality

Thyreoidea Tracker includes four main features:
- **Symptom Logging**: Users can track the intensity and occurrence of symptoms over time.
- **Thyroid Hormone Tracking**: Input thyroid hormone values (TSH, fT3, fT4) and monitor their progression.
- **Supplement Tracking**: Record the intake of supplements such as selenium or vitamin D.
- **Data Visualization**: Graphical representation of tracked symptoms, hormone levels, and supplement intake over weekly, monthly, or yearly periods.

## Architecture

The app is developed for the Android operating system and implemented in Java. 
Thyreoidea Tracker follows the MVC (Model-View-Controller) architecture:
- **Controller**: `MainActivity` acts as the controller, handling user input and app navigation.
- **View**: Fragments and dialogs make up the view, providing the UI for data interaction.
- **Model**: `DataHolder` stores all user data, serialized to JSON for persistence.

The following diagram provides an overview of how different classes are related to each part of the MVC architecture:

![App Architecture Diagram](https://github.com/BenjaminStahr/hashimoto_app_2020/blob/images/hashimoto_app_architecture.png)

## Clinical Validation

The Thyreoidea Tracker app was evaluated in a clinical study involving five participants over four weeks. The study demonstrated that the app is effective in accurately tracking the progression of Hashimoto's Thyroiditis.

# Thyreoidea Tracker

This app was developed during my bachelor's thesis. Its purpose is therapy support for people having 
Hashimoto Thyreoditis, which is a chronic disease. 

## Setup

Android Studio: 3.6, newer versions are currently not compatible.
Other dependencies should be loaded automatically.

Android Gradle Plugin Version: 3.5.2
Gradle Version: 5.4.1
Java: 8

## Functionality

The app includes three different main functions. These are the logging of medicine intake, occurring symptoms and food supplements.
For each of these components, a visualization is implemented and several dialogs to input the required data.
Internally an interface to send documented data to a server is integrated, because the functionality was evaluated remotely.

## Technology

The whole app is developed for the Android operating system and is implemented in Java. Data is stored as serialized JSON.

## Architecture

The organization of modules inside the app follows the MVC-Pattern.
The app is organized into one activity, which functions as the controller. This controller is implemented by an instance of the class MainActivity.
Most classes are comprised in the view which consists of fragments and different dialogs for extending the internal data structure. The class DataHolder
is instantiated once and includes all user data, which is serialized for storing.
![Here a picture from the overall architecture should be shown](https://github.com/BenjaminStahr/hashimoto_app_2020/blob/images/hashimoto_app_architecture.png)

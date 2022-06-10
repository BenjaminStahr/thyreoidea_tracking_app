# Thyreoidea Tracker

This app was developed during my bachelor thesis. Its purpose is therapy support for people having 
Hashimoto Thyreoditis, which is a chronic disease. 

## Functionality

The app includes three different main functions. These are the logging of medicine intake, occurring symptoms and food supplements.
For each of these components a visualization is implemented and several dialogs to input the required data.
Internally a interface to send documented data to a server is integrated, because the functionality was evaluated remotely.

## Technology

The whole app is developed for the Android operating system and is implemented in Java. Data is stored as serialized JSON.

## Architecture

The organization of modules inside the app is following the MVC-Pattern.
The app is organzized in one activity, which functions as the controller. This controller is implemented by an instance of the class MainActivity.
The most classes are comprised in the view which consists of fragments and different dialogs for extending the internal data structure. The class DataHolder
is instaniated once and includes all user data, which is serialized for storing.
![Here a picture from the overall architecture should be shown](https://github.com/BenjaminStahr/hashimoto_app_2020/blob/images/hashimoto_app_architecture.png)

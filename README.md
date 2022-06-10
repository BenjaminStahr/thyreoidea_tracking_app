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

https://github.com/BenjaminStahr/hashimoto_app_2020/blob/fc1e46c163ddfa90220fec1954cc95af975048ca/hashimoto_app_architecture.png


<h1 align="center">Finboost Mobile App (Front End)</h1>
<p align="center">This repository is the main entry for user to use the app. Mainly the app  is built using Kotlin Native, MDC and Jetpack Compose for UI purpose</p>

## Table of Contents

- [Tech Stack](#tech-stack)
- [Architecture File and Folder](#architecture-file-and-folder)

## Tech Stack

- [Kotlin Android](https://kotlinlang.org/) (`Native`): Main programming language for developing the mobile application
- [Room DB](https://developer.android.com/training/data-storage/room) (`Local Storage`): To test and save the dummy data while waiting the API.
- [MDC](https://m3.material.io/develop/android/mdc-android) (`UI Framework`): Also like Jetpack Compose, MDC will give more option while developing the UI
- [Retrofit](https://square.github.io/retrofit/)(`API Library`): Retrofit are android library to make easier when interacting with API
- [GSON Converter](https://github.com/square/retrofit/tree/trunk/retrofit-converters/gson) (`API Library`): For converting response API
- [Glide](https://github.com/bumptech/glide)(`Image Loading Framework`): To load image from network
- [Dots Indicator](https://github.com/tommybuonomo/dotsindicator) (`Indicator`): To tell the user where are they on
- [Flexbox Layoyt](https://github.com/google/flexbox-layout)(`Layouting`): To adjust complex layout
- [Shimmer](https://github.com/facebookarchive/shimmer-android)(`UI Library`): For making shimmer effect while loading the content

## Architecture File and Folder
| File/Folder Name    | Description                                                                                                                                                                                      |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| `app/kotlin+java//main/wafie/finboost_frontend/MainActivity.kt`        | Application starter, the entry point for accesss the  application                                                                                                                                         |
| `app/kotlin+java//main/wafie/finboost_frontend/data` | Folder destination for all resources that will be used for interacting with the API
| `app/kotlin+java//main/wafie/finboost_frontend/data/api`    | Folder that contain API config and API endpoint |
| `app/kotlin+java//main/wafie/finboost_frontend/data/model`    | Folder that contain many model to make some adjusment for requesting request on API body |
| `app/kotlin+java//main/wafie/finboost_frontend/data/preferences`       | Folder that contain user preferences and included function to save user session, get user session and clear user session          |
| `app/kotlin+java//main/wafie/finboost_frontend/ui`    | Folder that contain any resources that related with the UI interaction                |
| `app/kotlin+java//main/wafie/finboost_frontend/ui/auth`        | Folder that contain authentication screen  and logic                                                                                                                                                            |
| `app/kotlin+java//main/wafie/finboost_frontend/ui/chat`    | Folder that contain UI and Logic for Chat with expert and with Fin Ai                                                                                                     |
| `app/kotlin+java//main/wafie/finboost_frontend/ui/home`         | Folder that contain expert content                                                                                                                                  |
| `app/kotlin+java//main/wafie/finboost_frontend/onboarding`            | Folder that contain onboarding screen                                                                                                                        |
| `app/kotlin+java//main/wafie/finboost_frontend/ui/profile`      | Folder that contain any UI profile related                                                                                                                                         |
| `app/res/drawable`           | Folder that contain any assets like icon, pict and custom xml file                                                                                                   |
| `app/res/layout`           | Folder that contain that UI layout for the appplication                                                                                                               |
| `Graddle Scripts/build.gradle.kts`           | This file is used for management the whole library                                                                                                               |


## User Flow Diagrams

![User Mobile App Flow](https://github.com/Finboost/finboost/blob/main/userflow.png)

## App Overview
### Onboarding Screen
![Onboard Screen](https://github.com/Finboost/finboost/blob/main/onboard.png)
### Mobile app MVP
![MVP](https://github.com/Finboost/finboost/blob/main/mvp.png)

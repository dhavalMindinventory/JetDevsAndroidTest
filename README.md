# BaseStructureWithHiltCoroutine

[![N|Solid](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/05/android_kotlin.png)](https://d3nmt5vlzunoa1.cloudfront.net/kotlin/files/2017/05/android_kotlin.png)

BaseStructureWithHiltCoroutine for Android is using Kotlin as primary language, MVVM design pattern, Uncle Bob's clean architecture and the latest components from Jetpack library.

The Uncle Bob's clean architecture approach.
  - [Architecting Android..the clean way](https://fernandocejas.com/2014/09/03/architecting-android-the-clean-way/)
  - [Architecting Android..the evolution](http://fernandocejas.com/2015/07/18/architecting-android-the-evolution/)
  - [Clean Architecture..Dynamic parameters in UseCase](http://fernandocejas.com/2016/12/24/clean-architecture-dynamic-parameters-in-use-cases/)

## Project Environment

### Build Types

* Debug : Proguard disabled
* Release : Proguard enabled

### Product Flavors

1. Mock
2. Development
3. Staging
4. Production

Developers can change Flavours as per project requirement

### Setup Ktlint

**[Ktlint](https://github.com/pinterest/ktlint)** - Kotlin lint checker and formatter
   * **[Setup](https://github.com/pinterest/ktlint#installation)** - On macOS or Linux brew can be used 
    `brew install ktlint`
   * **[Add ktlint to Android Studio](https://github.com/pinterest/ktlint#option-1-recommended)**
    `ktlint --android applyToIDEAProject`
   * To run formatter - `./gradlew  ktlintFormat`.

## Code review
  * Developer has to create a separate branch for each task/ticket.
  * Before create a PR, developer need to follow the action below:
    * Execute `./gradlew  ktlintFormat`
    * If execution is failed , developer need to fix the code issues based on the report from ktlint
    * This cycle should be followed until the project is getting build successfully

### Notes
1. Always use `navController.safeNavigate()` for navigation
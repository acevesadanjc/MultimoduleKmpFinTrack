This is a Kotlin Multiplatform project targeting Android, iOS.

* [/iosApp](./iosApp/iosApp) contains an iOS application. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* [/shared](./shared/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./shared/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./shared/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./shared/src/jvmMain/kotlin)
    folder is the appropriate location.

### Running the apps

Use the run configurations provided by the run widget in your IDE's toolbar. You can also use these commands and options:

- Android app: `./gradlew :androidApp:assembleDebug`
- iOS app: open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…

## Integracion de navegacion con Host

Splash es una Feature compartida: no conoce el flavor ni decide directamente que Feature debe abrirse. `SplashScreen` expone callbacks y el Host conecta uno de ellos en `SplashNavigation`. Hay dos opciones intencionales:

- **Destino fijo Welcome:** `SplashViewModel` emite `NavigateToWelcome`; el callback `onNavigateToWelcome` pide `getWelcomeScreen()` al `NavigationFeaturesRouter`.
- **Destino seleccionado por flavor:** `SplashViewModel` emite `NavigateToNextScreen`; el callback `onNavigateToNextScreen` pide `getScreenAfterSplash()`. La implementacion de `NavigationFeaturesRouter` de cada flavor/source set devuelve el destino que corresponde. Por ejemplo, Ecuador puede devolver Login y CENAM Welcome.

Conecta el evento emitido con el callback de la opcion elegida; no se deben disparar ambos flujos para una misma finalizacion de Splash. Host es responsable de proveer una implementacion para cada variante Android y source set iOS. La navegacion usa `replaceAll` para que Atrás no regrese a Splash.
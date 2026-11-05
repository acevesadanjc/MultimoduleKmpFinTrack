# Introduccion al proyecto Host

Esta guia orienta a quienes comienzan a trabajar en **Host**, la aplicacion que integra los modulos Kotlin Multiplatform (KMP) de la suite. El `README.md` existente se conserva; este documento se enfoca en la estructura real del repositorio, su configuracion y el flujo para incorporar features.

## 1. Vista general

Host compone una aplicacion para Android e iOS. La logica y la interfaz compartidas viven en `shared`; `androidApp` proporciona el punto de entrada Android y `iosApp` el proyecto nativo de Xcode. Las pantallas y capacidades funcionales se mantienen como builds independientes bajo `features`.

El workspace debe conservar esta relacion de carpetas:

```text
MiClaro/
├── androidApp/
├── shared/
├── iosApp/
└── features/
    ├── core/
    └── <feature>/
```

Las rutas son importantes: `settings.gradle.kts` incluye builds bajo `features/`, y cada build de Feature incluye `../core`. Android Studio debe abrirse en la raiz de `MiClaro`. `includeBuild` los integra como builds compuestos; Core permanece como un build fuente independiente bajo `features`, no como una copia dentro de cada Feature.

## 2. Estructura del repositorio Host

| Ruta | Responsabilidad |
|---|---|
| `androidApp/` | Aplicacion Android, variantes Ecuador/Cenam, `Application`, inicializacion de Koin y adaptadores de navegacion por flavor. |
| `shared/` | Modulo KMP de la aplicacion: configuracion compartida, composicion de features, source sets de Android/iOS y configuracion de la variante iOS. |
| `iosApp/` | Aplicacion anfitriona nativa de Xcode; enlaza el framework `Shared` compilado desde `shared`. |
| `build-logic/convention/` | Plugin Gradle propio `com.kmp.commonversion`, publicado dentro del build incluido `build-logic`. |
| `gradle/libs.versions.toml` | Catalogo de versiones, plugins, dependencias y bundles de Host. |
| `settings.gradle.kts` | Repositorios, build logic, builds incluidos y modulos `:androidApp` y `:shared`. |
| `gradle.properties` | Ajustes de Kotlin/Native y rendimiento/configuracion de Gradle. |

Host solo declara `:androidApp` y `:shared` como subproyectos de su build principal. `build-logic` y cada feature se conectan mediante **composite builds** (`includeBuild`); no son carpetas fuente dentro de `shared`.

## 3. Como se integran Core y las Features

La composicion de la aplicacion sigue esta direccion:

```text
Host
  androidApp + shared
       |
       v
features/<feature>       (build KMP independiente)
  featureUi
  featureLogic
       |
       v
core                      (build KMP incluido por cada Feature)
  database | datastore | design | model
  navigation | network | utils
```

Host integra y selecciona Features para componer las apps; no declara dependencias directas a modulos Core. Cada build de Feature incluye Core desde `../core` y declara solo los modulos `com.kmp.core:*` que utiliza. Gradle resuelve esas dependencias contra el build local incluido. Core no depende de Host ni de una Feature concreta.

### 3.1 Composicion de un Feature

Un Feature encapsula una capacidad funcional y se compila como un modulo/build KMP independiente. La organizacion observada separa presentacion de logica de negocio:

| Area | Contenido habitual |
|---|---|
| `featureUi` | Pantallas y contenedores Compose, ViewModels, estado/eventos de UI, navegacion y modulo Koin del Feature. |
| `featureLogic` | Reglas propias del Feature, validaciones y casos de uso que no pertenecen a Core. |
| `commonMain` | Implementacion Kotlin que puede compartirse entre plataformas. |
| `androidMain` / `iosMain` | Adaptaciones que requieren APIs especificas de Android o iOS. |
| `commonTest` y source sets de test de plataforma | Pruebas de logica compartida o de comportamiento especifico de plataforma. |

Estas son convenciones utiles, no una plantilla que obligue a crear paquetes vacios. No hay una capa local `data` universal: cuando una capacidad compartida ya existe en Core, el Feature consume el modulo correspondiente; una capa de datos propia solo tiene sentido si contiene comportamiento especifico de esa funcionalidad.

### 3.2 Capas y modulos de Core

Core concentra capacidades reutilizables y agnosticas a Features. Sus modulos se agrupan por responsabilidad:

| Capa funcional | Modulo | Responsabilidad |
|---|---|---|
| Datos y persistencia | `database` | Persistencia relacional multiplataforma, DAOs, fuentes de datos y repositorios sobre Room. |
| Datos y persistencia | `datastore` | Preferencias y datos clave-valor, como configuraciones y estado de sesion. |
| Datos y persistencia | `network` | Acceso remoto con Ktor y las capas compartidas de datasource/repositorio para servicios. |
| Modelos compartidos | `model` | Entidades, modelos, solicitudes/respuestas y mappers usados entre capas. |
| Presentacion compartida | `design` | Tema, colores, recursos y componentes Compose reutilizables. |
| Navegacion | `navigation` | Contratos de navegacion que permiten conectar Features sin que se importen entre si. |
| Transversal | `utils` | Tipos de resultado/error de red, logging, extensiones y utilidades comunes. |

Un Feature depende solo de las capacidades Core que necesita. Por ejemplo, una pantalla que obtiene datos remotos y guarda preferencias puede consumir modulos diferentes de otra que solo necesita componentes de `design`; no se debe agregar todo Core indiscriminadamente.

### 3.3 Integracion en compilacion y ejecucion

1. **Compilacion:** `settings.gradle.kts` incluye cada build local desde `features/<feature>`. El `settings.gradle.kts` del Feature incluye `../core`, y el `build.gradle.kts` del Feature declara las dependencias a los modulos Core que usa. En Host, los bundles de dependencias determinan que Features se incorporan a cada mercado.
2. **Inyeccion:** cada Feature puede exponer su modulo Koin; Host registra los modulos de la aplicacion y de los Features seleccionados.
3. **Navegacion:** `core:navigation` define `NavigationFeaturesRouter`; Host aporta su implementacion y conecta las rutas disponibles sin crear dependencias directas entre Features.

#### Opciones de navegacion al terminar Splash

El modulo Splash es compartido y no debe decidir por si mismo que flavor esta ejecutandose. Hay dos opciones intencionales para continuar despues de Splash; son alternativas de flujo, no destinos que deban ejecutarse uno despues del otro:

1. **Ir directamente a Welcome:** el evento `NavigateToWelcome` activa `onNavigateToWelcome`, y `SplashNavigation` navega a `router.getWelcomeScreen()`. Usa esta opcion cuando todos los mercados deban mostrar Welcome despues del Splash.
2. **Elegir el destino por flavor:** el evento `NavigateToNextScreen` activa `onNavigateToNextScreen`, y `SplashNavigation` navega a `router.getScreenAfterSplash()`. El contrato `NavigationFeaturesRouter` no conoce el flavor; Host registra una implementacion por flavor/source set y ahi decide el destino. Por ejemplo, si Ecuador omite Welcome, Ecuador devuelve Login y CENAM devuelve Welcome.

Para usar una opcion, conecta el evento emitido por `SplashViewModel` con su callback correspondiente. La implementacion de `NavigationFeaturesRouter` debe mantener la misma politica para Android e iOS. Ambas rutas usan `navigator.replaceAll(...)` para retirar Splash de la pila. Los callbacks directos de otras pantallas, como `getLoginScreen()`, siguen siendo utiles para navegacion entre Features; no sustituyen la politica especifica de salida de Splash.

## 4. Android, iOS y flavors

### Android

`androidApp/build.gradle.kts` configura la dimension `market` y dos product flavors:

- **Ecuador**: application id con sufijo `.ecuador`, bundle de features Ecuador y `APP_FLAVOR = "ECUADOR"`.
- **Cenam**: application id con sufijo `.cenam`, bundle de features Cenam y `APP_FLAVOR = "CENAM"`.

`androidApp/src/main` contiene el arranque comun. Los directorios `androidApp/src/ecuador` y `androidApp/src/cenam` contienen implementaciones seleccionadas por Gradle para cada variante, como `HostApp` y `NavigationFeaturesRouterImpl`.

### iOS

`shared` compila para `iosArm64` e `iosSimulatorArm64`. En su configuracion KMP crea `iosMain` y selecciona uno de estos source sets:

- `iosEcuadorMain` cuando no se especifica la propiedad (valor por defecto `ECUADOR`).
- `iosCenamMain` con `-PiosFlavor=CENAM`.

Cada source set incorpora el bundle de features que corresponde a su mercado. La configuracion `kommonVersions.ios` declara los schemas `Ecuador` y `Cenam`, a partir de los cuales se generan archivos `.xcconfig` en `iosApp/Configuration`; el proyecto comparte los schemes de Xcode `Ecuador` y `Cenam`. Para compilar y ejecutar la app iOS se necesita macOS y Xcode.

## 5. Herramientas de configuracion

### Catalogo de versiones y Gradle

Las versiones comunes de Host se mantienen en `gradle/libs.versions.toml`; evita duplicar versiones en los modulos. La configuracion actual usa:

| Herramienta | Version/configuracion |
|---|---|
| Gradle Wrapper | Gradle `9.7.0` (`gradle/wrapper/gradle-wrapper.properties`) |
| JDK toolchain | Java `21` |
| Kotlin | `2.4.20` |
| Android Gradle Plugin | `9.3.2` |
| Compose Multiplatform | `1.12.1` |
| Android compile / min / target SDK | `37` / `24` / `37` |

El wrapper puede instalar la distribucion Gradle indicada si no esta disponible localmente. Para compilar Android tambien se requiere Android SDK; cada desarrollador configura su ruta local en `local.properties` o mediante `ANDROID_HOME`. No se debe subir `local.properties` al repositorio.

### Kommon Versions

`build-logic/convention` implementa el plugin `com.kmp.commonversion` y expone el bloque `kommonVersions { ... }`.

- En `shared/build.gradle.kts`, centraliza `namespace`, `applicationId`, versiones y definiciones de mercados Android; tambien declara los schemas iOS y los datos de la app.
- La tarea `generateKommonVersions` genera el contrato `KommonVersions` en `commonMain` y sus implementaciones para Android e iOS. En Host se activa desde `shared`; `androidApp` desactiva la generacion para no crear copias.
- En Android, `AppDelegate` inicializa flavor y build type en tiempo de ejecucion. En iOS, `initKoinIOS` inicializa el flavor.
- La configuracion iOS tambien genera los archivos `.xcconfig` asociados a los schemas.

Edita la configuracion Gradle y las plantillas de `build-logic` cuando cambie este mecanismo; no trates los archivos generados como el lugar principal para cambiar versiones o flavors.

### KMP Splash

El plugin externo `io.github.kmpbits.splash` se configura en `shared/build.gradle.kts` para integrar la pantalla nativa de inicio en Android e iOS: color, recurso del logo, animacion de salida y rutas de los proyectos. `MainActivity` extiende `SplashActivity`; `MainViewController` envuelve el contenido iOS con `SplashConfig`. Esta integracion de plataforma es independiente de las capas que tenga un Feature; no define una capa funcional obligatoria.

### Koin e inicializacion

Los `HostApp` de cada flavor registran `HostModule`, `AppVersionModule` y los modulos de las features. Android inicializa Koin desde `androidApp/src/main/.../AppDelegate.kt`; iOS lo hace mediante `initKoinIOS` en los source sets de mercado. Al agregar una feature con dependencias inyectadas, revisa sus modulos Koin y registralos en los `HostApp` correspondientes.

## 6. Preparar el entorno y compilar

1. Clona/ubica el workspace `MiClaro` con la estructura indicada en la seccion 1 (`features/core` incluido).
2. Abre la raiz de `MiClaro` con Android Studio o usa su Gradle Wrapper. Configura el JDK 21 y Android SDK.
3. En Windows CMD o PowerShell, desde la raiz de `MiClaro`, compila una variante:

```powershell
.\gradlew.bat :androidApp:assembleEcuadorDebug
.\gradlew.bat :androidApp:assembleCenamDebug
```

Para invocar las tareas de pruebas unitarias Android por variante:

```powershell
.\gradlew.bat :androidApp:testEcuadorDebugUnitTest
.\gradlew.bat :androidApp:testCenamDebugUnitTest
```

Actualmente `androidApp` no tiene fuentes de pruebas unitarias; Gradle reporta estas tareas como `NO-SOURCE` hasta que se agreguen tests.

Para iOS, abre `iosApp/iosApp.xcodeproj` en Xcode sobre macOS y compila el scheme `Ecuador` o `Cenam`. Si invocas la configuracion KMP desde Gradle para Cenam, especifica `-PiosFlavor=CENAM`; Ecuador es el valor por defecto.

Los APK de Android se generan bajo `androidApp/build/outputs/apk/`. Los directorios `build/` contienen resultados generados y no son el lugar para editar codigo fuente.

## 7. Incorporar o cambiar una feature

Al conectar una feature nueva, revisa todas las capas de configuracion relacionadas:

1. Crea o actualiza el build KMP de la feature bajo `features/` y confirma que resuelve Core mediante la estructura de workspace esperada.
2. Registra el build local en `settings.gradle.kts` con `includeBuild(...)`.
3. Agrega su alias y coordenada en `gradle/libs.versions.toml` y asignala al bundle de mercado adecuado.
4. Conecta el bundle en `androidApp/build.gradle.kts` para Android y en `shared/build.gradle.kts` para el source set iOS de ese mercado.
5. Si la feature participa en inyeccion o navegacion, actualiza el `HostApp` y la implementacion `NavigationFeaturesRouterImpl` del flavor/source set correspondiente. Si cambia el destino despues de Splash, selecciona la politica fija a Welcome o la politica por flavor descritas en la seccion 3.3.
6. Compila las variantes Android afectadas; para cambios iOS, valida tambien en Xcode sobre macOS.

## 8. Archivos de referencia

- [Configuracion de builds incluidos](settings.gradle.kts)
- [Catalogo de dependencias y flavors](gradle/libs.versions.toml)
- [Integracion Android](androidApp/build.gradle.kts)
- [Configuracion compartida KMP, Kommon Versions y Splash](shared/build.gradle.kts)
- [Plugin de convencion Kommon Versions](build-logic/convention/src/main/kotlin/plugins/kommonsVersions/KommonVersionExtension.kt)
- [Arranque de Android](androidApp/src/main/kotlin/com/kmp/host/base/AppDelegate.kt)
- [Punto de entrada Compose de iOS](shared/src/iosMain/kotlin/com/kmp/host/MainViewController.kt)

# Bone - Feature template para KMP

**Bone** es el template oficial para crear nuevos features dentro de los proyectos Kotlin Multiplatform (KMP) del SDK.

El proyecto contiene una estructura funcional y reutilizable para que un equipo pueda comenzar un feature sin configurar desde cero Gradle, Compose Multiplatform, Koin, Voyager, recursos compartidos ni la integración con el Core. El objetivo es que todos los features mantengan la misma organización, dependencias y contratos de integración con el proyecto **Host**.

> Bone no es una funcionalidad de negocio. Es una plantilla que debe copiarse o renombrarse antes de incorporarse al Host.

## Qué incluye Bone

Bone está configurado como un módulo KMP con los siguientes targets:

- Android
- iOS físico (`iosArm64`)
- iOS Simulator (`iosSimulatorArm64`)

La estructura principal se encuentra en `bone/src`:

```text
bone/
├── src/
│   ├── commonMain/
│   │   ├── composeResources/
│   │   └── kotlin/com/kmp/feature/bone/
│   │       └── featureUi/
│   │           ├── di/
│   │           └── presentation/
│   │               ├── navigation/
│   │               ├── ui/
│   │               │   ├── phone/
│   │               │   └── tablet/
│   │               └── viewmodel/
│   ├── androidMain/
│   └── iosMain/
├── build.gradle.kts
└── ...
```

El feature de ejemplo incluye:

| Componente | Responsabilidad |
| --- | --- |
| `BoneScreen` | Punto de entrada, conexión con el `ViewModel` y selección del layout adaptativo. |
| `BonePhoneContainer` | Presentación para anchos compactos y medios. |
| `BoneTabletContainer` | Presentación para anchos expandidos. |
| `BoneViewModel` | Punto de partida para el estado y la lógica de presentación; actualmente está vacío. |
| `BoneScreenInstance` | Pantalla de Voyager que expone la pantalla del feature. |
| `BoneModule` | Módulo de Koin y `ComponentScan` del feature. |
| `composeResources` | Recursos compartidos entre Android e iOS. |

## Implementación actual de Bone

El flujo base de presentación es:

```text
Host/Voyager
  -> BoneScreenInstance.Content()
  -> BoneScreen()  [viewModel default: koinViewModel()]
  -> SafeScreenContainer {
       AdaptiveScreenContent(
         phoneContainer = { BonePhoneContainer() },
         tabletContainer = { BoneTabletContainer() },
       )
     }
```

- **`BoneScreenInstance`** es el `data object` de Voyager. Su `Content()` llama a `BoneScreen()`; los comentarios de navegación están desactivados y no implementan un flujo activo.
- **`BoneScreen`** obtiene `BoneViewModel` con `koinViewModel()`, envuelve el contenido en `SafeScreenContainer` y delega la selección visual en `AdaptiveScreenContent`. Por ahora no lee el ViewModel.
- **`BonePhoneContainer`** está en `ui/phone/BoneDeviceContainer.kt`. Implementa una columna desplazable centrada que usa `Dimens`, `AppTheme`, `TextBigBold` y `Res.string.example_device`.
- **`BoneTabletContainer`** está en `ui/tablet/BoneTabletContainer.kt`. Es la presentación independiente para tablets y usa `Res.string.example_tablet`.
- **`BoneViewModel`** extiende `ViewModel` y tiene `@KoinViewModel`, pero todavía no declara estado, eventos ni lógica.
- **`BoneModule`** usa `@Module`, `@Configuration` y `@ComponentScan("com.kmp.feature.bone")` para descubrir las clases anotadas del paquete.
- **`boneUseCase.kt`** solo declara el paquete: no contiene casos de uso ni reglas de negocio.
- **`android.kt`** e **`ios.kt`** solo declaran el paquete Bone; no implementan APIs específicas de plataforma.

Bone es, por tanto, una base de UI y DI, no una funcionalidad de negocio lista para producción. El feature nuevo debe agregar explícitamente el estado, las acciones, los casos de uso, los recursos y cualquier comportamiento de plataforma que necesite.

### Layout adaptativo y previews

`AdaptiveScreenContent` compara el ancho disponible en `Dp` con las clases de
ancho del Core Design: `Compact` para menos de 600 dp, `Medium` desde 600 dp y
menos de 840 dp, y `Expanded` desde 840 dp. `Compact` y `Medium` muestran
`BonePhoneContainer`; `Expanded` muestra `BoneTabletContainer`. Ambos slots
son obligatorios.

La selección no detecta el tipo de dispositivo ni consulta la orientación: se
basa en el ancho que recibe el contenido. Un teléfono en landscape puede entrar
en la variante tablet si supera el umbral de 840 dp. `BoneScreen` conserva el
punto de entrada común para estado, eventos y navegación; las dos variantes
resuelven solamente sus diferencias de presentación.

`BoneScreen` incluye previews adaptativos para estas configuraciones:

| Preview | Especificación | Variante esperada |
| --- | --- | --- |
| Pixel 5 portrait | `width=1080px,height=2340px,dpi=440` | Phone |
| Pixel 5 landscape | `width=2340px,height=1080px,dpi=440` | Tablet si el ancho disponible llega a 840 dp |
| Tablet 7 pulgadas | `width=1920px,height=1200px,dpi=320` | Tablet |

Los previews del selector permiten revisar el comportamiento según el ancho;
los previews individuales de cada contenedor sirven para inspeccionar una
variante por separado.

### Configuración Gradle

Bone es un build independiente. Su `settings.gradle.kts` declara `Bone` como proyecto raíz, incluye el módulo `:bone` e incluye Core con `includeBuild("../core")`, ruta relativa desde la raíz de `features/bone`. El `build.gradle.kts` del módulo configura Kotlin Multiplatform, la librería Android, Compose Multiplatform y el plugin de Koin.

El target Android toma `compileSdk` y `minSdk` del catálogo y usa el namespace `com.kmp.feature.bone`. Los targets `iosArm64` e `iosSimulatorArm64` generan el framework dinámico `FeatureBone` (`isStatic = false`). Las dependencias base se declaran en `commonMain` mediante `feature-base-libs`; los bloques `androidMain` e `iosMain` están vacíos inicialmente.

Al crear un feature real, reemplaza el prefijo `Bone` por el nombre del feature en clases, funciones, archivos e imports. Por ejemplo, con el prefijo `Payment`:

| Elemento de Bone | Nuevo nombre | Archivo o ubicación |
| --- | --- | --- |
| `BoneScreen` | `PaymentScreen` | `ui/BoneScreen.kt` -> `ui/PaymentScreen.kt` |
| `BonePhoneContainer` | `PaymentPhoneContainer` | `ui/phone/BoneDeviceContainer.kt` -> `ui/phone/PaymentDeviceContainer.kt` |
| `BoneTabletContainer` | `PaymentTabletContainer` | `ui/tablet/BoneTabletContainer.kt` -> `ui/tablet/PaymentTabletContainer.kt` |
| `BoneScreenInstance` | `PaymentScreenInstance` | `presentation/navigation/BoneNavigation.kt` |
| `BoneViewModel` | `PaymentViewModel` | `presentation/viewmodel/BoneViewModel.kt` |
| `BoneModule` | `PaymentModule` | `di/BoneModule.kt` |
| `boneUseCase.kt` | `paymentUseCase.kt` | `featureLogic/usecase/` |

Conserva las carpetas `ui/phone` y `ui/tablet`, y actualiza en la pantalla los
imports y llamadas a ambos contenedores. La guía
[`doc/README_FEATURE_NEW.md`](doc/README_FEATURE_NEW.md) incluye el ejemplo de
`AdaptiveScreenContent` y las reglas de selección por ancho.

## Herramientas incluidas

### Kotlin Multiplatform

Permite compartir la implementación del feature entre Android e iOS usando `commonMain`, dejando espacio para código específico en `androidMain` e `iosMain`.

### Compose Multiplatform

La UI se construye en `commonMain`, por lo que los composables y los recursos pueden reutilizarse en Android e iOS.

### Koin

Bone incluye Koin Core, Koin Annotations y Koin Compose ViewModel. El feature puede declarar:

```kotlin
@Module
@ComponentScan("com.kmp.feature.bone")
@Configuration
class BoneModule
```

Las clases administradas por Koin pueden utilizar anotaciones como `@Single`, `@Factory` y `@KoinViewModel`. El plugin del compilador está configurado para generar y validar el código de Koin.

### Voyager

Voyager se utiliza para la navegación interna entre pantallas del feature. Los pasos para exponer la pantalla a través del router compartido están en [`doc/README_FEATURE_NEW.md`](doc/README_FEATURE_NEW.md).

### Lifecycle ViewModel y coroutines

El bundle base incluye `Lifecycle ViewModel` para KMP y `kotlinx-coroutines-core`, lo que permite manejar estado, `StateFlow`, `MutableStateFlow` y operaciones asíncronas desde `commonMain`.

### Recursos compartidos

Compose Resources está configurado con:

```kotlin
packageOfResClass = "com.kmp.feature.bone.generated.resources"
```

Los recursos se pueden utilizar desde código común:

```kotlin
import com.kmp.feature.bone.generated.resources.Res
import com.kmp.feature.bone.generated.resources.example_device
import com.kmp.feature.bone.generated.resources.example_tablet
import org.jetbrains.compose.resources.stringResource
```

Al renombrar el feature, actualiza también el paquete de recursos y sus imports.

## Módulos disponibles en Core

El bundle `feature-base-libs` agrega automáticamente las dependencias mínimas para un feature:

```toml
feature-base-libs = [
    "core-design",
    "core-navigation",
    "androidx-lifecycle-viewmodel",
    "kotlinx-coroutines-core",
    "compose-components-resources",
    "koin-compose-viewmodel",
    "koin-core",
    "koin-annotations",
]
```

El bundle base incluye solo `core-design` y `core-navigation` del Core. Los demás
módulos se agregan de forma explícita cuando el feature los necesita:

| Módulo | Uso |
| --- | --- |
| `:core:domain` | Modelos, contratos de repositorio y casos de uso independientes de frameworks. |
| `:core:data:database` | Persistencia local con Room, entidades, DAO e implementaciones de repositorios. |
| `:core:data:network` | Cliente Ktor, DTOs, mappers de transporte y acceso remoto. |
| `:core:data:datastore` | Implementación de preferencias y almacenamiento clave-valor. |
| `:core:presentation:design` | Tema, colores, dimensiones, componentes visuales y modelos de UI compartidos. |
| `:core:presentation:navigation` | Contratos compartidos de navegación entre features. |
| `:core:tools` | Utilidades y extensiones transversales. |

`data` y `presentation` son carpetas agrupadoras, no módulos Gradle. Para
agregar una dependencia, registra la coordenada del módulo hoja en
`gradle/libs.versions.toml` y añádela al bundle o a `commonMain.dependencies`,
según corresponda. Por ejemplo:

```kotlin
commonMain.dependencies {
    implementation(libs.bundles.feature.base.libs)
    implementation(libs.core.network)
}
```

La configuración del bundle permite que cada feature utilice únicamente las herramientas que necesita.

## Flujo recomendado

### Crear un feature nuevo

Usa esta opción cuando el feature tendrá sus propios paquetes, clases y contratos:

1. Usa `feature-bone-generator` para generar un feature nuevo dentro de `features`.
2. Renombra el proyecto Gradle y el módulo.
3. Renombra el namespace y los paquetes `com.kmp.feature.bone`.
4. Renombra las clases `Bone*`.
5. Actualiza `@ComponentScan` y `packageOfResClass`.
6. Integra el feature al Host solo cuando se solicite, conectando bundles, Koin y navegación por mercado/plataforma.

Guía completa: [`doc/README_FEATURE_NEW.md`](doc/README_FEATURE_NEW.md).

### Copiar un feature para otro flavor

No uses Bone cuando necesitas una variante de un feature existente para un flavor. En ese caso, copia el feature original, conserva sus paquetes y contratos públicos, y cambia únicamente las coordenadas Gradle y el bundle del flavor.

Guía completa: [`doc/README_FEATURE_COPY.md`](doc/README_FEATURE_COPY.md).

## Crear e integrar un feature

Este README describe la implementación interna de Bone. Los pasos para generar un nuevo feature e integrarlo al Host —incluyendo bundles, Koin y navegación por mercado/plataforma— están en [`doc/README_FEATURE_NEW.md`](doc/README_FEATURE_NEW.md).

## Verificación local

Compila Bone de forma independiente desde la raíz de este proyecto:

```bat
gradlew.bat build
```

Para compilar las variantes del Host después de integrarlo, consulta [`doc/README_FEATURE_NEW.md`](doc/README_FEATURE_NEW.md).

## Reglas importantes

- Mantén la lógica compartida en `commonMain` siempre que sea posible.
- Usa `androidMain` e `iosMain` únicamente para APIs específicas de plataforma.
- No agregues todos los módulos del Core por defecto; incorpora solo los necesarios.
- No registres dos features que expongan los mismos paquetes y símbolos públicos dentro del mismo bundle.
- Actualiza siempre `@ComponentScan`, el namespace y `packageOfResClass` al renombrar Bone.
- No edites directamente la carpeta `build/`; contiene artefactos generados.
- Sigue las instrucciones de Git del equipo antes de publicar el nuevo repositorio del feature.

## Documentación relacionada

- [`doc/README_FEATURE_NEW.md`](doc/README_FEATURE_NEW.md): crear e integrar un feature nuevo.
- [`doc/README_FEATURE_COPY.md`](doc/README_FEATURE_COPY.md): copiar un feature existente para otro flavor.

# Feature Skeleton (Bone) - KMP

Este proyecto sirve como un **esqueleto funcional** para la creación de nuevos módulos de "Feature" en una arquitectura de Kotlin Multiplatform (KMP). Está diseñado para ser implementado dentro de un proyecto **HOST** y utiliza una estructura predefinida para mantener la consistencia en el desarrollo.

Puedes crear el feature con el Skill `feature-bone-generator` o seguir los pasos manuales de esta guía. La integración con el Host se documenta aquí para ambas formas de creación; el `README.md` de Bone se enfoca en describir la implementación interna del template.

## 🚀 Pasos para Iniciar un Nuevo Feature

Para comenzar a trabajar en una nueva funcionalidad basada en este esqueleto, sigue estos pasos de personalización:

### 1. Preparar una copia nueva
Copia manualmente todo el build [`features/bone`](../) a `features/<slug>`. Antes de copiar, confirma que el destino exacto no exista; no sobrescribas ni combines carpetas. Conserva la estructura de build raíz/módulo y el `gradle-wrapper.jar` byte por byte. No copies `.git` ni artefactos `build/`, y nunca elimines el `.git` del proyecto fuente.

No uses Bone para crear una segunda variante de flavor de un feature existente; en ese caso sigue [`README_FEATURE_COPY.md`](README_FEATURE_COPY.md).

### 2. Definir nombres y actualizar Gradle
Confirma un nombre y úsalo de forma consistente. Por ejemplo, para `Payment Plan`:

| Uso | Ejemplo |
| --- | --- |
| Nombre visible | `Payment Plan` |
| Slug Gradle y carpeta del módulo | `payment-plan` |
| Segmento Kotlin del paquete | `paymentplan` |
| Prefijo PascalCase de clases | `PaymentPlan` |
| Prefijo lowerCamelCase | `paymentPlan` |

En el [`settings.gradle.kts`](../settings.gradle.kts) del feature, establece `rootProject.name` con el slug, renombra el directorio del módulo `bone` al slug y actualiza `include(":bone")`. Calcula también la ruta relativa desde la raíz nueva del feature hasta el build Core. Para `Host/features/<slug>` y `Host/features/core`, conserva `includeBuild("../core")` en el `settings.gradle.kts` del feature. No cambies el [`settings.gradle.kts` del Host](../../../settings.gradle.kts) en esta etapa.

En el [`build.gradle.kts` del módulo](../bone/build.gradle.kts), actualiza el namespace Android a `com.kmp.feature.<segmento>`.

### 3. Configurar `local.properties` (obligatorio)

> [!WARNING]
> **Este paso es obligatorio para compilar el feature.** Copia el [`local.properties` del Host](../../../local.properties) a la raíz del nuevo feature antes de ejecutar Gradle.

### 4. Renombrar paquetes y recursos
Actualiza `com.kmp.feature.bone` a `com.kmp.feature.<segmento>` en las rutas, declaraciones de paquete e imports de [`commonMain`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureLogic/usecase/boneUseCase.kt), [`androidMain`](../bone/src/androidMain/kotlin/com/kmp/feature/bone/android.kt) e [`iosMain`](../bone/src/iosMain/kotlin/com/kmp/feature/bone/ios.kt). Actualiza también `@ComponentScan` en [`BoneModule.kt`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureUi/di/BoneModule.kt) y configura Compose Resources en [`build.gradle.kts`](../bone/build.gradle.kts) como:
```kotlin
packageOfResClass = "com.kmp.feature.<segmento>.generated.resources"
```

Renombra `FeatureBone` a `Feature<Prefijo>` en [`build.gradle.kts`](../bone/build.gradle.kts) y actualiza los imports/referencias de `Res` y recursos generados. Revisa [`strings.xml`](../bone/src/commonMain/composeResources/values/strings.xml) y la documentación para quitar los nombres de ejemplo que ya no correspondan.

### 5. Renombrar clases y archivos de UI, navegación y DI
Usa el mismo prefijo acordado en las clases y nombres de archivo:

- [`BoneScreen`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureUi/presentation/ui/BoneScreen.kt) -> `[Prefijo]Screen`
- [`BonePhoneContainer`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureUi/presentation/ui/phone/BoneDeviceContainer.kt) -> `[Prefijo]PhoneContainer`
- [`BoneTabletContainer`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureUi/presentation/ui/tablet/BoneTabletContainer.kt) -> `[Prefijo]TabletContainer`
- [`BoneScreenInstance`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureUi/presentation/navigation/BoneNavigation.kt) -> `[Prefijo]ScreenInstance`
- [`BoneViewModel`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureUi/presentation/viewmodel/BoneViewModel.kt) -> `[Prefijo]ViewModel`
- [`BoneModule`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureUi/di/BoneModule.kt) -> `[Prefijo]Module`
- [`boneUseCase.kt`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureLogic/usecase/boneUseCase.kt) -> `[lowerCamelCase]UseCase.kt`

Después de renombrar, actualiza los imports y las llamadas de ambos contenedores
en `[Prefijo]Screen`. Conserva los dos slots obligatorios de
`AdaptiveScreenContent`:

```kotlin
AdaptiveScreenContent(
    phoneContainer = { [Prefijo]PhoneContainer() },
    tabletContainer = { [Prefijo]TabletContainer() },
)
```

La selección depende del ancho disponible, no del tipo de dispositivo ni de la
orientación: `Compact` (<600 dp) y `Medium` (600–<840 dp) muestran Phone;
`Expanded` (>=840 dp) muestra Tablet. Por eso, un teléfono en landscape puede
usar la presentación Tablet si alcanza ese ancho. Mantén en `[Prefijo]Screen`
el punto común de estado, eventos y navegación, y conserva previews adaptativos
de teléfono portrait, teléfono landscape y tablet. Las carpetas
`ui/phone` y `ui/tablet` no se renombran.

> [!IMPORTANT]
> **Ajuste de `@ComponentScan`**: En la clase `[Prefijo]Module` (actualmente [`BoneModule.kt`](../bone/src/commonMain/kotlin/com/kmp/feature/bone/featureUi/di/BoneModule.kt)), asegúrate de actualizar el paquete en la anotación para que coincida con el nuevo nombre del feature:
> ```kotlin
> @Module
> @ComponentScan("com.kmp.feature.[nombre_de_tu_feature]")
> @Configuration
> class [Prefijo]Module {}
> ```

### 6. Repositorio y publicación (opcional)
La creación del feature no requiere borrar ni reinicializar la configuración de control de versiones existente. Mantén el repositorio y su historial. Crear un repositorio remoto o publicar el feature son tareas separadas: hazlas solo si se solicitan explícitamente y siguiendo las políticas del equipo. No uses `git push --force` para publicar una integración normal.

### 7. Integración con el HOST

La generación del feature y su integración son pasos separados. Integra el feature solo cuando se solicite y para los mercados y plataformas acordados. Antes de editar el Host, inspecciona sus dimensiones de flavors/proveedores, bundles, `HostApp` y routers activos; no asumas que una implementación cubre todos los mercados o plataformas.

#### A. Incluir el composite build y las dependencias

En el [`settings.gradle.kts` raíz del Host](../../../settings.gradle.kts), agrega el feature. El ejemplo asume que está bajo `features/[slug]`:
```kotlin
includeBuild("features/[slug]")
```
Este include es relativo a la raíz del Host y no reemplaza `includeBuild("../core")` del `settings.gradle.kts` interno del feature.

En [`Host/gradle/libs.versions.toml`](../../../gradle/libs.versions.toml), registra su coordenada Gradle y añade el alias a cada bundle de mercado/plataforma solicitado, preservando las dependencias existentes:
```toml
[libraries]
feature-example = { module = "com.kmp.feature:example", version = "unspecified" }

[bundles]
features-ecuador = ["feature-example"]
features-cenam = ["feature-example"]
```

El `settings.gradle.kts` propio del feature conserva su includeBuild a Core relativo a la raíz del feature; no reemplaces esa ruta por la del Host.

#### B. Registrar Koin por mercado y plataforma

Registra `[FeatureName]Module::class` en cada `HostApp` aplicable. En este Host hay aplicaciones Android por mercado y `HostApp` iOS en source sets separados (`iosEcuadorMain` e `iosCenamMain`), seleccionados mediante la propiedad `iosFlavor`. Inspecciona la configuración actual y modifica cada variante incluida en el alcance:

- Android Ecuador: [`androidApp/src/ecuador/kotlin/com/kmp/host/di/HostApp.kt`](../../../androidApp/src/ecuador/kotlin/com/kmp/host/di/HostApp.kt)
- Android CENAM: [`androidApp/src/cenam/kotlin/com/kmp/host/di/HostApp.kt`](../../../androidApp/src/cenam/kotlin/com/kmp/host/di/HostApp.kt)
- iOS Ecuador: [`shared/src/iosEcuadorMain/kotlin/com/kmp/host/di/HostApp.kt`](../../../shared/src/iosEcuadorMain/kotlin/com/kmp/host/di/HostApp.kt)
- iOS CENAM: [`shared/src/iosCenamMain/kotlin/com/kmp/host/di/HostApp.kt`](../../../shared/src/iosCenamMain/kotlin/com/kmp/host/di/HostApp.kt)

```kotlin
@KoinApplication(
    modules = [
        HostModule::class,
        [FeatureName]Module::class,
    ]
)
class HostApp
```

#### C. Añadir la ruta al contrato común de Core

En [`features/core/core/presentation/navigation/src/commonMain/kotlin/com/kmp/core/navigation/router/NavigationFeaturesRouter.kt`](../../../features/core/core/presentation/navigation/src/commonMain/kotlin/com/kmp/core/navigation/router/NavigationFeaturesRouter.kt), agrega un método que devuelva `Screen`, siguiendo el nombre y estilo de los métodos existentes:
```kotlin
interface NavigationFeaturesRouter {
    fun get[FeatureName]Screen(): Screen
}
```

#### D. Implementar la ruta en cada router

Implementa el método en todos los routers de mercado/plataforma incluidos en el alcance. En este Host, revisa:

- [`androidApp/src/ecuador/kotlin/com/kmp/host/navigation/NavigationFeaturesRouterImpl.kt`](../../../androidApp/src/ecuador/kotlin/com/kmp/host/navigation/NavigationFeaturesRouterImpl.kt)
- [`androidApp/src/cenam/kotlin/com/kmp/host/navigation/NavigationFeaturesRouterImpl.kt`](../../../androidApp/src/cenam/kotlin/com/kmp/host/navigation/NavigationFeaturesRouterImpl.kt)
- [`shared/src/iosEcuadorMain/kotlin/com/kmp/host/navigation/NavigationFeaturesRouterImpl.kt`](../../../shared/src/iosEcuadorMain/kotlin/com/kmp/host/navigation/NavigationFeaturesRouterImpl.kt)
- [`shared/src/iosCenamMain/kotlin/com/kmp/host/navigation/NavigationFeaturesRouterImpl.kt`](../../../shared/src/iosCenamMain/kotlin/com/kmp/host/navigation/NavigationFeaturesRouterImpl.kt)

Los routers iOS activos se eligen mediante la propiedad `iosFlavor` en [`shared/build.gradle.kts`](../../../shared/build.gradle.kts). En cada router del alcance, importa `[FeatureName]ScreenInstance` y devuelve esa instancia:
```kotlin
class NavigationFeaturesRouterImpl : NavigationFeaturesRouter {
    override fun get[FeatureName]Screen(): Screen = [FeatureName]ScreenInstance
}
```

La ruta solo hace que la pantalla esté disponible para el Host. No añade un botón, deep link, llamada desde otra pantalla ni cambia la pantalla inicial; implementa esos flujos únicamente si también se solicitan. La navegación entre features pasa por el contrato del Core, no por dependencias directas entre features.

#### E. Compilar las variantes solicitadas

Después de integrar, compila/ensambla cada mercado/proveedor solicitado desde la raíz del Host usando [`gradlew.bat`](../../../gradlew.bat). En PowerShell, para GMS:
```powershell
.\gradlew.bat :androidApp:assembleEcuadorGmsDebug --console=plain
.\gradlew.bat :androidApp:assembleCenamGmsDebug --console=plain
```

El script de providers admite un mercado/proveedor por invocación de Gradle: ejecuta cada combinación por separado. Para iOS, compila o enlaza con las herramientas Apple requeridas y reporta exactamente qué tareas finalizaron; no afirmes que se generó un framework si no se ejecutó su tarea de enlace.

---

## 🛠️ Dependencias del Core

El esqueleto viene configurado por defecto con las implementaciones esenciales del Core, pero puedes agregar más según las necesidades de tu feature.

### Implementaciones actuales:
- **`core-navigation`**: Manejo de rutas y navegación entre pantallas y features.
- **`core-design`**: Componentes de UI, temas, colores y recursos compartidos (Atomic Design).

### Otras implementaciones disponibles:
Si tu feature requiere persistencia, lógica de negocio avanzada o red, puedes agregar al `build.gradle.kts`:

- **`core-database`**: Para almacenamiento local con Room.
- **`core-network`**: Para llamadas a API con Ktor.
- **`core-datastore`**: Para preferencias de usuario.
- **`core-tools`**: Utilidades comunes y extensiones.

---

## 📝 Notas Adicionales
- **Voyager**: Se utiliza para la navegación interna del feature.
- **Koin**: Se utiliza para la inyección de dependencias.
- **Compose Multiplatform**: Todos los recursos de UI en `commonMain` son compartidos entre Android e iOS.

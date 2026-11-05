# Arkive - Core Design

Documentación para la configuración y uso de **Arkive** en el módulo `core:Design`.

Este módulo contiene los componentes visuales compartidos del proyecto y utiliza **Compose Multiplatform**, **Arkive** y **Roborazzi** para generar previews, snapshots y un catálogo visual de los componentes.

---

# 🚀 Arkive

Arkive permite generar un catálogo visual de los componentes Compose y relacionarlos opcionalmente con diseños de Figma.

Además, utiliza un motor de screenshots para generar y verificar snapshots de los componentes.

En este módulo utilizamos:

* Compose Multiplatform
* Arkive
* Roborazzi
* KSP
* Figma

---

# 🔧 Configuración de Gradle

## Plugins

En `Core/core/design/build.gradle.kts` agregamos los siguientes plugins:

```kotlin
plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.arkive)
}
```

---

# ⚙️ Configuración de Arkive

La configuración utilizada es:

```kotlin
import com.infinum.arkive.plugin.extensions.SnapshotRetention

arkive {
    enableVariants.set(false)
    enablePreviewParameters.set(false)

    designFileKey.set("TU_FIGMA_FILE_KEY")

    snapshotRetention.set(SnapshotRetention.BASE)

    engine(Roborazzi)
}
```

## Opciones utilizadas

### `enableVariants`

```kotlin
enableVariants.set(false)
```

Se encuentra desactivado porque el módulo `core:Design` no necesita generar diferentes variantes.

---

### `enablePreviewParameters`

```kotlin
enablePreviewParameters.set(false)
```

Se encuentra desactivado porque actualmente no se están utilizando `PreviewParameterProvider`.

---

### `designFileKey`

```kotlin
designFileKey.set("TU_FIGMA_FILE_KEY")
```

Identifica el archivo de Figma que contiene los diseños del Design System.

El `fileKey` se obtiene de la URL de Figma.

Ejemplo:

```text
https://www.figma.com/design/ABC123XYZ/Mi-Design-System?node-id=123-456
```

El `designFileKey` sería:

```text
ABC123XYZ
```

---

# 🎨 Integración con Figma

Arkive permite asociar un componente Compose con un nodo específico de Figma mediante `designNodeId`.

Ejemplo:

```kotlin
@ArkiveComposable(
    name = "Primary Button",
    group = "Buttons",
    tags = ["cta"],
    designNodeId = "123-456"
)
@Preview
@Composable
internal fun PrimaryButtonPreview() {
    Box(modifier = Modifier.padding(Dimens.padding16)) {
        ButtonPrimary(
            backgroundColor = Color.Black,
            textColors = Color.White,
            text = stringResource(Res.string.primary_button),
        )
    }
}
```

## ¿Cómo obtener `designNodeId`?

En Figma:

1. Seleccionar el componente o frame.
2. Seleccionar **Copy link to selection**.
3. Copiar la URL.

Ejemplo:

```text
https://www.figma.com/design/ABC123XYZ/Mi-Design-System?node-id=123-456
```

Entonces:

```text
designFileKey = ABC123XYZ
designNodeId  = 123-456
```

El `designNodeId` permite que Arkive conozca la relación entre el componente de Compose y su correspondiente diseño en Figma.

---

# 🖼️ Snapshots

Los snapshots se generan para poder comparar visualmente los componentes.

La configuración utilizada es:

```kotlin
snapshotRetention.set(SnapshotRetention.BASE)
```

`BASE` conserva un golden de referencia por componente.

Los snapshots se encuentran dentro del módulo:

```text
Core/core/design/src/androidHostTest/snapshots/
```

---

# 🧪 Host Tests

Como Compose Multiplatform utiliza Android para realizar el rendering de los snapshots, el módulo necesita configurar el `androidHostTest`.

En el bloque `android`:

```kotlin
android {
    namespace = "com.kmp.core.design"

    compileSdk = libs.versions.android.compileSdk.get().toInt()
    minSdk = libs.versions.android.minSdk.get().toInt()

    androidResources {
        enable = true
    }

    withHostTest {
        isIncludeAndroidResources = true
    }
}
```

El:

```kotlin
isIncludeAndroidResources = true
```

permite que los recursos Android estén disponibles durante los host tests.

Esto es especialmente importante para los componentes que utilizan recursos de Compose Multiplatform.

---

# 🧩 KSP

Arkive utiliza KSP para procesar los componentes anotados con `@ArkiveComposable`.

El plugin debe estar aplicado:

```kotlin
alias(libs.plugins.ksp)
```

Arkive se encarga de conectar su processor con los source sets correspondientes.

---

# 📝 Previews

Los componentes que queremos mostrar en Arkive deben tener:

```kotlin
@ArkiveComposable
@Preview
@Composable
```

Ejemplo:

```kotlin
@ArkiveComposable(
    name = "Primary Button",
    group = "Buttons"
)
@Preview
@Composable
internal fun PrimaryButtonPreview() {
    Box(modifier = Modifier.padding(Dimens.padding16)) {
        ButtonPrimary(
            backgroundColor = Color.Black,
            textColors = Color.White,
            text = stringResource(Res.string.primary_button),
        )
    }
}
```

## Recomendaciones

Utilizar nombres y grupos descriptivos:

```kotlin
@ArkiveComposable(
    name = "Primary Button",
    group = "Buttons"
)
```

Otro ejemplo:

```kotlin
@ArkiveComposable(
    name = "Error Input",
    group = "Inputs",
    tags = ["error", "validation"]
)
```

Esto permite que el catálogo sea más fácil de navegar.

---

# ▶️ Generar Showcase

Desde la raíz del proyecto ejecutar:

```powershell
.\gradlew :core:presentation:design:generateShowcaseAndroidMain
```

Este comando:

* Procesa los `@ArkiveComposable`.
* Renderiza los previews.
* Genera los snapshots.
* Genera la información necesaria para Arkive.
* Conserva los goldens según `SnapshotRetention`.

Los snapshots generados quedan dentro de:

```text
Core/core/design/src/androidHostTest/snapshots/
```

---

# ✅ Verificar Showcase

Para verificar que los componentes actuales coinciden con los snapshots guardados:

```powershell
.\gradlew :core:presentation:design:verifyShowcaseAndroidMain
```

Si no existen diferencias, el proceso termina correctamente.

Si modificamos intencionalmente el diseño de un componente, la verificación puede detectar una diferencia entre:

```text
UI actual
   VS
Golden snapshot
```

---

# 🔄 Roborazzi

También están disponibles los tasks propios de Roborazzi.

## Generar snapshots

```powershell
.\gradlew :core:presentation:design:recordRoborazziAndroidHostTest
```

## Verificar snapshots

```powershell
.\gradlew :core:presentation:design:verifyRoborazziAndroidHostTest
```

## Verificar y registrar

```powershell
.\gradlew :core:presentation:design:verifyAndRecordRoborazziAndroidHostTest
```

## Comparar snapshots

```powershell
.\gradlew :core:presentation:design:compareRoborazziAndroidHostTest
```

### Arkive vs Roborazzi

Para el flujo normal de Arkive se recomienda utilizar:

```powershell
.\gradlew :core:presentation:design:generateShowcaseAndroidMain
```

y:

```powershell
.\gradlew :core:presentation:design:verifyShowcaseAndroidMain
```

Los tasks de Roborazzi son los tasks del engine de screenshots.

---

# 🤖 Task automatizado de Arkive

Para evitar ejecutar manualmente cada uno de los comandos necesarios para generar el catálogo, se creó el task:

```text
deployComponentsKmpArkive
```

Este task automatiza el flujo completo de generación de componentes de Arkive:

1. Graba los snapshots mediante Roborazzi.
2. Genera los metadatos del Showcase.
3. Genera el catálogo web de Arkive.

El task está configurado de la siguiente manera:

```kotlin
tasks.register("deployComponentsKmpArkive") {
    group = "arkive"
    description = "Ejecuta el flujo completo: Graba snapshots, genera metadatos y ensambla la web de Arkive."

    // 1. Definimos las dependencias (qué tareas debe correr)
    dependsOn(":core:presentation:design:recordRoborazziAndroidHostTest")
    dependsOn(":core:presentation:design:generateShowcaseAndroidMain")
    dependsOn("generateWebShowcase")

    // 2. Forzamos el orden secuencial
    // Primero graba, luego genera datos y al final la web
    tasks.findByName("generateShowcaseAndroidMain")
        ?.mustRunAfter(":core:presentation:design:recordRoborazziAndroidHostTest")

    tasks.findByName("generateWebShowcase")
        ?.mustRunAfter(":core:presentation:design:generateShowcaseAndroidMain")
}
```

## Ejecutar el flujo completo

En lugar de ejecutar cada comando individualmente:

```powershell
.\gradlew :core:presentation:design:recordRoborazziAndroidHostTest

.\gradlew :core:presentation:design:generateShowcaseAndroidMain

.\gradlew generateWebShowcase
```

ahora se puede ejecutar solamente:

```powershell
.\gradlew deployComponentsKmpArkive
```

El task se encarga de ejecutar las tareas necesarias en el orden correspondiente:

```text
recordRoborazziAndroidHostTest
             ↓
generateShowcaseAndroidMain
             ↓
generateWebShowcase
```

De esta forma, el proceso de generación del catálogo de componentes queda centralizado en un único comando.

---

# 🌐 Generar el catálogo web

Una vez generado el Showcase del módulo:

```powershell
.\gradlew :core:presentation:design:generateShowcaseAndroidMain
```

se puede generar el catálogo web de Arkive desde la raíz del proyecto:

```powershell
.\gradlew generateWebShowcase
```

Sin embargo, para el flujo habitual se recomienda utilizar el task automatizado:

```powershell
.\gradlew deployComponentsKmpArkive
```

Aunque `generateWebShowcase` es un task global, el catálogo únicamente utiliza los módulos que tienen integración con Arkive.

En este proyecto, el módulo principal que contiene los componentes visuales es:

```text
:core:presentation:design
```

---

# 🔎 Buscar el catálogo generado

Para encontrar el archivo:

```text
arkive-showcase.json
```

se puede utilizar PowerShell desde la raíz:

```powershell
Get-ChildItem -Path . -Filter "arkive-showcase.json" -Recurse
```

También se debe localizar:

```text
index.html
```

Ambos archivos deben encontrarse dentro del directorio generado del Showcase.

---

# 🖥️ Servidor local

El catálogo de Arkive no debe abrirse directamente mediante:

```text
file://
```

porque el navegador bloqueará el `fetch` de:

```text
arkive-showcase.json
```

y aparecerá:

```text
Could not load arkive-showcase.json (Failed to fetch).
```

Para evitarlo se puede utilizar el servidor HTTP incluido en Python.

---

# 🐍 Instalar Python en Windows

Descargar Python desde:

https://www.python.org/downloads/windows/

Durante la instalación activar:

```text
Add python.exe to PATH
```

Después abrir una nueva terminal y comprobar:

```powershell
python --version
```

Debe mostrar algo parecido a:

```text
Python 3.x.x
```

También puede utilizarse:

```powershell
py --version
```

---

# 🚀 Levantar Arkive localmente

Ubicarse en la carpeta donde se encuentran:

```text
index.html
arkive-showcase.json
```

Por ejemplo:

```powershell
cd build\generated\arkive\showcase
```

Después ejecutar:

```powershell
python -m http.server 8000
```

Si `python` no está disponible:

```powershell
py -m http.server 8000
```

Se mostrará algo similar a:

```text
Serving HTTP on 0.0.0.0 port 8000
```

Abrir en el navegador:

```text
http://localhost:8000
```

De esta manera Arkive puede cargar correctamente:

```text
index.html
    ↓
arkive-showcase.json
    ↓
Snapshots
    ↓
Componentes
```

Para detener el servidor:

```text
Ctrl + C
```

---

# 🌍 Servidor en la Nube (Producción)

El catálogo de componentes también se encuentra publicado y accesible en la siguiente URL:

[https://arkita-hitss.onrender.com](https://arkita-hitss.onrender.com)

---

# 🔁 Flujo de trabajo

## Flujo automatizado

Para generar todo el catálogo se recomienda utilizar:

```powershell
.\gradlew deployComponentsKmpArkive
```

El flujo es:

```text
┌───────────────────────┐
│ Modificar componente  │
└───────────┬───────────┘
            ↓
┌─────────────────────────────┐
│ @Preview                    │
│ @ArkiveComposable           │
└───────────┬─────────────────┘
            ↓
┌─────────────────────────────────────────┐
│ deployComponentsKmpArkive               │
└───────────┬─────────────────────────────┘
            ↓
┌─────────────────────────────────────────┐
│ recordRoborazziAndroidHostTest          │
│ Graba snapshots                         │
└───────────┬─────────────────────────────┘
            ↓
┌─────────────────────────────────────────┐
│ generateShowcaseAndroidMain             │
│ Genera metadatos y Showcase             │
└───────────┬─────────────────────────────┘
            ↓
┌─────────────────────────────────────────┐
│ generateWebShowcase                     │
│ Genera catálogo web                     │
└───────────┬─────────────────────────────┘
            ↓
┌────────────────────────┐
│ Servidor Python        │
│ localhost:8000         │
└───────────┬────────────┘
            ↓
┌────────────────────────┐
│ Arkive Showcase        │
└────────────────────────┘
```

---

# 📋 Comandos principales

| Objetivo                    | Comando                                                          |
| --------------------------- | ---------------------------------------------------------------- |
| ⭐ Flujo completo Arkive     | `.\gradlew deployComponentsKmpArkive`                            |
| Generar Showcase            | `.\gradlew :core:presentation:design:generateShowcaseAndroidMain`             |
| Verificar Showcase          | `.\gradlew :core:presentation:design:verifyShowcaseAndroidMain`               |
| Generar snapshots Roborazzi | `.\gradlew :core:presentation:design:recordRoborazziAndroidHostTest`          |
| Verificar Roborazzi         | `.\gradlew :core:presentation:design:verifyRoborazziAndroidHostTest`          |
| Verificar y registrar       | `.\gradlew :core:presentation:design:verifyAndRecordRoborazziAndroidHostTest` |
| Comparar snapshots          | `.\gradlew :core:presentation:design:compareRoborazziAndroidHostTest`         |
| Generar catálogo web        | `.\gradlew generateWebShowcase`                                  |
| Iniciar servidor local      | `python -m http.server 8000`                                     |
| Abrir catálogo local        | `http://localhost:8000`                                          |
| 🌍 Abrir catálogo web       | `https://arkita-hitss.onrender.com`                              |

### Comando recomendado

Para el uso cotidiano:

```powershell
.\gradlew deployComponentsKmpArkive
```

Este comando evita tener que ejecutar manualmente cada uno de los pasos necesarios para actualizar el catálogo de componentes.

---

# 🧹 Limpieza

Si es necesario limpiar los snapshots generados por Roborazzi:

```powershell
.\gradlew :core:presentation:design:clearRoborazziAndroidHostTest
```

---

# 📌 Consideraciones

### No abrir `index.html` directamente

No utilizar:

```text
file:///...
```

Utilizar:

```text
http://localhost:8000
```

---

### No instalar Roborazzi manualmente para Arkive

Arkive administra el engine utilizado para los snapshots.

La configuración utilizada es:

```kotlin
arkive {
    engine(Roborazzi)
}
```

---

### Los snapshots deben versionarse

Los golden snapshots generados por Arkive deben mantenerse en el repositorio para que posteriormente puedan utilizarse durante las verificaciones.

Con:

```kotlin
snapshotRetention.set(SnapshotRetention.BASE)
```

se mantiene una cantidad reducida de goldens de referencia.

---

# 🎯 Resultado esperado

Al finalizar la configuración, `core:Design` funciona como el **Design System visual compartido** del proyecto.

Los componentes pueden:

* Tener previews de Compose.
* Generar snapshots.
* Verificar cambios visuales.
* Organizarse por grupos y tags.
* Relacionarse con diseños de Figma.
* Visualizarse mediante un catálogo web de Arkive.
* Utilizarse desde las aplicaciones que consumen `core:Design`.

Además, el proceso de generación del catálogo se encuentra automatizado mediante:

```powershell
.\gradlew deployComponentsKmpArkive
```

Ejemplo:

```text
Figma
  ↕
designFileKey / designNodeId
  ↕
Arkive
  ↕
@ArkiveComposable
  ↕
Compose Preview
  ↕
Roborazzi
  ↕
Golden Snapshot
  ↕
Arkive Web Showcase
  ↕
deployComponentsKmpArkive
  ↕
Catálogo Web
```

---

# 📚 Referencia

Arkive:

https://github.com/infinum/arkive

Roborazzi:

https://github.com/takahirom/roborazzi

Figma:

https://www.figma.com/

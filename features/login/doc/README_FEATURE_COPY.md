# Copiar un feature existente para otro flavor

Esta guía muestra cómo copiar el feature `Login` y convertirlo en `Login-Cenam`, conservando los mismos paquetes y contratos para que el cambio sea transparente para el Host.

## 1. Copiar el feature

Duplica:

```text
Features/Login
```

como:

```text
Features/Login-Cenam
```

La carpeta interna puede continuar llamándose `login`:

```text
Features/Login-Cenam/login
```

## 2. Cambiar las coordenadas Gradle

En `Features/Login-Cenam/settings.gradle.kts`, configura:

```kotlin
rootProject.name = "Login-Cenam"

// Dependencias del Core
includeBuild("../../Core")

// Nombre Gradle diferente, conservando la carpeta física login
include(":login-cenam")
project(":login-cenam").projectDir = file("login")
```

Esto evita que ambos proyectos publiquen la misma coordenada:

```text
Login original: com.kmp.feature:login
Login CENAM:    com.kmp.feature:login-cenam
```

## 3. Mantener el namespace original

En `Features/Login-Cenam/login/build.gradle.kts`, conserva:

```kotlin
group = "com.kmp.feature"
```

Y utiliza el mismo namespace del Login original:

```kotlin
kotlin {
    android {
        namespace = "com.kmp.feature.login"
    }
}
```

## 4. Mantener los paquetes Kotlin

No cambies los paquetes del código copiado:

```kotlin
package com.kmp.feature.login.featureUi.presentation.ui
```

```kotlin
package com.kmp.feature.login.featureUi.presentation.navigation
```

```kotlin
package com.kmp.feature.login.featureUi.di
```

También deben conservarse los mismos símbolos públicos:

```text
LoginScreenInstance
LoginModule
```

Esto permite que el Host utilice los mismos imports para ambos Login:

```kotlin
import com.kmp.feature.login.featureUi.presentation.navigation.LoginScreenInstance
import com.kmp.feature.login.featureUi.di.LoginModule
```

## 5. Mantener el paquete de Compose Resources

Al cambiar el nombre Gradle a `login-cenam`, Compose intentará generar los recursos en:

```text
com.kmp.feature.login_cenam.generated.resources
```

Para conservar los imports originales, agrega en `Features/Login-Cenam/login/build.gradle.kts`, fuera del bloque `kotlin`:

```kotlin
compose.resources {
    packageOfResClass = "com.kmp.feature.login.generated.resources"
}
```

Los imports existentes podrán mantenerse:

```kotlin
import com.kmp.feature.login.generated.resources.Res
import com.kmp.feature.login.generated.resources.login
```

## 6. Incluir Login CENAM en el Host

En `Host/settings.gradle.kts`, agrega:

```kotlin
includeBuild("../Features/Login-Cenam")
```

El Login original debe mantenerse:

```kotlin
includeBuild("../Features/Login")
includeBuild("../Features/Login-Cenam")
```

## 7. Registrar la dependencia

En `Host/gradle/libs.versions.toml`, agrega:

```toml
feature-login-cenam = {
    module = "com.kmp.feature:login-cenam",
    version = "unspecified"
}
```

Mantén el Login original:

```toml
feature-login = {
    module = "com.kmp.feature:login",
    version = "unspecified"
}
```

## 8. Asignar Login CENAM a su bundle

Cambia únicamente el bundle de CENAM:

```toml
features-cenam = [
    "feature-splash",
    "feature-welcome",
    "feature-login-cenam",
]
```

Ecuador y Chile continúan usando el Login original:

```toml
features-ecuador = [
    "feature-splash",
    "feature-welcome",
    "feature-login",
]

features-chile = [
    "feature-splash",
    "feature-welcome",
    "feature-login",
]
```

> [!IMPORTANT]
> Nunca agregues `feature-login` y `feature-login-cenam` al mismo bundle porque ambos exponen las mismas clases y paquetes.

## 9. Personalizar Login CENAM

Después de completar la configuración anterior, modifica únicamente la implementación interna de:

```text
Features/Login-Cenam
```

Puedes cambiar su UI, ViewModel, casos de uso y recursos, pero debes conservar los contratos utilizados por el Host:

```text
com.kmp.feature.login.featureUi.presentation.navigation.LoginScreenInstance
com.kmp.feature.login.featureUi.di.LoginModule
```

## 10. Compilar CENAM

Desde el Host:

```bat
gradlew.bat :androidApp:assembleCenamDebug
```

Ecuador y Chile seguirán compilando con el Login original:

```bat
gradlew.bat :androidApp:assembleEcuadorDebug
gradlew.bat :androidApp:assembleChileDebug
```
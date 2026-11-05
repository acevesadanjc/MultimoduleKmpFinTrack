# Core Presentation Design

`:core:presentation:design` reúne estilos y componentes visuales compartidos de Arkive para
Compose Multiplatform. Su código común vive en
`src/commonMain/kotlin/com/kmp/core/design`; el módulo configura Android e iOS
(`iosArm64` e `iosSimulatorArm64`) en `build.gradle.kts`.

## Qué contiene

- **Tema:** `theme/Theme.kt` define `AppTheme(...)`, que provee colores y
  tipografía para el modo claro u oscuro y configura `MaterialTheme`. Dentro de
  un composable, consulta `AppTheme.colors`, `AppTheme.typography` y
  `AppTheme.spanStyle`. El modo oscuro predeterminado se obtiene de
  `isSystemInDarkTheme()`.
- **Colores:** `theme/colors/ColorsLight.kt` y `ColorsDark.kt` declaran las
  paletas; `AppColors` agrupa los roles Material (`primary`, `onPrimary`,
  `background`, `surface`, `error`, entre otros) y colores semánticos
  adicionales: `status`, `text` y `backgrounds`. Por ejemplo, están disponibles
  `AppTheme.colors.status.success`, `AppTheme.colors.text.link` y
  `AppTheme.colors.backgrounds.white`. `AppColors.asMaterialColorScheme(...)`
  adapta los roles Material a los esquemas claro y oscuro.
- **Tipografía:** `theme/Type.kt` carga Open Sans desde
  `Res.font.open_sans`. `MaterialThemAppTypography` configura roles de
  `MaterialTheme.typography`; `AppTheme.typography` expone estilos propios
  `bodyLarge`, `bodyMedium`, `bodyNormal` y `bodySmall`, y
  `AppTheme.spanStyle.bodyLarge` ofrece un estilo de texto enriquecido.
- **Dimensiones:** `dimen/Dimens.kt` centraliza valores `Dp` para alturas,
  anchos, padding, esquinas, bordes y elevaciones, además de tamaños de texto
  (`TextUnit`). Ejemplos: `Dimens.padding16`, `Dimens.corner48`,
  `Dimens.height40` y `Dimens.textSizeNormal`.
- **Componentes Compose:** `component/` contiene botones, campos de texto,
  tarjetas, diálogos, contenedores, imágenes, loader, barra de navegación,
  pull-to-refresh, texto y toolbar. Entre las funciones definidas están
  `ButtonPrimary`, `ButtonOutlined`, `TextFieldSimple`, `TextFieldPassword`,
  `SimpleCard`, `InfoDialog`, `ImageAsync`, `Loader` y `Toolbar`.
- **Layout adaptativo:** `adaptive/AdaptiveScreenContent.kt` selecciona entre
  dos contenedores Compose compartidos según el ancho disponible.
- **Recursos compartidos:** `src/commonMain/composeResources` incluye
  `drawable` (`ic_example`, `ic_eye`, `ic_eye_slash`, `ic_test_1`,
  `ic_test_2`, `il_placeholder`), la fuente `open_sans.ttf` y cadenas en
  `values/strings.xml` (por ejemplo, `example`, `primary_button`,
  `outlined_button`, `accept` y `cancel`). El código común usa los recursos
  generados como `Res.drawable.*`, `Res.font.open_sans` y `Res.string.*`.

## Código específico por plataforma

El contrato `SystemAppearance(isDarkMode)` está declarado como `expect`
`internal` en `commonMain` y tiene implementaciones `actual` también internas:

- `src/androidMain/kotlin/com/kmp/core/design/theme/Theme.android.kt` establece
  el color primario del tema en las barras de estado y navegación y ajusta la
  apariencia de sus iconos.
- `src/iosMain/kotlin/com/kmp/core/design/theme/Theme.ios.kt` ajusta el estilo
  de la barra de estado de UIKit según el modo recibido.

Mantén aquí el comportamiento que depende de Android o UIKit, en su source set
correspondiente; no traslades esas APIs de plataforma a `commonMain`.

## Layout adaptativo por ancho disponible

`AdaptiveScreenContent` permite que una pantalla componga una variante de
teléfono o tablet usando las restricciones reales que recibe del contenedor.
Ambos contenedores son obligatorios:

```kotlin
AdaptiveScreenContent(
    phoneContainer = { PhoneContainer() },
    tabletContainer = { TabletContainer() },
)
```

La selección usa `maxWidth` en `Dp` y las clases definidas en
`adaptive/WindowWidthSizeClass.kt`:

| Ancho disponible | Clase | Contenedor mostrado |
| --- | --- | --- |
| Menos de 600 dp | `Compact` | `phoneContainer` |
| Desde 600 dp y menos de 840 dp | `Medium` | `phoneContainer` |
| 840 dp o más | `Expanded` | `tabletContainer` |

El criterio es el ancho disponible, no la categoría física del dispositivo ni
la orientación. Por eso, un teléfono en landscape puede mostrar
`tabletContainer` si el ancho que recibe la pantalla llega a 840 dp. Conserva
el estado, el `ViewModel`, los eventos y la navegación en el nivel de pantalla;
usa cada slot para la presentación visual correspondiente. Los dos contenedores
deben estar implementados aunque sus diseños iniciales sean similares.

## Añadir un componente reutilizable

Coloca un composable compartido bajo `component/`, en un paquete relacionado con
su responsabilidad. Sigue patrones de componentes existentes, como
`component/button/Button.kt` o `component/field/TextField.kt`:

1. Define una función `@Composable` con parámetros explícitos para contenido,
   interacción y personalización necesaria. Acepta `modifier: Modifier = Modifier`
   cuando corresponda.
2. Reutiliza elementos de Compose/Material y los componentes de texto existentes
   cuando sirvan; usa `Dimens` y los colores de `AppTheme` en vez de repetir
   valores de diseño.
3. Mantén en `commonMain` el comportamiento visual compartido y reserva los
   detalles de plataforma para `androidMain` o `iosMain`.

Uso de un componente existente con los tokens del tema:

```kotlin
AppTheme {
  ButtonPrimary(
    backgroundColor = AppTheme.colors.primary,
    textColors = AppTheme.colors.onPrimary,
    text = "Continuar",
    onClick = onContinue,
  )
}
```

`AppTheme` provee el tema para su contenido; `ButtonPrimary` y los parámetros
mostrados corresponden a la implementación actual. Para un componente nuevo,
sigue ese patrón de API y consulta sus valores desde el tema en lugar de fijar
colores o dimensiones que ya tengan token.

Un componente pertenece a **Core** cuando representa una pieza visual o un
patrón de interacción genérico que puede reutilizarse desde varias features y
no depende de reglas, datos o vocabulario propios de una sola feature. Si su
presentación o comportamiento expresa una necesidad específica de una feature,
déjalo en esa feature y no amplíes Core con una abstracción prematura.

## Relación con otros módulos

Este módulo contiene elementos de presentación reutilizables; no define casos
de uso ni accede a repositorios. Consulta la [guía principal de Core](../../../README.md)
para añadir la dependencia desde un Feature y entender el límite entre Core y
las funcionalidades concretas.

## Convenciones y alcance de la API

- Reutiliza los tokens existentes; evita valores de diseño codificados a mano
  cuando el tema o `Dimens` ya proporcionan el valor correspondiente.
- Conserva el código multiplataforma en `commonMain` y el comportamiento
  dependiente del sistema en sus source sets de plataforma.
- No des por hecho que un símbolo es API pública o estable solo porque aparece
  en el módulo: comprueba su declaración y visibilidad en el código fuente.
  `SystemAppearance`, por ejemplo, es interno. Este README describe lo que hoy
  existe; no promete compatibilidad pública.

# Core Tools

Este módulo reúne utilidades técnicas compartidas por distintas partes de la
aplicación. Es soporte transversal; las reglas de negocio específicas de una
funcionalidad deben permanecer en su módulo o capa correspondiente.

## Qué contiene

- **API de red** (`com.kmp.core.tools.network.api`): `NetworkResult` y
  `NetworkErrorType` representan resultados y tipos de error; `safeApiCall`
  ejecuta una llamada que devuelve `HttpResponse`, interpreta respuestas
  `BaseResponse` y aplica una transformación al cuerpo exitoso.
- **Constantes de red** (`com.kmp.core.tools.network.constants`): `Network`
  contiene la configuración del cliente y el endpoint de login existente.
- **Códigos de servicio** (`com.kmp.core.tools.network.enums`): `CodeServices`
  enumera los códigos de respuesta de servicio.
- **Logging** (`com.kmp.core.tools.logs`): `Log` ofrece métodos `debug`,
  `info`, `error` y `network` sobre Napier. `KmpLogger` permite configurar un
  `Antilog` y registrar mensajes mediante `TypeLog`.
- **Constantes y tipos generales** (`constants`, `enums`): actualmente incluyen
  `ConstantsGeneral.IDLE` y `BuildTypes`.
- **Extensiones** (`com.kmp.core.tools.extensions`): `Int.convertToDouble()`.
- **DataStore** (`com.kmp.core.tools.datastore`): `DS` define el nombre del
  DataStore y las claves de preferencias `USER_TOKEN` y
  `STATUS_USER_CREDENTIALS`; no configura ni crea por sí mismo una instancia de
  DataStore. Estas claves son contratos existentes usados por el módulo
  DataStore y el flujo de login.
- **Compose y animación** (`com.kmp.core.tools.compose.flow` y
  `com.kmp.core.tools.animation`): `CollectWithLifecycleLaunchEffect`
  recopila un `StateFlow` con `collectLatest` dentro de `LaunchedEffect`, y
  `Animation` centraliza transiciones de entrada, salida, escala y
  expansión/contracción vertical. El helper de flujo usa `LaunchedEffect`; no
  implementa por sí solo una API de ciclo de vida.

El módulo es Kotlin Multiplatform: configura Android e iOS, y sus dependencias
comunes incluyen Compose Runtime/UI, Ktor, DataStore y Napier.

## Contratos compartidos existentes

`Network.Client.URL_LOGIN` y las claves `DS.KEY.USER_TOKEN` y
`DS.KEY.STATUS_USER_CREDENTIALS` están declaradas en este módulo de tools y
forman parte de contratos existentes que usa el código de autenticación/login
(directamente o a través del módulo DataStore). Su ubicación refleja el
arreglo actual del código; no significa que sean utilidades genéricas ni que
deba migrarse o cambiarse ahora.

Para nuevas constantes de endpoint o preferencias específicas de una feature,
usa la feature, servicio o módulo propietario. Colócalas en Core solo cuando
representen un contrato genuinamente compartido.

## Ejemplo

Usa los imports del paquete publicado por el módulo y llama la API existente:

```kotlin
import com.kmp.core.tools.extensions.convertToDouble
import com.kmp.core.tools.logs.Log

val amount = 42.convertToDouble()
Log.debug(message = "Importe convertido: $amount", tag = "Checkout")
```

Para manejar fallos de red, prefiere el `NetworkResult` y `safeApiCall`
existentes (`com.kmp.core.tools.network.api`) en vez de crear wrappers
paralelos. `safeApiCall` recibe la llamada y una transformación; la respuesta
debe ser un `BaseResponse` y su campo `success` debe ser `true` para producir
`NetworkResult.Success`.

## Cuándo agregar una utilidad

Agrega aquí un helper cuando resuelva una necesidad técnica reutilizable y
transversal —por ejemplo, una extensión pequeña o una convención compartida de
logging— que tenga sentido para más de una funcionalidad. Si la lógica expresa
una regla o decisión propia de una feature, mantenla en esa feature en lugar
de convertirla en una utilidad global.

## Relación con otros módulos

Network consume los tipos y helpers de red de este módulo; DataStore consume
sus claves de preferencias. Revisa las guías de
[`core:data:network`](../data/network/README.md),
[`core:data:datastore`](../data/datastore/README.md) y el
[mapa principal de Core](../../README.md) antes de agregar una API transversal.

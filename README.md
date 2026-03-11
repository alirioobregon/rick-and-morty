This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop (JVM).

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/iosApp](./iosApp/iosApp) contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform,
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

### Build and Run Web Application

To build and run the development version of the web app, use the run configuration from the run widget
in your IDE's toolbar or run it directly from the terminal:
- for the Wasm target (faster, modern browsers):
  - on macOS/Linux
    ```shell
    ./gradlew :composeApp:wasmJsBrowserDevelopmentRun
    ```
  - on Windows
    ```shell
    .\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
    ```
- for the JS target (slower, supports older browsers):
  - on macOS/Linux
    ```shell
    ./gradlew :composeApp:jsBrowserDevelopmentRun
    ```
  - on Windows
    ```shell
    .\gradlew.bat :composeApp:jsBrowserDevelopmentRun
    ```

### Build and Run iOS Application

To build and run the development version of the iOS app, use the run configuration from the run widget
in your IDE’s toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

---

## Decisiones técnicas

### Arquitectura
Se adoptó **MVVM + Clean Architecture** con tres capas bien definidas:

- **Domain** — modelos de negocio (`Character`) e interfaces de repositorio (`CharacterRepository`). Sin dependencias de frameworks; es la capa más estable.
- **Data** — DTOs con `@Serializable`, API service (`RickAndMortyApi` via Ktor), mapper DTO → dominio y la implementación del repositorio (`CharacterRepositoryImpl`).
- **Presentation** — `ViewModel` + `UiState` (StateFlow) + Composable. La pantalla solo observa el estado; no contiene lógica de negocio.

### Networking — Ktor 3
Se eligió **Ktor** por ser la solución oficial de JetBrains para HTTP en KMP. Se configura una única instancia de `HttpClient` como `single {}` en Koin. El engine HTTP se provee mediante `expect/actual` para cada plataforma:

| Plataforma | Engine |
|---|---|
| Android | `ktor-client-android` |
| iOS | `ktor-client-darwin` |
| JVM (Desktop) | `ktor-client-okhttp` |
| JS / WasmJS | `ktor-client-js` |

### Serialización — kotlinx.serialization
Se usa `ContentNegotiation` con `kotlinx-serialization-json` para deserializar las respuestas de la API directamente a los DTOs anotados con `@Serializable`. Se configura `ignoreUnknownKeys = true` para tolerar campos extra de la API sin romper el parseo.

### Inyección de dependencias — Koin 4
Se eligió **Koin** por su simplicidad y soporte nativo para KMP. El árbol de dependencias se declara en un único `appModule` e inicializa con `KoinApplication {}` en el composable raíz `App()`, evitando la necesidad de una `Application` class en Android para este proyecto.

### Carga de imágenes — Coil 3
**Coil 3** es la única librería de carga de imágenes con soporte real para KMP. Se integra con Ktor como motor de red (`coil-network-ktor3`) para reutilizar el mismo cliente HTTP ya configurado. Se aplican tres optimizaciones:
- `SubcomposeAsyncImage` con `loading` y `error` para manejar todos los estados de la imagen.
- `remember(url) { ImageRequest... }` para evitar reconstruir el request en cada recomposición y no relanzar peticiones al hacer scroll.
- `key = { it.id }` en `LazyColumn` para que Compose reutilice los items sin recrearlos.

### Fuente de datos
Se consume la API pública [Rick and Morty API](https://rickandmortyapi.com/api/character) (sin autenticación). De la respuesta completa solo se usan los campos necesarios para la pantalla actual:

| Campo API | Usado |
|---|---|
| `id` | ✅ |
| `name` | ✅ |
| `status` | ✅ |
| `image` | ✅ |
| `species`, `type`, `gender` | ❌ |
| `origin`, `location` | ❌ |
| `episode` | ❌ |

El DTO y el modelo de dominio declaran únicamente esos cuatro campos. `ignoreUnknownKeys = true` en el deserializador se encarga de descartar el resto de los campos de la respuesta sin errores.

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [YouTrack](https://youtrack.jetbrains.com/newIssue?project=CMP).
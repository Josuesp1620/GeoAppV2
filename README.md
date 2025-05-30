# GeoApp

## Descripción General

GeoApp es una aplicación Android

## Tecnologías Utilizadas

El proyecto está construido utilizando Kotlin como lenguaje principal y aprovecha las siguientes tecnologías y librerías:

*   **Interfaz de Usuario (UI):** Jetpack Compose para una UI declarativa y moderna.
*   **Inyección de Dependencias:** Hilt (Dagger) para gestionar las dependencias en toda la aplicación.
*   **Programación Asíncrona:** Coroutines de Kotlin para operaciones en segundo plano y asíncronas.
*   **Networking:** Ktor como cliente HTTP para realizar peticiones a APIs remotas.
*   **Base de Datos Local:** Room para persistencia de datos estructurados localmente (usuarios, clientes, datos del dispositivo).
*   **Preferencias Locales:** Jetpack DataStore para almacenar datos simples clave-valor (ej. tokens de autenticación, configuraciones de ubicación).
*   **Mapas:** MapLibre GL para la visualización de mapas interactivos.
*   **Localización:**
    *   Integración con Google Play Services Location.
    *   Integración con Huawei Mobile Services (HMS) Location (a través del módulo `geolocation`).
*   **Ciclo de Vida:** Componentes de ciclo de vida de Jetpack (Lifecycle KTX).
*   **Navegación:** Jetpack Navigation Compose para la navegación dentro de la app.
*   **Serialización:** Kotlinx Serialization para la (de)serialización de objetos JSON.
*   **Build System:** Gradle con scripts en Kotlin (KTS).

## Estructura del Proyecto

El proyecto está organizado en los siguientes módulos y directorios principales:

*   **`GeoApp/` (Proyecto Raíz)**
    *   `build.gradle.kts`: Script de configuración principal de Gradle para todo el proyecto.
    *   `settings.gradle.kts`: Define los módulos incluidos en el proyecto (`:app`, `:geolocation`).
    *   `gradle/libs.versions.toml`: Centraliza la gestión de versiones de las dependencias.
    *   `gradlew`, `gradlew.bat`: Scripts del Wrapper de Gradle.

*   **`app/` (Módulo Principal de la Aplicación)**
    *   `build.gradle.kts`: Script de configuración de Gradle específico para el módulo `app`.
    *   `src/main/AndroidManifest.xml`: Manifiesto de la aplicación, define componentes, permisos, etc.
    *   `src/main/java/com/geosolution/geoapp/`: Código fuente principal de la aplicación en Kotlin.
        *   `core/`: Clases base y utilidades transversales.
            *   `application/GeoApp.kt`: Clase `Application` personalizada (inicialización de Hilt, canales de notificación).
            *   `location/`: Servicios y lógica relacionada con la localización (ej. `LocationService`).
        *   `data/`: Lógica de acceso a datos.
            *   `local/`: Componentes para datos locales (Room DB, DataStore).
                *   `dao/`: Data Access Objects para Room.
                *   `database/Database.kt`: Definición de la base de datos Room (`GeoAppDataBase`).
                *   `datastore/`: Clases para interactuar con DataStore.
            *   `remote/`: Componentes para datos remotos (Ktor services).
                *   `api/`: Definiciones de servicios/APIs (ej. `AuthService`).
            *   `repository/`: Implementaciones de los repositorios.
        *   `di/`: Módulos de Hilt para la inyección de dependencias (`DataStoreModule`, `NetWorkModule`, `RepositoryModule`, `RoomModule`, `ServiceModule`).
        *   `domain/`: Lógica de negocio y modelos de dominio.
            *   `model/`: Clases de modelo de datos.
            *   `repository/`: Interfaces de los repositorios.
            *   `use_case/`: Casos de uso de la aplicación.
        *   `presentation/`: Capa de interfaz de usuario (Jetpack Compose).
            *   `screens/`: Composables que representan las diferentes pantallas de la app (ej. `MainActivity`, `MainScreen`).
            *   `ui/theme/`: Definición del tema de la aplicación Compose (`CampusXTheme`).
    *   `src/main/res/`: Recursos de la aplicación (drawables, values, etc.).

*   **`geolocation/` (Módulo de Biblioteca para Geolocalización)**
    *   `build.gradle.kts`: Script de Gradle para este módulo.
    *   `src/main/AndroidManifest.xml`: Manifiesto específico para este módulo (si es necesario).
    *   `src/main/java/com/geosolution/geolocation/`: Código fuente del módulo de geolocalización.
        *   Este módulo parece encapsular la lógica para interactuar con diferentes proveedores de servicios de localización (Google Play Services, Huawei Mobile Services).

## Cómo Empezar

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno de desarrollo:

### Requisitos Previos

*   Android Studio (versión recomendada: Iguana o superior).
*   JDK 17 o superior.
*   Configurar el emulador de Android o tener un dispositivo físico Android conectado.
*   [**TODO: Añadir si se requieren claves de API para MapLibre, Google Maps, o Huawei Services y dónde configurarlas.** Ejemplo: 'Es posible que necesites obtener claves de API para los servicios de mapas y añadirlas en `local.properties` o en `AndroidManifest.xml`.'].

### Clonación del Repositorio

```bash
git clone [URL_DEL_REPOSITORIO] # Reemplazar con la URL real
cd GeoApp
```

### Configuración del Entorno

1.  Abre el proyecto `GeoApp` en Android Studio.
2.  Android Studio debería detectar automáticamente la configuración de Gradle y descargar las dependencias necesarias (puede tomar unos minutos la primera vez).
3.  Asegúrate de que el JDK correcto esté configurado para el proyecto en Android Studio: `File > Settings > Build, Execution, Deployment > Build Tools > Gradle > Gradle JDK`.

### Compilación y Ejecución

1.  Selecciona el módulo `app` en la configuración de ejecución/debug de Android Studio.
2.  Elige un dispositivo virtual (emulador) o un dispositivo físico conectado.
3.  Haz clic en el botón "Run" (▶️) o "Debug" (🐞).

La aplicación debería compilarse e instalarse en el dispositivo/emulador seleccionado.

## Puntos Clave / Características Importantes (Opcional)

*   **Soporte Multi-Proveedor de Localización:** La app puede cambiar entre Google Play Services y Huawei Mobile Services para la geolocalización.
*   **Persistencia de Datos Offline:** Uso de Room y DataStore para funcionalidades offline y almacenamiento de preferencias.
*   **Arquitectura Moderna:** Implementación de principios de Clean Architecture (o similar) con capas de dominio, datos y presentación.
*   **Interfaz Reactiva:** Construida con Jetpack Compose para una UI moderna y eficiente.
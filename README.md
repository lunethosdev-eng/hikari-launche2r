# 🌅 Hikari Launcher

Un launcher de Android moderno, customizable y con animaciones fluidas construido con **Jetpack Compose** y **Kotlin**.

## ✨ Características

- 🎨 **Interfaz moderna** - Diseño Material 3
- 🎭 **Animaciones suaves** - Transiciones fluidas al abrir apps
- ⚙️ **Totalmente personalizable** - Colores, temas y layouts
- 🔧 **Gestos intuitivos** - Navegación natural
- 🚀 **Rendimiento optimizado** - Construido con Compose
- 🌙 **Modo oscuro** - Tema claro y oscuro
- 📱 **Compatible** - Android 7.0+ (API 24+)

## 🛠️ Requisitos

- Android Studio Arctic Fox o superior
- JDK 17
- Android SDK 34
- Gradle 8.0+

## 🚀 Instalación

### Desde el código fuente

```bash
git clone https://github.com/tuusuario/Hikari-Launcher.git
cd Hikari-Launcher
./gradlew assembleDebug
```

### Compilar APK Release

```bash
./gradlew assembleRelease
```

## 🔨 Compilación con GitHub Actions

El proyecto está configurado para compilar automáticamente:

1. En cada push a `main` o `develop`
2. En cada pull request

Los APKs compilados estarán disponibles en la sección de "Artifacts" del workflow.

### Crear un Release

```bash
git tag -a v1.0.0 -m "Version 1.0.0"
git push origin v1.0.0
```

Esto activará la creación automática de un release en GitHub.

## 📦 Dependencias

- `androidx.core:core-ktx`
- `androidx.compose.ui:ui`
- `androidx.compose.material3:material3`
- `androidx.navigation:navigation-compose`
- `io.coil-kt:coil-compose`

## 🎨 Personalización

### Cambiar colores

Edita `app/src/main/kotlin/com/hikari/launcher/ui/theme/Color.kt`:

```kotlin
val HikariPrimary = Color(0xFF2196F3)
val HikariSecondary = Color(0xFF03DAC6)
```

### Cambiar animaciones

Busca `HomeScreen.kt` y modifica los parámetros de `spring()`:

```kotlin
animationSpec = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,
    stiffness = Spring.StiffnessMedium
)
```

## 📋 Características por Implementar

- [ ] Búsqueda de apps
- [ ] Widgets customizables
- [ ] Accesos directos personalizados
- [ ] Sincronización en la nube
- [ ] Temas descargables
- [ ] Soporte para gestos

## 🐛 Reportar Bugs

Si encuentras un error, por favor abre un [Issue](https://github.com/tuusuario/Hikari-Launcher/issues).

## 📄 Licencia

Este proyecto está bajo la licencia **MIT**. Ver `LICENSE` para más detalles.

## 👤 Autor

Creado con ❤️ por [Tu Nombre]

---

**Disfruta de tu nuevo launcher! 🚀**

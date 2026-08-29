# ⚡ Quick Start - Hikari Launcher

## 5 Minutos para Empezar

### Opción 1: Clonar y Abrir (Recomendado)

```bash
# Clonar el repositorio
git clone https://github.com/tuusuario/Hikari-Launcher.git
cd Hikari-Launcher

# Abrir en Android Studio
open -a "Android Studio" .
# O en Windows:
start studio.exe
```

### Opción 2: Crear desde cero

```bash
# Crear carpeta
mkdir Hikari-Launcher
cd Hikari-Launcher

# Copiar todos los archivos del proyecto
# ...
```

---

## Primeros Pasos

### 1️⃣ Sincronizar Gradle (Automático)
Cuando abras en Android Studio, espera a que Gradle sincronice. Esto puede tomar 2-5 minutos la primera vez.

### 2️⃣ Conectar Dispositivo o Emulador
```bash
# Ver dispositivos conectados
adb devices

# Si no ves tu dispositivo, instala los drivers
# Windows: https://developer.android.com/studio/run/win-usb
# Mac/Linux: Check en Settings > About Phone
```

### 3️⃣ Compilar y Ejecutar
```bash
# Opción 1: Desde Android Studio
# Build > Build and Run

# Opción 2: Desde Terminal
./gradlew installDebug
adb shell am start -n com.hikari.launcher/.MainActivity
```

### 4️⃣ Ver en el Emulador
¡Deberías ver Hikari Launcher ejecutándose! 🎉

---

## Comandos Útiles

```bash
# Compilar
./gradlew build                 # Compilación completa
./gradlew assembleDebug         # APK debug
./gradlew assembleRelease       # APK release

# Ejecutar
./gradlew installDebug          # Instalar en dispositivo
./gradlew connectedAndroidTest  # Tests en dispositivo

# Limpiar
./gradlew clean                 # Borrar build cache

# Ver logs
adb logcat | grep "hikari"      # Logs de la app
adb logcat -c                   # Limpiar logs
```

---

## Estructura Básica

```
📁 hikari-launcher/
  📁 app/
    📁 src/
      📁 main/
        📁 kotlin/                    ← Tu código
          📁 com/hikari/launcher/
            MainActivity.kt           ← Activity principal
            📁 ui/screens/            ← Pantallas (Compose)
              HomeScreen.kt           ← Pantalla principal
        📁 res/                       ← Recursos
          📁 values/                  ← Strings, colores, temas
  📁 .github/
    📁 workflows/
      build.yml                       ← CI/CD (GitHub Actions)
  build.gradle.kts                    ← Dependencias
```

---

## Modificar la App

### Cambiar Nombre
En `build.gradle.kts` (app):
```kotlin
android {
    namespace = "com.tu.launcher"    // Cambiar aquí
    defaultConfig {
        applicationId = "com.tu.launcher"
    }
}
```

En `AndroidManifest.xml`:
```xml
<application
    android:label="@string/app_name">  <!-- Cambiar en strings.xml -->
```

En `res/values/strings.xml`:
```xml
<string name="app_name">Tu Launcher</string>
```

### Cambiar Colores
En `ui/theme/Color.kt`:
```kotlin
val HikariPrimary = Color(0xFFTU_COLOR)
val HikariSecondary = Color(0xFFTU_COLOR)
```

### Cambiar Animaciones
En `ui/animations/AnimationUtils.kt` o `ui/screens/HomeScreen.kt`:
```kotlin
animationSpec = spring(
    dampingRatio = Spring.DampingRatioMediumBouncy,  // Cambiar aquí
    stiffness = Spring.StiffnessMedium               // O aquí
)
```

---

## Troubleshooting

### ❌ "Gradle sync failed"
```bash
# Solución
./gradlew clean
./gradlew sync

# O desde Android Studio:
# File > Invalidate Caches... > Invalidate and Restart
```

### ❌ "SDK location not found"
```bash
# Crea un archivo local.properties
echo "sdk.dir=$ANDROID_HOME" > local.properties

# En Windows, usa paths con \\ o /
echo "sdk.dir=C:\\Users\\tu_user\\AppData\\Local\\Android\\sdk" > local.properties
```

### ❌ "No connected devices"
```bash
# Reinstala drivers USB
adb kill-server
adb start-server
adb devices

# O usa emulador: Tools > AVD Manager
```

### ❌ "APK install fails"
```bash
# Desinstalar versión anterior
adb uninstall com.hikari.launcher

# Luego reintentar
./gradlew installDebug
```

---

## Próximos Pasos

### Para Aprender
1. 📖 Lee `DEVELOPMENT.md` para arquitectura
2. 🎯 Lee `ROADMAP.md` para ver qué implementar
3. 💻 Modifica `HomeScreen.kt` para ver cambios en vivo

### Para Contribuir
1. 🔀 Crea una rama: `git checkout -b feature/mi-feature`
2. 💾 Haz cambios y commit: `git commit -m "Descripción"`
3. 📤 Push y abre Pull Request: `git push origin feature/mi-feature`

### Para Publicar
1. 📱 Lee `GITHUB_SETUP.md` para publicar en GitHub
2. 🔐 Firmar APK y crear keystore
3. 📦 Publicar en Play Store (opcional)

---

## Atajos Útiles

| Atajo | Función |
|-------|---------|
| `Ctrl+Shift+A` | Find Action (Android Studio) |
| `Ctrl+B` | Ir a definición |
| `Ctrl+Alt+L` | Formatear código |
| `Ctrl+/` | Comentar línea |
| `Shift+Shift` | Search everywhere |
| `Alt+Enter` | Quick fix |

---

## Recursos

- 📚 [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)
- 🎨 [Material Design 3](https://m3.material.io/)
- 📖 [Android Dev Guide](https://developer.android.com/guide)
- 🔗 [Kotlin Docs](https://kotlinlang.org/docs/)

---

## Ayuda Rápida

```bash
# ¿Cómo veo los logs?
adb logcat

# ¿Cómo instalo una app con APK local?
adb install ruta/al/app.apk

# ¿Cómo limpio datos de la app?
adb shell pm clear com.hikari.launcher

# ¿Cómo veo información del dispositivo?
adb shell getprop
```

---

## Versión Actual
**v1.0.0** - Agosto 2026

## ¿Problemas?
1. Revisa `DEVELOPMENT.md`
2. Revisa los logs: `adb logcat | grep hikari`
3. Limpia gradle: `./gradlew clean`
4. Reinicia Android Studio

---

**¡Feliz desarrollo! 🚀**

*Creado con ❤️ por Prism AI Project*

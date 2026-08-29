# 🔧 Guía de Desarrollo - Hikari Launcher

## Configuración del Ambiente

### 1. Requisitos Previos

```bash
# Verificar Java
java -version  # Debe ser 17 o superior

# Verificar Android SDK
$ANDROID_HOME/tools/bin/sdkmanager --list
```

### 2. Clonar el Repositorio

```bash
git clone https://github.com/tuusuario/Hikari-Launcher.git
cd Hikari-Launcher
```

### 3. Abrir en Android Studio

1. Abre Android Studio
2. `File > Open` → Selecciona la carpeta del proyecto
3. Espera a que Gradle sincronice
4. Conecta un dispositivo o inicia un emulador

## 🏗️ Estructura del Proyecto

```
hikari-launcher/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── kotlin/com/hikari/launcher/
│   │   │   │   ├── MainActivity.kt          # Actividad principal
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/            # Pantallas Compose
│   │   │   │   │   ├── theme/              # Temas y colores
│   │   │   │   │   └── components/         # Componentes reutilizables
│   │   │   │   ├── data/                   # DataStore, Room, etc
│   │   │   │   └── receiver/               # Broadcast receivers
│   │   │   └── res/
│   │   │       ├── values/                 # Strings, dimensiones
│   │   │       ├── drawable/               # Iconos y drawables
│   │   │       └── xml/                    # Configuración XML
│   │   └── test/                          # Tests unitarios
│   ├── build.gradle.kts                   # Configuración de compilación
│   └── proguard-rules.pro                 # Reglas de obfuscación
├── .github/
│   └── workflows/
│       └── build.yml                      # Workflow de GitHub Actions
├── gradle.properties
├── build.gradle.kts
├── README.md
└── DEVELOPMENT.md (Este archivo)
```

## 🚀 Comandos Útiles

```bash
# Compilar aplicación debug
./gradlew assembleDebug

# Compilar aplicación release
./gradlew assembleRelease

# Ejecutar tests
./gradlew test

# Ejecutar en dispositivo conectado
./gradlew installDebug

# Limpiar build
./gradlew clean

# Verificar dependencias
./gradlew dependencies

# Ver logs en tiempo real
adb logcat | grep "hikari"
```

## 📐 Agregar Nuevas Pantallas

1. Crea un nuevo archivo en `ui/screens/`:
   ```kotlin
   @Composable
   fun MiPantalla() {
       // Tu código aquí
   }
   ```

2. Agrega la navegación en `ui/navigation/NavGraph.kt`:
   ```kotlin
   composable("mi_pantalla") {
       MiPantalla()
   }
   ```

## 🎨 Personalizar Temas

1. Edita `ui/theme/Color.kt` para cambiar colores
2. Edita `ui/theme/Type.kt` para cambiar tipografía
3. Edita `ui/theme/Theme.kt` para aplicar globalmente

## 🔄 Agregar Nuevas Dependencias

1. Edita `app/build.gradle.kts`
2. Agrega en la sección `dependencies { }`
3. Ejecuta `./gradlew build`

## 🐛 Debugging

### Con Android Studio

1. `Run > Debug 'app'`
2. Coloca breakpoints haciendo clic en los números de línea
3. Usa Logcat para ver logs

### Con adb

```bash
# Conectar dispositivo
adb devices

# Ver logs
adb logcat

# Instalar APK
adb install app/build/outputs/apk/debug/app-debug.apk

# Ejecutar con permisos
adb shell am start -n com.hikari.launcher/.MainActivity
```

## 📦 Generar APK Release

1. Crea un keystore (primera vez):
   ```bash
   keytool -genkey -v -keystore release.keystore \
     -keyalg RSA -keysize 2048 -validity 10000 \
     -alias hikari_launcher_key
   ```

2. Añade el keystore a `local.properties`:
   ```properties
   storeFile=../release.keystore
   storePassword=tu_password
   keyAlias=hikari_launcher_key
   keyPassword=tu_key_password
   ```

3. Compila el APK:
   ```bash
   ./gradlew assembleRelease
   ```

## 🔗 GitHub Actions

El workflow en `.github/workflows/build.yml` compila automáticamente:

- En cada push a `main` o `develop`
- En cada pull request a `main`
- Crea artifacts descargables

### Crear un Release automático

```bash
git tag -a v1.0.0 -m "Release 1.0.0"
git push origin v1.0.0
```

## 📝 Estilo de Código

- **Kotlin**: Sigue la [guía oficial](https://kotlinlang.org/docs/coding-conventions.html)
- **Compose**: Usar composables funcionales
- **Nombres**: camelCase para variables/métodos, PascalCase para clases
- **Comentarios**: Documenta funciones públicas

## 🧪 Testing

```kotlin
@Test
fun testAppLaunch() {
    val intent = Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java)
    val activity = ActivityScenario.launch<MainActivity>(intent)
    activity.onActivity { activity ->
        assertNotNull(activity.findViewById(android.R.id.content))
    }
}
```

## 🎯 Checklist antes de hacer Push

- [ ] Código compilado sin errores
- [ ] Sin warnings de Kotlin Lint
- [ ] Tests pasan correctamente
- [ ] Comentarios en código complejo
- [ ] git commit con mensaje descriptivo

## 📚 Recursos Útiles

- [Jetpack Compose Docs](https://developer.android.com/jetpack/compose)
- [Kotlin Official](https://kotlinlang.org/)
- [Material Design 3](https://m3.material.io/)
- [Android Dev Guide](https://developer.android.com/guide)

## 🆘 Solución de Problemas

### Error: "SDK location not found"
```bash
echo "sdk.dir=/path/to/android/sdk" > local.properties
```

### Error: Gradle sync failed
```bash
./gradlew clean
./gradlew sync
```

### APK size too large
Activa minify en build.gradle.kts:
```kotlin
isMinifyEnabled = true
```

---

**¡Happy coding! 🚀**

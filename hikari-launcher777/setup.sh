#!/bin/bash
# Hikari Launcher - Setup Script
# Script para configurar el ambiente de desarrollo

set -e

echo "🌅 Configurando Hikari Launcher..."
echo ""

# Verificar Java
echo "✓ Verificando Java..."
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado. Por favor instala JDK 17+"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -1 | grep -oP '(?<=version ")[^"]*')
echo "  ✅ Java $JAVA_VERSION encontrado"

# Verificar Android SDK
echo ""
echo "✓ Verificando Android SDK..."
if [ -z "$ANDROID_HOME" ]; then
    echo "⚠️  ANDROID_HOME no está configurado"
    echo "   En Linux/Mac, agrega a ~/.bash_profile o ~/.zshrc:"
    echo "   export ANDROID_HOME=\$HOME/Library/Android/sdk"
    echo "   export PATH=\$PATH:\$ANDROID_HOME/tools"
fi

# Hacer gradlew ejecutable
echo ""
echo "✓ Configurando Gradle..."
chmod +x gradlew
echo "  ✅ Gradlew es ahora ejecutable"

# Sincronizar Gradle
echo ""
echo "✓ Sincronizando dependencias de Gradle..."
echo "  (Esto puede tomar unos minutos la primera vez)"
./gradlew sync

# Limpiar
echo ""
echo "✓ Limpiando build anterior..."
./gradlew clean

# Build
echo ""
echo "✓ Compilando proyecto..."
./gradlew build

echo ""
echo "╔═══════════════════════════════════════════════════════════╗"
echo "║          ✅ ¡SETUP COMPLETADO EXITOSAMENTE!             ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""
echo "📝 Próximos pasos:"
echo ""
echo "  1. Abre el proyecto en Android Studio:"
echo "     open -a 'Android Studio' ."
echo ""
echo "  2. Conecta un dispositivo o inicia un emulador"
echo ""
echo "  3. Ejecuta la app:"
echo "     ./gradlew installDebug"
echo ""
echo "  4. O desde Android Studio:"
echo "     Build > Run App"
echo ""
echo "📚 Documentación:"
echo "  - README.md - Descripción general"
echo "  - QUICKSTART.md - Inicio rápido"
echo "  - DEVELOPMENT.md - Guía de desarrollo"
echo ""
echo "🎉 ¡Happy coding!"

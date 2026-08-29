# 📦 Guía: Publicar Hikari Launcher en GitHub

## Paso 1: Crear el repositorio en GitHub

1. Ve a [GitHub.com](https://github.com)
2. Inicia sesión en tu cuenta
3. Haz clic en el **+** en la esquina superior derecha
4. Selecciona **New repository**
5. Completa:
   - **Repository name**: `Hikari-Launcher`
   - **Description**: Un launcher Android customizable con animaciones
   - **Public/Private**: Public (para que otros lo vean)
   - **Initialize this repository with**: No seleccionar nada
6. Haz clic en **Create repository**

## Paso 2: Subir el código

Desde la carpeta del proyecto:

```bash
# Inicializar git (si no está iniciado)
git init

# Agregar todos los archivos
git add .

# Crear commit inicial
git commit -m "Commit inicial: Hikari Launcher v1.0.0"

# Agregar el repositorio remoto
git remote add origin https://github.com/tuusuario/Hikari-Launcher.git

# Subir al repositorio
git branch -M main
git push -u origin main
```

## Paso 3: Configurar GitHub Actions

El archivo `.github/workflows/build.yml` ya está configurado. Simplemente:

1. Ve a tu repositorio en GitHub
2. Haz clic en la pestaña **Actions**
3. Verifica que el workflow aparezca
4. En cada push, compilará automáticamente los APKs

## Paso 4: Crear el primer Release

```bash
# Crear un tag
git tag -a v1.0.0 -m "Release inicial de Hikari Launcher"

# Subir el tag
git push origin v1.0.0
```

Luego en GitHub:
1. Ve a **Releases**
2. El release se creará automáticamente
3. Descarga los APKs compilados

## Paso 5: Proteger la rama main (Opcional)

En **Settings > Branches**:

1. Haz clic en **Add rule**
2. Patrón de rama: `main`
3. Activa:
   - ✅ Require a pull request before merging
   - ✅ Require status checks to pass before merging
   - ✅ Require branches to be up to date

## Paso 6: Configurar Secrets (Si compilas APK Signed)

1. Ve a **Settings > Secrets**
2. Agrega los secretos necesarios:
   - `KEYSTORE_FILE`: Base64 del archivo keystore
   - `KEYSTORE_PASSWORD`: Tu contraseña del keystore
   - `KEY_ALIAS`: Alias de tu llave
   - `KEY_PASSWORD`: Contraseña de la llave

## Paso 7: Agregar badge al README

En tu `README.md`, agrega esto:

```markdown
[![Build Status](https://github.com/tuusuario/Hikari-Launcher/workflows/Compilar%20Hikari%20Launcher/badge.svg)](https://github.com/tuusuario/Hikari-Launcher/actions)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
```

## Paso 8: Habilitar Discussions (Opcional)

En **Settings > Discussions**, activa para que usuarios reporten bugs y sugieran features.

## Paso 9: Agregar Topics

En **Settings**, agrega topics como:
- `android`
- `launcher`
- `compose`
- `kotlin`
- `material-design`

## Estructura de Ramas Recomendada

```
main (versiones estables)
  ├── develop (rama de desarrollo)
  │   ├── feature/search-bar
  │   ├── feature/dark-mode
  │   └── bugfix/animation-crash
  └── release/v1.1.0
```

## Flujo de trabajo Git recomendado

```bash
# 1. Crear rama de feature
git checkout -b feature/mi-feature develop

# 2. Hacer cambios y commits
git add .
git commit -m "Agregar nueva feature"

# 3. Subir rama
git push origin feature/mi-feature

# 4. Crear Pull Request en GitHub
# (desde la interfaz web)

# 5. Una vez aprobado, merge a develop
git checkout develop
git pull origin develop
git merge --no-ff feature/mi-feature
git push origin develop

# 6. Borrar rama local y remota
git branch -d feature/mi-feature
git push origin --delete feature/mi-feature
```

## Archivo .gitignore ya configurado ✅

El proyecto incluye un `.gitignore` completo que excluye:
- Archivos build
- APKs
- Archivos IDE
- Keystores locales
- Archivos temporales

## Próximos pasos

1. ⭐ Pide que otros den "Star" al proyecto
2. 🐛 Abre Issues para bugs conocidos
3. 🎯 Crea milestones para versiones futuras
4. 📝 Escribe documentation completa
5. 🤝 Acepta Pull Requests de contribuidores

## Comandos útiles

```bash
# Ver remoto
git remote -v

# Cambiar URL remota
git remote set-url origin https://github.com/nuevouser/Hikari-Launcher.git

# Ver todas las ramas
git branch -a

# Borrar rama local
git branch -d nombre-rama

# Borrar rama remota
git push origin --delete nombre-rama

# Ver commits
git log --oneline --graph --all

# Deshacer último commit (no pusheado)
git reset --soft HEAD~1
```

---

**¡Tu launcher ya está en GitHub! 🚀**

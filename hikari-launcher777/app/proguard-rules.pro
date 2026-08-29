# This is a configuration file for ProGuard.
# http://proguard.sourceforge.net/index.html#manual/usage.html
# http://proguard.sourceforge.net/index.html#manual/examples.html

# Preserve line numbers for debugging stack traces
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# Keep our app's classes
-keep class com.hikari.launcher.** { *; }

# Keep data classes
-keepclassmembers class com.hikari.launcher.** {
  *** get*();
  void set*(***);
}

# Keep Compose
-keep class androidx.compose.** { *; }
-keep interface androidx.compose.** { *; }

# Keep Kotlin
-keep class kotlin.** { *; }
-keepclassmembers class kotlin.** {
  *** get*();
  void set*(***);
}

# Keep AndroidX
-keep class androidx.** { *; }
-keep interface androidx.** { *; }

# Keep Material3
-keep class com.google.android.material.** { *; }

# Remove logging
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

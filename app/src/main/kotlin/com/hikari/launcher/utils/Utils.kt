package com.hikari.launcher.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import kotlin.math.log10
import kotlin.math.pow

object AppUtils {

    /**
     * Obtiene el tamaño de una aplicación instalada
     */
    fun getAppSize(context: Context, packageName: String): Long {
        return try {
            val pm = context.packageManager
            val info = pm.getApplicationInfo(packageName, 0)
            info.publicSourceDir?.let { java.io.File(it).length() } ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Verifica si una app es de sistema
     */
    fun isSystemApp(appInfo: ApplicationInfo): Boolean {
        return (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
    }

    /**
     * Obtiene la última fecha de uso de una app
     */
    fun getLastUsedTime(context: Context, packageName: String): Long {
        return try {
            val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as? android.app.usage.UsageStatsManager
            val calendar = java.util.Calendar.getInstance()
            calendar.add(java.util.Calendar.DAY_OF_YEAR, -7)
            
            val stats = usageStatsManager?.queryUsageStats(
                android.app.usage.UsageStatsManager.INTERVAL_BEST,
                calendar.timeInMillis,
                System.currentTimeMillis()
            )?.filter { it.packageName == packageName }
            
            stats?.maxByOrNull { it.lastTimeUsed }?.lastTimeUsed ?: 0L
        } catch (e: Exception) {
            0L
        }
    }
}

object StringUtils {

    /**
     * Formatea bytes a formato legible
     */
    fun formatBytes(bytes: Long): String {
        if (bytes <= 0) return "0 B"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (log10(bytes.toDouble()) / log10(1024.0)).toInt()
        return String.format(
            "%.1f %s",
            bytes / 1024.0.pow(digitGroups.toDouble()),
            units[digitGroups]
        )
    }

    /**
     * Capitaliza la primera letra
     */
    fun capitalizeFirstLetter(text: String): String {
        return if (text.isNotEmpty()) {
            text[0].uppercase() + text.substring(1)
        } else {
            text
        }
    }

    /**
     * Trunca el texto a una longitud específica
     */
    fun truncate(text: String, maxLength: Int, suffix: String = "..."): String {
        return if (text.length > maxLength) {
            text.substring(0, maxLength - suffix.length) + suffix
        } else {
            text
        }
    }
}

object ColorUtils {

    /**
     * Convierte un color HEX string a un Int
     */
    fun parseColor(hexColor: String): Int {
        val hex = if (hexColor.startsWith("#")) hexColor.substring(1) else hexColor
        return android.graphics.Color.parseColor("#$hex")
    }

    /**
     * Convierte un Int de color a HEX string
     */
    fun colorToHex(color: Int): String {
        return String.format("#%06X", 0xFFFFFF and color)
    }

    /**
     * Genera un color determinista basado en un string
     */
    fun generateColorFromString(input: String): Int {
        val hash = input.hashCode()
        val hue = (hash % 360).toFloat()
        val saturation = 0.8f
        val value = 0.9f
        return android.graphics.Color.HSVToColor(floatArrayOf(hue, saturation, value))
    }
}

object DateTimeUtils {

    /**
     * Formatea un timestamp a un string legible
     */
    fun formatTime(millis: Long): String {
        val formatter = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", java.util.Locale.getDefault())
        return formatter.format(java.util.Date(millis))
    }

    /**
     * Obtiene tiempo relativo (ej: "hace 2 horas")
     */
    fun getRelativeTime(millis: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - millis

        return when {
            diff < 60 * 1000 -> "Hace unos segundos"
            diff < 60 * 60 * 1000 -> "Hace ${diff / (60 * 1000)} minutos"
            diff < 24 * 60 * 60 * 1000 -> "Hace ${diff / (60 * 60 * 1000)} horas"
            diff < 7 * 24 * 60 * 60 * 1000 -> "Hace ${diff / (24 * 60 * 60 * 1000)} días"
            else -> formatTime(millis)
        }
    }
}

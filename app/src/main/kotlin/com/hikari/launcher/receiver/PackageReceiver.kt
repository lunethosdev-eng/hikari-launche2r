package com.hikari.launcher.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PackageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_PACKAGE_ADDED -> {
                // Nueva app instalada
            }
            Intent.ACTION_PACKAGE_REMOVED -> {
                // App desinstalada
            }
            Intent.ACTION_PACKAGE_CHANGED -> {
                // App actualizada
            }
        }
    }
}

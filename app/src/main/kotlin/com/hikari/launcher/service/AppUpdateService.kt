package com.hikari.launcher.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class AppUpdateService : Service() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var packageReceiver: BroadcastReceiver? = null

    override fun onCreate() {
        super.onCreate()
        setupPackageReceiver()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun setupPackageReceiver() {
        packageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_PACKAGE_ADDED -> {
                        val packageName = intent.data?.schemeSpecificPart
                        handlePackageAdded(packageName ?: "")
                    }
                    Intent.ACTION_PACKAGE_REMOVED -> {
                        val packageName = intent.data?.schemeSpecificPart
                        handlePackageRemoved(packageName ?: "")
                    }
                    Intent.ACTION_PACKAGE_CHANGED -> {
                        val packageName = intent.data?.schemeSpecificPart
                        handlePackageChanged(packageName ?: "")
                    }
                }
            }
        }

        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addAction(Intent.ACTION_PACKAGE_CHANGED)
            addDataScheme("package")
        }

        ContextCompat.registerBroadcastReceiver(
            this,
            packageReceiver!!,
            intentFilter,
            ContextCompat.RECEIVER_EXPORTED
        )
    }

    private fun handlePackageAdded(packageName: String) {
        scope.launch {
            // Actualizar lista de apps
            // Notificar a la UI
        }
    }

    private fun handlePackageRemoved(packageName: String) {
        scope.launch {
            // Remover app de la lista
            // Notificar a la UI
        }
    }

    private fun handlePackageChanged(packageName: String) {
        scope.launch {
            // Actualizar info del app
            // Notificar a la UI
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (packageReceiver != null) {
            unregisterReceiver(packageReceiver)
        }
        job.cancel()
    }
}

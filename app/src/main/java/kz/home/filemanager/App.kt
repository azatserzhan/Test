package kz.home.filemanager

import android.app.Application

internal class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KoinManager.init(this)
    }
}

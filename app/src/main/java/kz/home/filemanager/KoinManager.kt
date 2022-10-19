package kz.home.filemanager

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

internal object KoinManager {
    fun init(context: Context) {
        startKoin {
            androidContext(context)
            modules(AppModule.create())
        }
    }
}

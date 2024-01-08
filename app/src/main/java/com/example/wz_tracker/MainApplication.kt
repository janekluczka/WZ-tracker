package com.example.wz_tracker

import android.app.Application
import com.example.wz_tracker.koin.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() = startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(getKoinModules())
        }

    private fun getKoinModules() = listOf(
        databaseModule,
        repositoryModule,
        matchesListViewModelModule,
        matchDetailsViewModelModule,
        lifetimeStatsViewModelModule,
        usernameViewModelModule
    )

}
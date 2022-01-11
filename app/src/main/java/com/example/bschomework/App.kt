package com.example.bschomework

import android.app.Application
import com.example.bschomework.koin.networkModule
import com.example.bschomework.koin.repositoryModule
import com.example.bschomework.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}
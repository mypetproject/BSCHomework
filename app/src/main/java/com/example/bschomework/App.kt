package com.example.bschomework

import android.app.Application
import android.content.Context
import com.example.bschomework.dagger.AppComponent
import com.example.bschomework.dagger.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger()
    }

    private fun initDagger() = DaggerAppComponent.builder()
        .context(this)
        .build()
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> this.applicationContext.appComponent
    }
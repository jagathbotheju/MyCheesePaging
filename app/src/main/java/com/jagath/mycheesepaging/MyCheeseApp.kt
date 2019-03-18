package com.jagath.mycheesepaging

import android.app.Application
import com.jagath.mycheesepaging.di.appModule
import org.koin.android.ext.android.startKoin

class MyCheeseApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}
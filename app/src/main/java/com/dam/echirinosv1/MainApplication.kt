package com.dam.echirinosv1

import android.app.Application
import com.dam.echirinosv1.data.AppContainer
import com.dam.echirinosv1.data.DefaultAppContainer

class MainApplication : Application() {

    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
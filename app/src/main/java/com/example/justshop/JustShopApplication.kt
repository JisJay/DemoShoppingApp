package com.example.justshop

import android.app.Application
import com.example.justshop.data.AppContainer
import com.example.justshop.data.DefaultAppContainer

class JustShopApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
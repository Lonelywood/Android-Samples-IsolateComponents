package com.github.lonelywood.architecture.android

import android.app.Application

class App: Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    init {
        instance = this
    }
}
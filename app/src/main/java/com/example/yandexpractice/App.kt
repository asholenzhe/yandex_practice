package com.example.yandexpractice

import android.app.Application
import com.example.yandexpractice.creator.Creator

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Creator.init(this)
    }
}

package com.clearscore.test

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ClearScoreApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
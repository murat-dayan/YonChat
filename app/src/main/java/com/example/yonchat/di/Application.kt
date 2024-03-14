package com.example.yonchat.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Application @Inject constructor() : Application() {
}
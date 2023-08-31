package com.gaosi.moduledemo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/4/6 15:34
 *
 **/

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
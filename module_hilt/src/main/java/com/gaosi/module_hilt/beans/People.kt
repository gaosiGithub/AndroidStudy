package com.gaosi.module_hilt.beans

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class People @Inject constructor(
        @ActivityContext private val context: Context,
        private val blackPeople : BlackPeople
) {

    fun eat() {
        Log.d("People", "people eat : $context")
    }

    fun walk() {
        Log.d("People", "people walk : $blackPeople")
    }
}
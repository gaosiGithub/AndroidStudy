package com.gaosi.module_hilt.beans

import android.util.Log
import com.gaosi.module_hilt.constants.Utils.TAG
import com.gaosi.module_hilt.intefaces.IAnimal
import javax.inject.Inject

class Dog @Inject constructor(): IAnimal {
    override fun play() {
        Log.d(TAG, " dog play")
    }
}
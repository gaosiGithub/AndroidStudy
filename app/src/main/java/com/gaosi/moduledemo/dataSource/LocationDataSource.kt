package com.gaosi.moduledemo.dataSource

import android.util.Log
import com.gaosi.moduledemo.utils.Constants.TAG
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/8/17 10:25
 *
 **/
class LocationDataSource() {

    val locationSource: Flow<String> = channelFlow {

        val callBack = object : LocationCallBack {
            override fun onLocationResult(location: String) {
                Log.d(TAG, "callbackFlow location : $location")

                try {
                    offer(location)
                } catch (e: Exception) {

                }
            }

        }

        requestData(callBack)

        awaitClose {
            //unregister callback
        }
    }

    private fun requestData(callback: LocationCallBack) {
        GlobalScope.launch {
            var count = 0
            while (true) {
                count++
                delay(1000)
                callback.onLocationResult(count.toString())
            }
        }
    }
}
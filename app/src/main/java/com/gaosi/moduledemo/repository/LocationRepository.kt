package com.gaosi.moduledemo.repository

import com.gaosi.moduledemo.dataSource.LocationDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/8/17 10:43
 *
 **/
class LocationRepository(
    scope: CoroutineScope
) {

    private val locationDataSource: LocationDataSource = LocationDataSource()

    val locations: Flow<String> =
        locationDataSource.locationSource.shareIn(scope, SharingStarted.WhileSubscribed())
}
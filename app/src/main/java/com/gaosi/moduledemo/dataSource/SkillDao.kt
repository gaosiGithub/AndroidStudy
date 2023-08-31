package com.gaosi.moduledemo.dataSource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gaosi.moduledemo.bean.MainData
import kotlinx.coroutines.flow.Flow

@Dao
interface SkillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(skill: MainData)

    @Query("select * from skill")
    fun queryAllUser(): Flow<List<MainData>>

}
package com.gaosi.moduledemo.dataSource

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gaosi.moduledemo.bean.MainData
import com.gaosi.moduledemo.utils.Constants.TAG

@Database(entities = [MainData::class], version = 1, exportSchema = false)
abstract class SkillDataBase : RoomDatabase() {

    abstract fun skillDao(): SkillDao

    companion object {

        fun newInstance(context: Context): SkillDataBase {
            Log.i(TAG,"Skill Data Base init")
            return Room.databaseBuilder(context, SkillDataBase::class.java, "db_skill").build()
        }
    }
}
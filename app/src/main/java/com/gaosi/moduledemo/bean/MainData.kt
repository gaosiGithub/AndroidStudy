package com.gaosi.moduledemo.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gaosi.moduledemo.R

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/8/18 13:53
 *
 **/
@Entity(tableName = "skill")
data class MainData(

        @PrimaryKey
        @ColumnInfo(name = "skill_id")
        var id: Int = 0,

        @ColumnInfo(name = "skill_content")
        val content: String = "",

        @ColumnInfo(name = "skill_content_image")
        val contentImage: Int = R.mipmap.scene1
)

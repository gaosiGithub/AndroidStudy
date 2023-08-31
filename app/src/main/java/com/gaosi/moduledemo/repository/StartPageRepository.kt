package com.gaosi.moduledemo.repository

import com.gaosi.moduledemo.R
import com.gaosi.moduledemo.bean.MainData
import com.gaosi.moduledemo.dataSource.SkillDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StartPageRepository @Inject constructor(
        private val skillDao: SkillDao
) {

    val sourceData = skillDao.queryAllUser()

    private var contentList = mutableListOf(
            "设计模式",
            "Hilt学习",
            "高级UI",
            "Jetpack学习"
    )

    private var contentImageList = mutableListOf(
            R.mipmap.scene1,
            R.mipmap.scene2,
            R.mipmap.scene3,
            R.mipmap.scene4
    )

    suspend fun getSourceData() = withContext(Dispatchers.IO) {
        for (i in contentList.indices) {
            skillDao.insert(MainData(i, contentList[i], contentImageList[i]))
        }
    }

}
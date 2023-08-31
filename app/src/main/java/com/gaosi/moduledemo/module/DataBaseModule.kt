package com.gaosi.moduledemo.module

import android.content.Context
import com.gaosi.moduledemo.dataSource.SkillDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) = SkillDataBase.newInstance(context)

    @Provides
    fun provideSkillDao(db: SkillDataBase) = db.skillDao()

}
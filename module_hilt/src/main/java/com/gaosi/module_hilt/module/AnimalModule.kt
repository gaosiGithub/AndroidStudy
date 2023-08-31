package com.gaosi.module_hilt.module

import com.gaosi.module_hilt.annotations.BindCat
import com.gaosi.module_hilt.annotations.BindDog
import com.gaosi.module_hilt.beans.Cat
import com.gaosi.module_hilt.beans.Dog
import com.gaosi.module_hilt.intefaces.IAnimal
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class AnimalModule {

    @BindDog
    @Binds
    abstract fun bindDog(animal: Dog): IAnimal

    @BindCat
    @Binds
    abstract fun bindCat(animal: Cat): IAnimal
}
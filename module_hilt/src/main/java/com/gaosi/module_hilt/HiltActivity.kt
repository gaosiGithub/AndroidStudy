package com.gaosi.module_hilt

import com.gaosi.module_base.base.BaseActivity
import com.gaosi.module_hilt.annotations.BindCat
import com.gaosi.module_hilt.annotations.BindDog
import com.gaosi.module_hilt.beans.People
import com.gaosi.module_hilt.intefaces.IAnimal
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltActivity : BaseActivity() {

    //构造函数方式注入
    @Inject
    lateinit var people: People

    @BindDog
    @Inject
    lateinit var animal1: IAnimal

    @BindCat
    @Inject
    lateinit var animal2: IAnimal

    override fun showTopBar(): Boolean {
        return true
    }

    override fun initData() {
        setTitle("Hilt依赖注入", true)

        people.eat()
        people.walk()
        animal1.play()
        animal2.play()
    }

    override fun initListener() {

    }

    override fun layoutId(): Int {
        return R.layout.activity_hilt
    }


}
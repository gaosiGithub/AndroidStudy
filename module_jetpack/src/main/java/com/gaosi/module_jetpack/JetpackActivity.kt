package com.gaosi.module_jetpack

import com.gaosi.module_base.base.BaseActivity

class JetpackActivity : BaseActivity() {
    override fun showTopBar(): Boolean {
        return true
    }

    override fun initData() {
        setTitle("jetpack", true)
    }

    override fun initListener() {

    }

    override fun layoutId(): Int {
        return R.layout.jetpack_activity
    }
}
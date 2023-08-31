package com.gaosi.moduledemo.views

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.gaosi.module_base.base.BaseActivity
import com.gaosi.moduledemo.R

class SplashActivity : BaseActivity() {
    override fun showTopBar(): Boolean {
        return false
    }

    override fun initData() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }, 1000)

    }

    override fun initListener() {

    }

    override fun layoutId(): Int {
        return R.layout.splash_activity
    }
}
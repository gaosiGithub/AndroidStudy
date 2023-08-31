package com.example.module_master_ui

import android.animation.ObjectAnimator
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.gaosi.module_base.base.BaseActivity

/**
 * @Author : gaosi
 * @Time : 2023/8/25 22:56
 * @Description:
 */
class MasterUiActivity : BaseActivity() {
    override fun showTopBar(): Boolean {
        return true
    }

    override fun initData() {
        setTitle("高级UI", true)
        setPaddingTopAndTitleIsWhite(showStatusBar = false)

        Handler(Looper.getMainLooper()).postDelayed({
            val animator = ObjectAnimator.ofFloat(findViewById(R.id.simple_color_change_txt), "percent", 0f, 1f)
            animator.duration = 3500
            animator.repeatCount = 100
            animator.addUpdateListener {
                Log.d("Simple","it : " + it.animatedFraction )
            }
            animator.start()
        }, 1000)

    }

    override fun initListener() {
    }

    override fun layoutId(): Int {
        return R.layout.master_ui_activity
    }
}
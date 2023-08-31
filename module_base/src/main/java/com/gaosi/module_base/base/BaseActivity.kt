package com.gaosi.module_base.base

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.gaosi.module_base.R
import kotlinx.android.synthetic.main.base_activity.*


/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/8/17 20:40
 *
 **/
abstract class BaseActivity : AppCompatActivity() {

    lateinit var insertControllerCompat: WindowInsetsControllerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.base_activity)

        LayoutInflater.from(this).inflate(layoutId(), base_content)
        baseInit()
        initData()
        initListener()

        base_topbar.visibility = if (showTopBar()) View.VISIBLE else View.GONE
    }

    private fun baseInit() {
        base_back.setOnClickListener {
            onClickBack()
        }

        //设置沉浸式
        insertControllerCompat = WindowCompat.getInsetsController(window, window.decorView)!!
        //页面布局是否在状态栏下方， false：侵入状态栏
        WindowCompat.setDecorFitsSystemWindows(window, true)
        //状态栏字体明暗主题，true：黑色（默认黑色）， false为白色
        insertControllerCompat.isAppearanceLightStatusBars = true
        //状态栏背景色设置标题栏颜色，白色
        window.statusBarColor = Color.TRANSPARENT
    }

    //在继承BaseActivity的类渲染完(setContentView)页面后，再调用该方法，可以设置状态栏文字颜色
    fun setPaddingTopAndTitleIsWhite(
            showStatusBar: Boolean = true,
            decorFitsSystemWindow: Boolean = false,
            isWhite: Boolean = true,
            statusBarColor: Int = Color.WHITE) {
        if (showStatusBar) {
            showSystemUI()
            WindowCompat.setDecorFitsSystemWindows(window, decorFitsSystemWindow)
            insertControllerCompat.isAppearanceLightStatusBars = !isWhite
            window.statusBarColor = statusBarColor
        } else {
            hideSystemUI()
        }
    }

    private fun hideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, base_content).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, base_content).show(WindowInsetsCompat.Type.systemBars())
    }

    private fun getDisplayCutout(view: View): DisplayCutoutCompat? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val windowInsets = view.rootWindowInsets
            val displayCutout = windowInsets.displayCutout
            if (displayCutout != null) {
                val safeInsets = Rect(displayCutout.safeInsetLeft,
                        displayCutout.safeInsetTop,
                        displayCutout.safeInsetRight,
                        displayCutout.safeInsetBottom)
                return DisplayCutoutCompat(safeInsets, displayCutout.boundingRects)
            }

        }
        return null
    }

    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    abstract fun showTopBar(): Boolean

    abstract fun initData()

    abstract fun initListener()

    abstract fun layoutId(): Int

    open fun onClickBack() {
        finish()
    }

    open fun setTitle(title: String, showLeftIcon: Boolean) {
        base_title.text = title
        base_back.visibility = if (showLeftIcon) View.VISIBLE else View.GONE
    }

}
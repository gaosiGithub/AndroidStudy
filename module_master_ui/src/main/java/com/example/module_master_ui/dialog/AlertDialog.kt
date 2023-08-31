package com.example.module_master_ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.*
import android.widget.TextView
import com.example.module_master_ui.R
import com.gaosi.lib_ui.utils.setDrawable

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/3/8 9:23
 *
 **/

fun createDialog(
    context: Context,
    body: AlertDialog.Builder.() -> Unit
) {
    val dialog = AlertDialog.Builder(context)
    dialog.body()
}

fun AlertDialog.showDialog() {
    show()
    val mWindow = window
    mWindow?.setBackgroundDrawableResource(R.color.transparent)
    val group: ViewGroup = mWindow?.decorView as ViewGroup
    val child: ViewGroup = group.getChildAt(0) as ViewGroup
    child.post {
        val param: WindowManager.LayoutParams? = mWindow.attributes
        param?.width = (DensityUtils.getScreenWidth() * 0.8).toInt()
        param?.gravity = Gravity.CENTER
        mWindow.setGravity(Gravity.CENTER)
        mWindow.attributes = param
    }
}

//当你调用一个内联函数的时候，编译器不会产生一次函数调用，不会有压栈出栈。而是会在每次调用时，将inline function中的代码直接复制到调用处
inline fun AlertDialog.Builder.rootLayout(
    render: GradientDrawable.() -> Unit = {},
    job: View.() -> Unit
) {

    val root = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)

    with(GradientDrawable()) {
        //默认定义的背景设置
        colors = intArrayOf(Color.parseColor("#f7c653"), Color.parseColor("#f7cccc"))
        orientation = GradientDrawable.Orientation.TOP_BOTTOM
        cornerRadius = DensityUtils.dp2px(10f).toFloat()

        //自定义背景设置
        render()
        root.background = this
    }

    root.setPadding(
        DensityUtils.dp2px(10f),
        DensityUtils.dp2px(10f),
        DensityUtils.dp2px(10f),
        DensityUtils.dp2px(10f)
    )
    root.job()

    setView(root).create().showDialog()
}

inline fun View.title(titleJob: TextView.() -> Unit) {
    context
    val title = findViewById<TextView>(R.id.dialog_title)
    title.titleJob()
}

inline fun View.message(messageJob: TextView.() -> Unit) {
    val message = findViewById<TextView>(R.id.dialog_message)
    message.messageJob()
}

inline fun View.negativeBtn(negativeJob: TextView.() -> Unit) {
    val negative = findViewById<TextView>(R.id.dialog_negative_btn_text)
    negative.visibility = View.VISIBLE
    negative.negativeJob()

    setDrawable(negative, {
        cornerRadius = DensityUtils.dp2px(10f).toFloat()
        color = ColorStateList.valueOf(context.getColor(R.color.design_default_color_primary))
        setStroke(
            DensityUtils.dp2px(1f),
            ColorStateList.valueOf(context.getColor(R.color.design_default_color_error))
        )
    }, ripple = true, rippleColor = context.getColor(R.color.color_f7c653))
}

inline fun View.positiveBtn(positiveJob: TextView.() -> Unit) {
    val positive = findViewById<TextView>(R.id.dialog_positive_btn_text)
    positive.positiveJob()

    setDrawable(positive, {
        cornerRadius = DensityUtils.dp2px(10f).toFloat()
        color = ColorStateList.valueOf(context.getColor(R.color.design_default_color_primary))
        setStroke(
            DensityUtils.dp2px(1f),
            ColorStateList.valueOf(context.getColor(R.color.design_default_color_error))
        )
    }, {
        cornerRadius = DensityUtils.dp2px(10f).toFloat()
        color = ColorStateList.valueOf(context.getColor(R.color.white))
    })
}
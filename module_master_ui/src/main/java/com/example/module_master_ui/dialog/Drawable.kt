package com.gaosi.lib_ui.utils

import android.R
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/3/9 16:39
 *
 **/

//自定义drawable
fun setDrawable(
    root: View,
    normalRender: GradientDrawable.() -> Unit,
    pressedRender: GradientDrawable.() -> Unit = {},
    ripple : Boolean = false,
    rippleColor : Int = 0
) {

    val normal = GradientDrawable()
    with(normal) {
        normalRender()
    }

    val press = GradientDrawable()
    with(press) {
        pressedRender()
    }

    if (ripple) {
        val rippleDrawable = RippleDrawable(
            ColorStateList(
                arrayOf(
                    intArrayOf(R.attr.state_pressed),
                    intArrayOf(R.attr.state_focused),
                    intArrayOf(R.attr.state_activated)
                ),
                intArrayOf(
                    rippleColor,
                    rippleColor,
                    rippleColor
                )
            ), normal, ShapeDrawable()
        )
        root.isClickable = true
        root.background = rippleDrawable
    } else {
        with(StateListDrawable()) {
            addState(
                intArrayOf(
                    android.R.attr.state_focused,
                    android.R.attr.state_pressed,
                    android.R.attr.state_enabled
                ), press
            )

            addState(
                intArrayOf(android.R.attr.state_pressed),
                press
            )
            addState(
                intArrayOf(android.R.attr.state_focused),
                press
            )
            addState(
                intArrayOf(android.R.attr.state_enabled),
                normal
            )
            addState(intArrayOf(), normal)

            root.isClickable = true
            root.background = this
        }
    }

}
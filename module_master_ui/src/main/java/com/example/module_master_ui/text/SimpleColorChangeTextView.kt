package com.example.module_master_ui.text

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

/**
 * @Author : gaosi
 * @Time : 2023/8/29 23:22
 * @Description:
 */
class SimpleColorChangeTextView : AppCompatTextView {

    private val mPaint = Paint()
    private val mText = "天天向上"
    private var percent: Float = 0f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var baseline = 100f
        mPaint.textSize = 80f
        canvas?.drawText(mText, 0f, baseline, mPaint)

        val x = width / 2
        //默认left
        canvas?.drawText(mText, x.toFloat(), baseline, mPaint)
        //设置文字居中对齐
        mPaint.textAlign = Paint.Align.CENTER
        canvas?.drawText(mText, x.toFloat(), baseline + 2 * paint.fontSpacing, mPaint)
        //right
        mPaint.textAlign = Paint.Align.RIGHT
        canvas?.drawText(mText, x.toFloat(), baseline + 4 * paint.fontSpacing, mPaint)

        drawCenterText(canvas)
        drawCenterText1(canvas)

        drawCenterLineX(canvas)

        drawCenterLineY(canvas)
    }

    private fun drawCenterText(canvas: Canvas?) {
        canvas?.save()
        //文字绘制到中心
        //文字高度计算
        val fontMetrics = mPaint.fontMetrics
        val measureText = mPaint.measureText(mText)

        mPaint.textAlign = Paint.Align.LEFT
        val baseline =
            height / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent

        val x = width / 2 - measureText / 2
        canvas?.drawText(mText, x, baseline, mPaint)
        canvas?.restore()
    }

    private fun drawCenterText1(canvas: Canvas?) {
        canvas?.save()
        mPaint.color = Color.RED
        //文字绘制到中心
        //文字高度计算
        val fontMetrics = mPaint.fontMetrics
        val measureText = mPaint.measureText(mText)

        mPaint.textAlign = Paint.Align.LEFT
        val baseline =
            height / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent

        val left = width / 2 - measureText / 2
        val right = left + measureText * percent
        Log.d("Simple", "left : $left , right : $right")
        val rect = Rect(left.toInt(), 0, right.toInt(), height)
        canvas?.clipRect(rect)
        canvas?.drawText(mText, left, baseline, mPaint)
        canvas?.restore()

    }

    private fun drawCenterLineX(canvas: Canvas?) {
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        mPaint.strokeWidth = 3f
        canvas?.drawLine((width / 2).toFloat(), 0f, (width / 2).toFloat(), height.toFloat(), mPaint)
    }

    private fun drawCenterLineY(canvas: Canvas?) {
        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.BLUE
        mPaint.strokeWidth = 3f
        canvas?.drawLine(
            0f,
            (height / 2).toFloat(),
            width.toFloat(),
            (height / 2).toFloat(),
            mPaint
        )
    }

    fun getPresent(): Float {
        return percent
    }

    fun setPercent(percent: Float) {
        this.percent = percent
        invalidate()
    }
}
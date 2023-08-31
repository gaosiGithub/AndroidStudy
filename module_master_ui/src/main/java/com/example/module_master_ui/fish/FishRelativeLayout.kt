package com.example.module_master_ui.fish

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlin.math.acos
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * @Author : gaosi
 * @Time : 2023/8/27 22:47
 * @Description:
 */
class FishRelativeLayout : RelativeLayout {

    private val mPaint: Paint = Paint()
    private lateinit var ivFish: ImageView
    private lateinit var fishDrawable: FishDrawable
    private var touchX: Float = 0f
    private var touchY: Float = 0f
    private var ripple: Float = 0f
    private var rippleAlpha: Int = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        //ViewGroup 默认 不执行 onDraw
        setWillNotDraw(false)

        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 8f

        ivFish = ImageView(context)
        val layoutParams = LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        )
        layoutParams.addRule(CENTER_IN_PARENT)
        fishDrawable = FishDrawable()

        ivFish.setImageDrawable(fishDrawable)
        addView(ivFish, layoutParams)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mPaint.alpha = rippleAlpha
        canvas?.drawCircle(touchX, touchY, ripple * 150, mPaint)

        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        touchX = event?.x!!
        touchY = event.y

        val objectAnimator = ObjectAnimator.ofFloat(this, "ripple", 0f, 1f).setDuration(1000)
        objectAnimator.start()

        makeTrail()
        return super.onTouchEvent(event)
    }

    private fun makeTrail() {
        //鱼的重心：相对ImageView坐标
        val fishRelativeMiddle = fishDrawable.getMiddlePoint()
        //鱼的重心 ：绝对坐标 --起始点
        val fishMiddle = PointF(ivFish.x + fishRelativeMiddle.x, ivFish.y + fishRelativeMiddle.y)
        //鱼头圆心的坐标 -- 控制点1
        val fishHead = PointF(
            ivFish.x + fishDrawable.getHeadPoint().x,
            ivFish.y + fishDrawable.getHeadPoint().y
        )
        //点击坐标 -- 结束点
        val touch = PointF(touchX, touchY)
        //控制点2的坐标
        val angle = includeAngel(fishMiddle, fishHead, touch)
        val delta = includeAngel(fishMiddle, PointF(fishMiddle.x + 1, fishMiddle.y), fishHead)
        val controlPoint =
            fishDrawable.calculatePoint(fishMiddle, FishDrawable.HEAD_RADIUS * 1.6f, angle + delta)

        val path = Path()
        path.moveTo(fishMiddle.x - fishRelativeMiddle.x, fishMiddle.y - fishRelativeMiddle.y)
        //三阶贝塞尔曲线
        path.cubicTo(
            fishHead.x - fishRelativeMiddle.x, fishHead.y - fishRelativeMiddle.y,
            controlPoint.x - fishRelativeMiddle.x, controlPoint.y - fishRelativeMiddle.y,
            touchX - fishRelativeMiddle.x, touchY - fishRelativeMiddle.y
        )

        val objectAnimator = ObjectAnimator.ofFloat(ivFish, "x", "y", path)
        objectAnimator.duration = 2000
        objectAnimator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)

                fishDrawable.frequence = 1f
            }

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)

                fishDrawable.frequence = 3f
            }
        })

        val pathMeasure = PathMeasure(path, false)
        val tan = FloatArray(2)
        objectAnimator.addUpdateListener {
            //执行了整个周期的百分之多少
            val fraction = it.animatedFraction
            pathMeasure.getPosTan(pathMeasure.length * fraction, null, tan)

            val angle = Math.toDegrees(atan2(-tan[1], tan[0]).toDouble())
            fishDrawable.fishMainAngle = angle
        }

        objectAnimator.start()

    }

    fun includeAngel(O: PointF, A: PointF, B: PointF): Double {
        //cosAOB
        //OA*OB=(Ax-Ox)
        val AOB: Float = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y)
        val OALength = sqrt(((A.x - O.x) * (A.x - O.x) + (A.y - O.y) * (A.y - O.y)).toDouble())
        val OBLength = sqrt(((B.x - O.x) * (B.x - O.x) + (B.y - O.y) * (B.y - O.y)).toDouble())
        val cosAOB = AOB / (OALength * OBLength)

        //反余弦
        val angleAOB = Math.toDegrees(acos(cosAOB))

        //AB连线与X的夹角的tan值 - OB与X轴的夹角的tan值
        val direction: Float = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) * (O.x - B.x)

        return if (direction == 0f) {
            if (AOB >= 0) {
                0.0
            } else {
                180.0
            }
        } else {
            if (direction > 0) {
                -angleAOB
            } else {
                angleAOB
            }
        }
    }

    fun getRipple(): Float {
        return ripple
    }

    fun setRipple(ripple: Float) {
        rippleAlpha = (100 * (1 - ripple)).toInt()
        this.ripple = ripple
    }
}
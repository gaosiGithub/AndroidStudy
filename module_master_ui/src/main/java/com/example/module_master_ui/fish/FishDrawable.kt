package com.example.module_master_ui.fish

import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.sin

/**
 * @Author : gaosi
 * @Time : 2023/8/25 23:01
 * @Description:
 */
class FishDrawable : Drawable() {

    companion object {
        const val OTHER_ALPHA = 110
        const val BODY_ALPHA = 160

        //鱼头半径
        const val HEAD_RADIUS: Float = 50f

        //寻找鱼鳍的起始点长度
        const val FIND_FINS_LENGTH: Float = 0.9f * HEAD_RADIUS

        //鱼鳍的长度
        const val FINS_LENGTH: Float = 1.3f * HEAD_RADIUS

        //鱼的长度值
        const val BODY_LENGTH: Float = HEAD_RADIUS * 3.2f

        //大圆的半径
        const val BIG_CIRCLE_RADIUS: Float = 0.7f * HEAD_RADIUS
        const val MIDDLE_CIRCLE_RADIUS: Float = 0.6f * BIG_CIRCLE_RADIUS
        const val SMALL_CIRCLE_RADIUS: Float = 0.4f * MIDDLE_CIRCLE_RADIUS

        //寻找尾部中圆圆心的线长
        const val FIND_MIDDLE_CIRCLE_LENGTH: Float = BIG_CIRCLE_RADIUS * (0.6f + 1)

        //寻找尾部小圆圆心的线长
        const val FIND_SMALL_CIRCLE_LENGTH: Float = MIDDLE_CIRCLE_RADIUS * (0.4f + 2.7f)

        //寻找大三角形底边中心点的线长
        const val FIND_TRIANGLE_LENGTH: Float = MIDDLE_CIRCLE_RADIUS * 2.7f
    }

    private var mPath: Path = Path()

    private var mPaint: Paint = Paint()

    //鱼的重心
    private val middlePoint: PointF = PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS)

    //鱼的主要朝向角度，与水平位置的夹角
    var fishMainAngle: Double = 90.0

    private var currentValue: Float = 0f

    private lateinit var headPoint: PointF

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        mPaint.setARGB(OTHER_ALPHA, 244, 92, 71)

        val valueAnimator = ValueAnimator.ofFloat(0f, 720f)
        valueAnimator.duration = 2000
        valueAnimator.repeatMode = ValueAnimator.RESTART
        valueAnimator.repeatCount = ValueAnimator.INFINITE
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener {
            currentValue = it.animatedValue as Float

            invalidateSelf()
        }
        valueAnimator.start()
    }

    var frequence: Float = 1f

    override fun draw(canvas: Canvas) {
        val fishAngle = fishMainAngle + sin(Math.toRadians(currentValue.toDouble() * frequence)) * 10
        //鱼头的圆心坐标
        headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, fishAngle)
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint)

        //右鱼鳍
        val rightFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle - 110)
        makeFins(canvas, rightFinsPoint, fishAngle)

        //左鱼鳍
        val leftFinsPoint = calculatePoint(headPoint, FIND_FINS_LENGTH, fishAngle + 110)
        makeFins(canvas, leftFinsPoint, fishAngle, false)

        val bodyBottomCenterPoint = calculatePoint(headPoint, BODY_LENGTH, fishAngle - 180)
        //画节肢1
        val middleCenterPoint = makeSegment(
                canvas,
                bodyBottomCenterPoint,
                BIG_CIRCLE_RADIUS,
                MIDDLE_CIRCLE_RADIUS,
                FIND_MIDDLE_CIRCLE_LENGTH,
                fishAngle,
                true
        )

        //画节肢2
        makeSegment(
                canvas,
                middleCenterPoint,
                MIDDLE_CIRCLE_RADIUS,
                SMALL_CIRCLE_RADIUS,
                FIND_SMALL_CIRCLE_LENGTH,
                fishAngle,
                false
        )

        //尾巴
        makeTriangle(canvas, middleCenterPoint, FIND_TRIANGLE_LENGTH, BIG_CIRCLE_RADIUS, fishAngle)
        makeTriangle(
                canvas,
                middleCenterPoint,
                FIND_TRIANGLE_LENGTH - 10,
                BIG_CIRCLE_RADIUS - 20,
                fishAngle
        )

        //身体
        makeBody(canvas, headPoint, bodyBottomCenterPoint, fishAngle)
    }

    private fun makeBody(
            canvas: Canvas,
            headPoint: PointF,
            bodyBottomCenterPoint: PointF,
            fishAngle: Double
    ) {
        //身体的四个点求出来
        val topLeftPoint: PointF = calculatePoint(headPoint, HEAD_RADIUS, fishAngle + 90)
        val topRightPoint: PointF = calculatePoint(headPoint, HEAD_RADIUS, fishAngle - 90)
        val bottomLeftPoint: PointF =
                calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle + 90)
        val bottomRightPoint: PointF =
                calculatePoint(bodyBottomCenterPoint, BIG_CIRCLE_RADIUS, fishAngle - 90)
        //二阶贝塞尔曲线控制点 -- 决定鱼的胖瘦
        val controlLeft = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle + 130)
        val controlRight = calculatePoint(headPoint, BODY_LENGTH * 0.56f, fishAngle - 130)

        mPath.reset()
        mPath.moveTo(topLeftPoint.x, topLeftPoint.y)
        mPath.quadTo(controlLeft.x, controlLeft.y, bottomLeftPoint.x, bottomLeftPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.quadTo(controlRight.x, controlRight.y, topRightPoint.x, topRightPoint.y)
        mPaint.alpha = BODY_ALPHA
        canvas.drawPath(mPath, mPaint)
    }

    private fun makeTriangle(
            canvas: Canvas,
            startPoint: PointF,
            findCenterLength: Float,
            findEdgeLength: Float,
            fishAngle: Double
    ) {

        val triangleAngle = fishAngle + sin(Math.toRadians(currentValue.toDouble() * 1.5 * frequence)) * 35

        //三角形底边的中心坐标
        val centerPoint: PointF = calculatePoint(startPoint, findCenterLength, triangleAngle - 180)
        //三角形底边两点
        val leftPoint: PointF = calculatePoint(centerPoint, findEdgeLength, triangleAngle + 90)
        val rightPoint: PointF = calculatePoint(centerPoint, findEdgeLength, triangleAngle - 90)
        mPath.reset()
        mPath.moveTo(startPoint.x, startPoint.y)
        mPath.lineTo(leftPoint.x, leftPoint.y)
        mPath.lineTo(rightPoint.x, rightPoint.y)
        canvas.drawPath(mPath, mPaint)
    }

    private fun makeSegment(
            canvas: Canvas,
            bottomCenterPoint: PointF,
            bigRadius: Float,
            smallRadius: Float,
            findSmallCircleLength: Float,
            fishAngle: Double,
            hasBigCircle: Boolean
    ): PointF {

        var segmentAngle = 0.0
        if (hasBigCircle) {
            //节肢1
            segmentAngle = fishAngle + cos(Math.toRadians(currentValue.toDouble() * 1.5) * frequence) * 15
        } else {
            //节肢2
            segmentAngle = fishAngle + sin(Math.toRadians(currentValue.toDouble() * 1.5 * frequence)) * 35
        }

        //梯形上底圆的圆心
        val upperCenterPoint =
                calculatePoint(bottomCenterPoint, findSmallCircleLength, segmentAngle - 180)

        //梯形的四个点
        val bottomLeftPoint = calculatePoint(bottomCenterPoint, bigRadius, segmentAngle + 90)
        val bottomRightPoint = calculatePoint(bottomCenterPoint, bigRadius, segmentAngle - 90)
        val upLeftPoint = calculatePoint(upperCenterPoint, smallRadius, segmentAngle + 90)
        val upRightPoint = calculatePoint(upperCenterPoint, smallRadius, segmentAngle - 90)

        if (hasBigCircle) {
            //画大圆---只在节肢1 上才绘画
            canvas.drawCircle(bottomCenterPoint.x, bottomCenterPoint.y, bigRadius, mPaint)
        }

        //画小圆
        canvas.drawCircle(upperCenterPoint.x, upperCenterPoint.y, smallRadius, mPaint)

        //画梯形
        mPath.reset()
        mPath.moveTo(upLeftPoint.x, upLeftPoint.y)
        mPath.lineTo(upRightPoint.x, upRightPoint.y)
        mPath.lineTo(bottomRightPoint.x, bottomRightPoint.y)
        mPath.lineTo(bottomLeftPoint.x, bottomLeftPoint.y)
        canvas.drawPath(mPath, mPaint)

        return upperCenterPoint
    }

    private fun makeFins(
            canvas: Canvas,
            startPoint: PointF,
            fishAngle: Double,
            isRight: Boolean = true
    ) {
        val controlAngle: Double = 115.0

        //鱼鳍的终点 --- 二阶贝塞尔曲线的终点
        val endPoint: PointF = calculatePoint(
                startPoint,
                FINS_LENGTH,
                if (isRight) fishAngle - 180 else fishAngle + 180
        )
        //控制点
        val controlPoint: PointF =
                calculatePoint(
                        startPoint,
                        FINS_LENGTH * 1.8f,
                        if (isRight) fishAngle - controlAngle else fishAngle + controlAngle
                )

        //绘制
        mPath.reset()
        mPath.moveTo(startPoint.x, startPoint.y)
        mPath.quadTo(controlPoint.x, controlPoint.y, endPoint.x, endPoint.y)
        canvas.drawPath(mPath, mPaint)
    }

    fun calculatePoint(startPoint: PointF, length: Float, angle: Double): PointF {
        val deltaX: Float = (cos(Math.toRadians(angle)) * length).toFloat()
        val deltaY: Float = (sin(Math.toRadians(angle - 180)) * length).toFloat()

        return PointF(startPoint.x + deltaX, startPoint.y + deltaY)
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38f * HEAD_RADIUS).toInt()
    }

    fun getMiddlePoint(): PointF {
        return middlePoint
    }

    fun getHeadPoint(): PointF {
        return headPoint
    }
}
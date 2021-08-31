package com.ray.bezierlayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import android.graphics.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.graphics.plus
import com.example.animations.R


class BezierLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private  val TAG = "BezierLayout"

    private var strokeColor: Int = Color.RED

    private var strokeWidth: Int = 4

    private var pathBackgroundColor: Int = Color.YELLOW

    private var paintAxis: Int = 1

    private val PAINT_AXIS_TOP_TO_BOTTOM = 1

    private val PAINT_AXIS_BOTTOM_TO_TOP = 2

    private val PAINT_AXIS_RIGHT_TO_LEFT = 3

    private val PAINT_AXIS_LEFT_TO_RIGHT = 4

    private val STROKE_CAP_ROUND = 1

    private val STROKE_CAP_BUTT = 2

    private val STROKE_CAP_SQUARE = 3


    private var strokeCap = STROKE_CAP_ROUND

    override fun onViewAdded(view: View?) {
        super.onViewAdded(view)
    }

    fun getPoints(): ArrayList<PointF> {
        val res = ArrayList<PointF>()


        for (i in 0 until childCount){
            res.add(PointF(0f,0f))
        }


        for (i in 0 until  childCount){
            val child = getChildAt(i) as AnchorView
            res[child.order] = child.anchor
        }

        return res
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        Log.e(TAG, "dispatchDraw: called" )

        val curvePath = Path()
        val backgroundPath = Path()
        val points = getPoints()

        val offset = PointF(left.toFloat(), top.toFloat())

        points.map {
            it + offset
        }


        when(paintAxis){

            PAINT_AXIS_TOP_TO_BOTTOM -> {
                backgroundPath.moveTo(points[0].x, top.toFloat() + height.toFloat())
            }

            PAINT_AXIS_BOTTOM_TO_TOP -> {
                backgroundPath.moveTo(points[0].x, top.toFloat())
            }

            PAINT_AXIS_LEFT_TO_RIGHT -> {
                backgroundPath.moveTo(left.toFloat(), points[0].y)
            }

            PAINT_AXIS_RIGHT_TO_LEFT -> {
                backgroundPath.moveTo(left.toFloat() + width.toFloat(), points[0].y)
            }
        }


        backgroundPath.lineTo(points[0].x, points[0].y)

        curvePath.moveTo(points[0].x, points[0].y)

        var lastPoint = points[0]

        for (i in 1 until points.size step 2) {
            curvePath.cubicTo(lastPoint.x, lastPoint.y, points[i].x, points[i].y, points[i+1].x, points[i + 1].y)
            backgroundPath.cubicTo(lastPoint.x, lastPoint.y, points[i].x, points[i].y, points[i+1].x, points[i + 1].y)
            lastPoint = points[i+1]
        }


        when(paintAxis){
            PAINT_AXIS_TOP_TO_BOTTOM -> {
                backgroundPath.lineTo(lastPoint.x, top.toFloat() + height.toFloat())
            }

            PAINT_AXIS_BOTTOM_TO_TOP -> {
                backgroundPath.lineTo(lastPoint.x, top.toFloat())
            }

            PAINT_AXIS_LEFT_TO_RIGHT -> {
                backgroundPath.lineTo(left.toFloat(), lastPoint.y)
            }

            PAINT_AXIS_RIGHT_TO_LEFT -> {
                backgroundPath.lineTo(left.toFloat() + width.toFloat(), lastPoint.y)
            }
        }

        val backgroundPaint = Paint()
        backgroundPaint.color = pathBackgroundColor
        canvas!!.drawPath(backgroundPath, backgroundPaint)

        val curvePaint =  Paint(Paint.ANTI_ALIAS_FLAG);
        curvePaint.style = Paint.Style.STROKE;
        curvePaint.strokeWidth = strokeWidth.toFloat()
        curvePaint.color = strokeColor
        curvePaint.strokeCap = getStroke()
        canvas!!.drawPath(curvePath, curvePaint)

        //curvePath.lineTo(left.toFloat() + width.toFloat(), top.toFloat() + height.toFloat())


    }

    fun getStroke(): Paint.Cap {
        return when(strokeCap){
            STROKE_CAP_BUTT -> Paint.Cap.BUTT
            STROKE_CAP_ROUND -> Paint.Cap.ROUND
            STROKE_CAP_SQUARE -> Paint.Cap.SQUARE
            else -> {
                Paint.Cap.ROUND
            }
        }

    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BezierLayout)
        strokeColor = typedArray.getColor(R.styleable.BezierLayout_strokeColor, Color.RED)
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.BezierLayout_strokeWidth, 10)
        pathBackgroundColor = typedArray.getColor(R.styleable.BezierLayout_path_background, Color.BLACK)
        paintAxis = typedArray.getInt(R.styleable.BezierLayout_paint_axis, PAINT_AXIS_TOP_TO_BOTTOM)
        strokeCap = typedArray.getInt(R.styleable.BezierLayout_strokeCap, STROKE_CAP_ROUND)
        typedArray.recycle()
    }





    override fun drawChild(canvas: Canvas?, child: View?, drawingTime: Long): Boolean {
        return super.drawChild(canvas, child, drawingTime)
    }


}
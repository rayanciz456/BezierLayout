package com.ray.bezierlayout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.example.animations.R

@SuppressLint("UseCompatLoadingForDrawables", "Recycle")
class AnchorView(context: Context, attrs: AttributeSet): AppCompatImageView(context, attrs) {

    var visibleOnScreen: Boolean = true

    var anchorFlag: Int = 0

    val ANCHOR_TOP = 1
    val ANCHOR_BOTTOM = 2
    val ANCHOR_LEFT = 4
    val ANCHOR_RIGHT = 8
    val ANCHOR_CENTER_VERTICAL = 16
    val ANCHOR_CENTER_HORIZONTAL = 32
    val ANCHOR_CENTER = 64


    var order: Int = 0



    init {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnchorView)
        visibleOnScreen = typedArray.getBoolean(R.styleable.AnchorView_visible_on_screen, true)
        anchorFlag = typedArray.getInt(R.styleable.AnchorView_anchor_position, 0)
        order = typedArray.getInt(R.styleable.AnchorView_order, 0)

        if (order == 0) {
            Log.e("AnchorView", ": " + anchorFlag )
        }

        typedArray.recycle()

        if (visibleOnScreen){
            background = context.getDrawable(R.drawable.circle)
        }
    }


    val anchor: PointF
        get () {

            val leftF = left.toFloat()
            val topF = top.toFloat()
            val widthF = width.toFloat()
            val heightF = height.toFloat();

           printIF0("left is " + ANCHOR_LEFT)

            printIF0("bottom is " + ANCHOR_BOTTOM)

            printIF0("anchor is " + anchorFlag)

            if ((anchorFlag and ANCHOR_CENTER) == ANCHOR_CENTER)
            {
                printIF0("63 CENTER")
                return PointF(leftF + widthF / 2, topF + heightF / 2)
            }

            if (anchorFlag and ANCHOR_TOP == ANCHOR_TOP){
                printIF0("68 TOP")
                if (anchorFlag and ANCHOR_LEFT == ANCHOR_LEFT)
                    return PointF(leftF, topF)

                if (anchorFlag and ANCHOR_RIGHT == ANCHOR_RIGHT)
                    return PointF(leftF + widthF, topF)

                if (anchorFlag and ANCHOR_CENTER_HORIZONTAL == ANCHOR_CENTER_HORIZONTAL){
                    return PointF(leftF + widthF / 2, topF)
                }

            }

            if (anchorFlag and ANCHOR_LEFT == ANCHOR_LEFT) {
                printIF0("79  left")
                if (anchorFlag and ANCHOR_CENTER_VERTICAL == ANCHOR_CENTER_VERTICAL)
                    return PointF(leftF, topF +  heightF / 2)

                if (anchorFlag and ANCHOR_BOTTOM == ANCHOR_BOTTOM)
                {

                    printIF0("86  Bottom and left")
                    return PointF(leftF, topF +  heightF)
                }

            }

            if (anchorFlag and ANCHOR_RIGHT == ANCHOR_RIGHT) {

                if (anchorFlag and ANCHOR_CENTER_VERTICAL == ANCHOR_CENTER_VERTICAL)
                    return PointF(leftF + widthF, topF +  heightF / 2)

                if (anchorFlag and ANCHOR_BOTTOM == ANCHOR_BOTTOM)
                    return PointF(leftF + widthF, topF +  heightF)

            }

            if ( (anchorFlag and bottom == bottom) && (anchorFlag and ANCHOR_CENTER_HORIZONTAL == ANCHOR_CENTER_HORIZONTAL)){
                return PointF(leftF + widthF / 2, topF + heightF)
            }

            return  PointF(leftF, topF)
        }

    fun printIF0(message: String){
        if (order == 0)
            Log.e("AnchorView", "printIF0: " + message )
    }
}
package com.ray.bezierlayout

import android.animation.ValueAnimator
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.animations.R

class MainActivity : AppCompatActivity() {

    private lateinit var rocketAnimation: AnimationDrawable

    private lateinit var imageView: ImageView

    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val middle = findViewById<AnchorView>(R.id.anchor_middle)

        ValueAnimator.ofFloat(0f, 100f).apply {
            duration = 4000
            addUpdateListener {
                Log.e("Calling", "onCreate: ", )
                middle.translationX = it.animatedValue as Float
            }
            start()
        }

       // linearLayout = findViewById(R.id.linearLayout)

//        imageView = findViewById<ImageView>(R.id.image_view).apply {
//            setBackgroundResource(R.drawable.drawable_animatopm)
//            rocketAnimation = background as AnimationDrawable
//        }
//
//        imageView.setOnClickListener({ rocketAnimation.start() })


//        Handler().postDelayed({
//            val iv = ImageView(this)
//            val lp = LinearLayout.LayoutParams(240, 240)
//            iv.setBackgroundColor(Color.RED)
//            lp.topMargin = 10
//            lp.bottomMargin = 10
//            lp.leftMargin = 10
//            lp.rightMargin = 10
//
//            iv.layoutParams = lp
//
//            linearLayout.addView(iv, 0)
//
//        }, 2000)

    }



    override fun onStart() {

//        ValueAnimator.ofObject(FloatEvaluator(),0f , 100f).apply {
//            duration = 1_000
//
//            addUpdateListener {
//                imageView.translationX = it.animatedValue as Float
//            }
//
//            start()
//        }
//
        super.onStart()
    }


}
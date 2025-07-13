package ru.netology.statsview.ui

import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.netology.statsview.R


class MainActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = findViewById<StatsView>(R.id.statsView)
        view.data = listOf(
            500F,
            500F,
            500F,
//            500F,
        )

        val text = findViewById<TextView>(R.id.label)
        text.textSize = 25F
        text.setPadding(0,32,0,0)
        text.setTypeface(null, Typeface.NORMAL)

        view.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.animation).apply {

                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        text.text = "START"
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        text.text = "END"
                    }

                    override fun onAnimationRepeat(animation: Animation?) {}

                })
            }
        )


    }

}

package ru.netology.statsview.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Typeface
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.netology.statsview.R
import ru.netology.statsview.ui.StatsView.CallbackRepeatState.END
import ru.netology.statsview.ui.StatsView.CallbackRepeatState.REPEAT
import ru.netology.statsview.ui.StatsView.CallbackRepeatState.START


class MainActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = findViewById<StatsView>(R.id.statsView)

        val text = findViewById<TextView>(R.id.label)
        text.textSize = 25F
        text.setPadding(0, 32, 0, 0)
        text.setTypeface(null, Typeface.NORMAL)

        var count = 0

        view.callbackAnimator = { state ->
            when (state) {
                START -> text.text = getString(R.string.word_start)
                REPEAT -> {
                    count++
                    text.text = getString(R.string.empty, count)
                }
                END -> text.text = getString(R.string.word_end)
            }
        }

        view.data = listOf(
            500F,
            500F,
            500F,
            500F,
        )


        view.animate()
            .setStartDelay(5200)
            .setDuration(300)
            .rotation(-360F)
            .setInterpolator(AccelerateInterpolator())
            .withEndAction {

                val scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.85f, 1.15f, 1f).apply {
                    duration = 250
                    interpolator = LinearInterpolator()
                }
                val scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.85f, 1.15f, 1f).apply {
                    duration = 250
                    interpolator = LinearInterpolator()
                }
                val rotation =
                    ObjectAnimator.ofFloat(view, "rotation", 0f, -6f, 6f, -4f, 4f, 0f).apply {
                        duration = 250
                        interpolator = LinearInterpolator()
                    }
                val shake =
                    ObjectAnimator.ofFloat(view, "translationX", 0f, -6f, 6f, -4f, 4f, 0f).apply {
                        duration = 250
                        interpolator = LinearInterpolator()
                    }

                val alphaOn = ObjectAnimator.ofFloat(view, "alpha", 0.2F, 1F).apply {
                    startDelay = 100
                    duration = 350
                    interpolator = BounceInterpolator()
                }

                AnimatorSet().apply {
                    playTogether(scaleX, scaleY, rotation, shake, alphaOn)
                    start()
                }
            }
            .start()

    }

}


//        view.animate()
//            .rotation(360F)
//            .setInterpolator(LinearInterpolator())
//            .setStartDelay(300)
//            .setDuration(1700)
//                        .setListener(object : Animator.AnimatorListener {
//                override fun onAnimationStart(animation: Animator) {
//                    text.text = "START"
//                }
//
//                override fun onAnimationEnd(animation: Animator) {
//                    text.text = "END"
//                }
//
//                override fun onAnimationCancel(animation: Animator) {}
//                override fun onAnimationRepeat(animation: Animator) {}
//
//            })
//            .withStartAction { // укороченный вариант верхнего
//                text.text = getString(R.string.word_start)
//            }.withEndAction { // -//-
//                text.text = getString(R.string.word_end)
//            }
//            .start()


// Пример 1 android.view.animation
//        val label = findViewById<TextView>(R.id.label)
//        val viewAnim = AnimationUtils.loadAnimation(
//            this, R.anim.view_animation
//        ).apply {
//            setAnimationListener(object : Animation.AnimationListener {
//                override fun onAnimationStart(animation: Animation?) {
//                    label.text = "started"
//                }
//
//                override fun onAnimationEnd(animation: Animation?) {
//                    label.text = "ended"
//                }
//
//                override fun onAnimationRepeat(animation: Animation?) {
//                    label.text = "repeat"
//                }
//
//            })
//        }
//
//        view.startAnimation(viewAnim)
// Конец примера 1

// Пример 2 ObjectAnimator через рефлексию
//        ObjectAnimator.ofFloat(view, "alpha", 0.25F, 1F).apply {
//            startDelay = 500
//            duration = 300
//            interpolator = BounceInterpolator()
//        }.start()
// Конец примера 2

// Пример 3 ObjectAnimator через готовые property
//        ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
//            startDelay = 500
//            duration = 300
//            interpolator = BounceInterpolator()
//        }.start()
// Конец примера 3

// Пример 4 Анимация нескольких свойств через PropertyValuesHolder
//        val rotation = PropertyValuesHolder.ofFloat(View.ROTATION, 0F, 360F)
//        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0F, 1F)
//        ObjectAnimator.ofPropertyValuesHolder(view, rotation, alpha)
//            .apply {
//                startDelay = 500
//                duration = 500
//                interpolator = LinearInterpolator()
//            }.start()
// Конец примера 4

// Пример 5 Использование ViewPropertyAnimator
//        view.animate()
//            .rotation(360F)
//            .scaleX(1.2F)
//            .scaleY(1.2F)
//            .setInterpolator(LinearInterpolator())
//            .setStartDelay(500)
//            .setDuration(500)
//            .start()
// Конец примера 5

// Пример 6 Комбинация нескольких анимаций через AnimatorSet
//        val alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0.25F, 1F).apply {
//            duration = 300
//            interpolator = LinearInterpolator()
//        }
//        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0F, 1F)
//        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0F, 1F)
//        val scale = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY).apply {
//            duration = 300
//            interpolator = BounceInterpolator()
//        }
//        AnimatorSet().apply {
//            startDelay = 500
//            playSequentially(scale, alpha)
//        }.start()
// Конец примера 6
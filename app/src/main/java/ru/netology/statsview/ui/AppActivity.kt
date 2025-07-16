package ru.netology.statsview.ui

import android.graphics.Typeface
import android.os.Bundle
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
            500F,
        )

        val text = findViewById<TextView>(R.id.label)
        text.textSize = 25F
        text.setPadding(0, 32, 0, 0)
        text.setTypeface(null, Typeface.NORMAL)


        view.animate().setDuration(2000)
            .withStartAction { // короткий вариант👆
                text.text = getString(R.string.word_start)
            }.withEndAction { // -//-
                text.text = getString(R.string.word_end)
            }
            .start()

    }

}






//        view.animate()
//            .rotation(360F)
//            .setInterpolator(LinearInterpolator())
//            .setStartDelay(300)
//            .setDuration(1700)
////                        .setListener(object : Animator.AnimatorListener {
////                override fun onAnimationStart(animation: Animator) {
////                    text.text = "START"
////                }
////
////                override fun onAnimationEnd(animation: Animator) {
////                    text.text = "END"
////                }
////
////                override fun onAnimationCancel(animation: Animator) {}
////                override fun onAnimationRepeat(animation: Animator) {}
////
////            })
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
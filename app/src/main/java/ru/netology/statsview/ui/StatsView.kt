package ru.netology.statsview.ui

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.withStyledAttributes
import ru.netology.statsview.R
import ru.netology.statsview.util.AndroidUtils
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
) : View(
    context,
    attributeSet,
    defStyleAttr,
    defStyleRes,
) {

    private var textSize = AndroidUtils.dp(context, 20F).toFloat()
    private var lineWidth = AndroidUtils.dp(context, 5F)
    private var colors = emptyList<Int>()

    private var progress = 0F
    //    private var progressOfRotate = 0
    private var valueAnimator: ValueAnimator? = null
    //    private var valueAnimatorRotate: ValueAnimator? = null
    private var endOfAnimation = 0

    sealed class CallbackRepeatState {
        object START : CallbackRepeatState()
        object END : CallbackRepeatState()
        object REPEAT : CallbackRepeatState()
    }

    var callbackAnimator: ((CallbackRepeatState) -> Unit)? = null

    init {
        context.withStyledAttributes(attributeSet, R.styleable.StatsView) {

            textSize = getDimension(R.styleable.StatsView_textSize, textSize)
            lineWidth = getDimension(R.styleable.StatsView_lineWidth, lineWidth.toFloat()).toInt()

            colors = listOf(
                getColor(R.styleable.StatsView_color1, generateRandomColor()),
                getColor(R.styleable.StatsView_color2, generateRandomColor()),
                getColor(R.styleable.StatsView_color3, generateRandomColor()),
                getColor(R.styleable.StatsView_color4, generateRandomColor()),
                getColor(R.styleable.StatsView_color5, generateRandomColor()),
            )
        }

    }

    var data: List<Float> = emptyList()
        set(value) {
            field = value
            update()
        }

    private var radius = 0F
    private var center = PointF() // - Простой класс для получения точки по двум координатам?
    // Доступ к контексту можно получить из любого места View.

    private var oval = RectF()
    private var fullIndicator = 2000F // полностью заполненный (условный) индикатор

    //----------------------------------------- Paint ----------------------------------------------

    private val paint = Paint( // Попробуем нарисовать круг
        Paint.ANTI_ALIAS_FLAG // - Флаг отвечающий за сглаживание (так круг будет угловатым)
    ).apply {
        strokeWidth = lineWidth.toFloat() // Настойка -Paint-
        style = Paint.Style.STROKE // -/-
        strokeJoin = Paint.Join.ROUND // Округление краёв при отрисовке
        strokeCap = Paint.Cap.ROUND // -/-
        // Будем округлять концы линий и при пересечении
    }

    private val textPaint = Paint(
        Paint.ANTI_ALIAS_FLAG
    ).apply {
        textSize = this@StatsView.textSize
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
    }

    //----------------------------------------------------------------------------------------------

    // Нужно переопределить несколько методов:

    // 1) Метод для определения размеров View:
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius =
            min(w, h) / 2F - lineWidth //⬅️ Чтобы поместилась отрисовка, делается небольшой отступ
        center = PointF(w / 2F, h / 2F)
        oval = RectF(
            center.x - radius,
            center.y - radius,
            center.x + radius,
            center.y + radius
        )
    }

    // 2) Метод для отрисовки:
    override fun onDraw(canvas: Canvas) {
        // -canvas- представляет собой "холст" на котором можно рисовать (имеет множество методов для рисования)
        if (data.isEmpty()) {
            return
        }
        var startAngle = -90F

        val totalProgress = 360F * progress

//        val dataLastPath = data.size

        data.forEachIndexed { index, item -> // отдаёт элемент и его индекс
            val pathOfData = item / fullIndicator // Какая это часть от общего индикатора

            val angle = pathOfData * 360 // Сколько эта часть будет в градусах

            val progressItem = (totalProgress - (startAngle + 90F))
                .coerceIn(0F, angle)

            paint.color =
                colors.getOrElse(index) { generateRandomColor() } // Для каждого итема свой цвет

            if (progressItem >= angle) canvas.drawArc(oval, startAngle, angle, false, paint)

//            if (index == dataLastPath - 1) endOfAnimation = 1

            startAngle += angle
        }

        canvas.drawText(
            "%.0f%%".format(progress * 100), // "2f" оставляет два знака после запятой,
            // спецификатор %% вставляет %.
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint
        )

        if (totalProgress >= 360F) {
            paint.color = colors.first()
            canvas.drawPoint(center.x + 5F, center.y - radius, paint) // решение с точкой
            endOfAnimation = 0
        }
    }


    private fun update() {

        valueAnimator?.let {
            it.removeAllListeners() // Удаляет всех "слушателей" и pauseListeners из этого объекта.
            it.cancel() // Отменяет анимацию
        }
        progress = 0F
        valueAnimator = ValueAnimator.ofFloat(0F, 1F).apply {// Создаёт и возвращает
            // объект ValueAnimator, который выполняет анимацию между значениями с плавающей точкой.
            addUpdateListener { anim -> // Добавляет "слушатель" в набор "слушателей", которым
                // отправляются события обновления на протяжении всего цикла анимации.
                progress = anim.animatedValue as Float // Последнее значение, вычисленное этим
                // ValueAnimator для одного анимируемого свойства.
                invalidate() // Вызовет onDraw. Перерисовка при установке значения?
                // Каждый раз когда изменяются значения реагирует лисенер и вызовется invalidate()
            }
            duration = 5000
//            repeatCount = 2
            interpolator = LinearInterpolator()

            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    callbackAnimator?.let { it(CallbackRepeatState.START) }
                }
                override fun onAnimationEnd(animation: Animator) {
                    callbackAnimator?.let { it(CallbackRepeatState.END) }
                }
                override fun onAnimationRepeat(animation: Animator) {
                    callbackAnimator?.let { it(CallbackRepeatState.REPEAT) }
                }
                override fun onAnimationCancel(animation: Animator) {}

            })

        }.also {
            it.start()
        }

//----------------------------------------------------------------------

//        valueAnimatorRotate?.let {
//            it.removeAllListeners()
//            it.cancel()
//        }
//
//        progressOfRotate = 0
//        valueAnimatorRotate = ValueAnimator.ofInt(0, 60).apply {
//
//            addUpdateListener { anim ->
//                val lastValue = anim.animatedValue as Int
//                if (lastValue == 60) endOfAnimation = 1
//                progressOfRotate = lastValue * 6
//                invalidate()
//            }
//
//            duration = 2000
////            repeatCount = 3
//            interpolator = LinearInterpolator()
//
//        }.also {
//            it.start()
//        }


    }

    private fun generateRandomColor() =
        Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())

}
package ru.netology.statsview.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
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
            invalidate() // Вызовет onDraw. Перерисовка при установке значения?
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

    private val textPaint = Paint( // Попробуем нарисовать круг
        Paint.ANTI_ALIAS_FLAG // - Флаг отвечающий за сглаживание (так круг будет угловатым)
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
        val dataSum = data.sum()
        val dataCount = data.count()

        println("Количество частей: $dataCount")

        paint.color = colors[4]
        canvas.drawCircle(center.x, center.y, radius, paint)

        data.forEachIndexed { index, item -> // отдаёт элемент и его индекс
            val pathOfData = item / fullIndicator
            val angle = pathOfData * 360
            paint.color = colors.getOrElse(index) { generateRandomColor() }

            if (index != 3) {
                canvas.drawArc(oval, startAngle, angle, false, paint)
            } else {
                canvas.drawArc(oval, startAngle, angle, false, paint)
                paint.color = colors.first()
                canvas.drawPoint(center.x + 5F, center.y - radius, paint) // решение с точкой
            }

            startAngle += angle
        }

        canvas.drawText(
            "%.2f%%".format(dataSum / fullIndicator * 100.00), // "2f" оставляет два знака после запятой,
            // спецификатор %% вставляет %.
            center.x,
            center.y + textPaint.textSize / 4,
            textPaint
        )

    }

    private fun generateRandomColor() = Random.Default.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())

}
package ru.netology.statsview.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import ru.netology.statsview.R


class MainActivity : ComponentActivity(R.layout.activity_app) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<StatsView>(R.id.statsView).data = listOf(
            500F,
            500F,
            500F,
//            500F,
        )
    }


}

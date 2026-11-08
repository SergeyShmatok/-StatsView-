## StatsView - мини-проект по созданию кастомных View.

**Цель:** изучение API отрисовки Android (Canvas, Paint) и создание элементов интерфейса — круговых диаграмм с анимацией.

**Что реализовано:**
- Кастомная View для отображения круговой диаграммы
- Расчёт долей и процентов на основе переданных данных (SmartStatsView)
- Корректное отображение точек-разделителей между секторами (Dot)
- Анимация поворота на 360 градусов при отрисовке (Rotation)

<!-- <p align="center">
  <img src="Screen-video/1.png" width="250" alt="Демонстрация работы StatsView">
</p> -->
<p align="left">
  <img src="Screen-video/2_GIF.gif" width="700" alt="Демонстрация работы StatsView">
</p>


<details>
  <summary>Вариации</summary>
  <p align="left">
    
  </p>
<img src="Screen-video/3_GIF.gif" width="700" alt="Демонстрация работы StatsView">
<p align="left">
  <em>Постепенное заполнение по часовой стрелке.</em>
</p>

  
<img src="Screen-video/5_GIF.gif" width="700" alt="Демонстрация работы StatsView">
<p align="left">
  <em>Заполнение секторами: 25% → 50% → 75% → 100%.
  </em>
</p>

</details>

Смотреть [скрины](Screen-video)

**Стек:** Kotlin, Android SDK (Canvas, Paint, View).

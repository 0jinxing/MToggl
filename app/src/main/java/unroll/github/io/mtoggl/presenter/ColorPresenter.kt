package unroll.github.io.mtoggl.presenter

import java.util.*

object ColorPresenter {
    private val colors =
            arrayListOf<String>("#f17c67", "#495A80", "#FD5B78", "#008573", "#ACE1AF", "#228fbd", "#C7FFEC", "#FFA07B", "#87843b", "#fdb933", "#70a19f", "#00EBC0", "#20F856", "#BF0A10", "#6f599c", "#ca8687", "#70a19f")
    private val random = Random()
    fun getRandomColor(): String {
        return colors[Math.abs(random.nextInt(colors.size)) % colors.size]
    }
}
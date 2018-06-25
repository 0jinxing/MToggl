package unroll.github.io.mtoggl.task

import android.os.AsyncTask
import android.support.v7.widget.Toolbar

class TicketTask : AsyncTask<Void, Long, Boolean> {
    private var toolbar: Toolbar? = null
    var ticket: Long = 0
    private var completeFlag = false

    constructor() {}
    constructor(toolbar: Toolbar, ticket: Long = 0, isReckon: Boolean = false) {
        this.toolbar = toolbar
        this.ticket = ticket
        this.completeFlag = isReckon
    }

    private var lastTimeMillis: Long = 0
    override fun doInBackground(vararg p0: Void?): Boolean {
        while (!completeFlag) {
            val dMillis = if (lastTimeMillis != 0L) System.currentTimeMillis() - lastTimeMillis else 0
            lastTimeMillis = System.currentTimeMillis()
            ticket += dMillis
            Thread.sleep(100)
            publishProgress(ticket)
        }
        publishProgress(-1)
        return true
    }

    override fun onProgressUpdate(vararg values: Long?) {
        if ((values[0]!!) == -1L) {
            toolbar!!.title = title
        } else toolbar!!.title = formatterTicket(values[0]!!)
    }

    private var title: String? = null
    override fun onPreExecute() {
        title = toolbar!!.title as String?;
    }

    private fun formatterTicket(ticket: Long): String {
        val ms = ticket % 1000
        var s = ticket / 1000 % 60
        var m = (ticket / (1000 * 60)) % 60
        var h = ticket / (1000 * 60 * 60)
        return "${numberByScale(h, 2)}: ${numberByScale(m, 2)}: ${numberByScale(s, 2)}: $ms"
    }

    private fun numberByScale(num: Long, leng: Int): String {
        val builder: StringBuilder = StringBuilder()
        val numStr: String = num.toString()
        for (i in 0 until leng - numStr.length) builder.append("0")
        builder.append(numStr)
        return builder.toString()
    }

    fun complete() {
        completeFlag = true
    }
}
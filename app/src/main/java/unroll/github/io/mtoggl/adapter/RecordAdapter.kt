package unroll.github.io.mtoggl.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_record.view.*
import unroll.github.io.mtoggl.R
import unroll.github.io.mtoggl.bean.Record
import unroll.github.io.mtoggl.presenter.RecordPresenter

class RecordAdapter : RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    var recordList: ArrayList<Record>? = null
    private var context: Context? = null

    constructor(recordList: ArrayList<Record>?, context: Context?) : super() {
        this.recordList = recordList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_record, parent, false))
    }

    override fun getItemCount(): Int {
        return recordList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val record = recordList!![position]
        val tag = RecordPresenter.getTag(record)

        holder.description!!.text = record.description
        holder.ticket!!.text = formatterTicket(record.ticket!!)
        holder.tag!!.text = tag!!.name
        holder.color!!.setBackgroundColor(Color.parseColor(tag.color))

        holder.itemView.findViewById<View>(R.id.delete).setOnClickListener {
            RecordPresenter.remove(recordList!![position])
            recordList!!.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, recordList!!.size - position);
        }
    }

    private fun formatterTicket(ticket: Long): String {
        var s = ticket / 1000 % 60
        var m = (ticket / (1000 * 60)) % 60
        var h = ticket / (1000 * 60 * 60)
        return "${numberByScale(h, 2)}: ${numberByScale(m, 2)}: ${numberByScale(s, 2)}"
    }

    private fun numberByScale(num: Long, leng: Int): String {
        val builder: StringBuilder = StringBuilder()
        val numStr: String = num.toString()
        for (i in 0 until leng - numStr.length) builder.append("0")
        builder.append(numStr)
        return builder.toString()
    }

    class ViewHolder : RecyclerView.ViewHolder {
        var description: TextView? = null
        var ticket: TextView? = null
        var tag: TextView? = null
        var color: View? = null

        constructor(view: View) : super(view) {
            description = view.findViewById(R.id.description)
            ticket = view.findViewById(R.id.ticket)
            tag = view.findViewById(R.id.tag)
            color = view.findViewById(R.id.color)
        }
    }
}
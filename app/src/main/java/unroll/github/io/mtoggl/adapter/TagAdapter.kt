package unroll.github.io.mtoggl.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_tag.view.*
import unroll.github.io.mtoggl.R
import unroll.github.io.mtoggl.bean.Record
import unroll.github.io.mtoggl.bean.Tag
import unroll.github.io.mtoggl.presenter.RecordPresenter
import unroll.github.io.mtoggl.presenter.TagPresenter

class TagAdapter : RecyclerView.Adapter<TagAdapter.ViewHolder> {
    var tagList: ArrayList<Tag>? = null
    private var context: Context? = null

    constructor(tagList: ArrayList<Tag>?, context: Context?) : super() {
        this.tagList = tagList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tag, parent, false))
    }

    override fun getItemCount(): Int {
        return tagList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag: Tag = tagList!![position]
        holder.ticket!!.text = formatterTicket(TagPresenter.getTicket(tagList!![position]))
        holder.name!!.text = tag.name
        holder.color!!.setBackgroundColor(Color.parseColor(tag.color))
        holder.itemView.findViewById<View>(R.id.delete).setOnClickListener {
            TagPresenter.remove(tagList!![position])
            tagList!!.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tagList!!.size - position);
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
        var name: TextView? = null
        var ticket: TextView? = null
        var color: View? = null

        constructor(itemView: View?) : super(itemView) {
            name = itemView!!.findViewById(R.id.name)
            ticket = itemView!!.findViewById(R.id.ticket)
            color = itemView!!.findViewById(R.id.color)
        }
    }
}
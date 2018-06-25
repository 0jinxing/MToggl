package unroll.github.io.mtoggl.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import unroll.github.io.mtoggl.R
import unroll.github.io.mtoggl.adapter.RecordAdapter
import unroll.github.io.mtoggl.bean.Record
import unroll.github.io.mtoggl.fragment.base.BaseFragment
import unroll.github.io.mtoggl.presenter.RecordPresenter

class RecordFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    var recycler: RecyclerView? = null
    override fun onStart() {
        super.onStart()
        recycler = view!!.findViewById(R.id.recycler)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler!!.layoutManager = linearLayoutManager
        recycler!!.adapter = RecordAdapter(ArrayList(RecordPresenter.getRecordAll()), context)
    }

    fun addElement(element: Record) {
        var adapter: RecordAdapter = recycler!!.adapter as RecordAdapter
        adapter.recordList!!.add(element)
        adapter.notifyItemInserted(adapter.recordList!!.size - 1)
    }

    fun notifyDataSetChanged() {
        var adapter: RecordAdapter = recycler!!.adapter as RecordAdapter
        adapter.recordList = ArrayList(RecordPresenter.getRecordAll())
        adapter.notifyDataSetChanged()
    }
}
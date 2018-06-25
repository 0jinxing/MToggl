package unroll.github.io.mtoggl.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import unroll.github.io.mtoggl.R
import unroll.github.io.mtoggl.adapter.RecordAdapter
import unroll.github.io.mtoggl.adapter.TagAdapter
import unroll.github.io.mtoggl.fragment.base.BaseFragment
import unroll.github.io.mtoggl.presenter.RecordPresenter
import unroll.github.io.mtoggl.presenter.TagPresenter

class TagFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tag, container, false)
    }

    var recycler: RecyclerView? = null

    override fun onStart() {
        super.onStart()
        recycler = view!!.findViewById(R.id.recycler)
        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recycler!!.layoutManager = linearLayoutManager
        recycler!!.adapter = TagAdapter(ArrayList(TagPresenter.getTagAll()), context)
    }

    fun notifyDataSetChanged() {
        var adapter = recycler!!.adapter as TagAdapter
        adapter.tagList = ArrayList(TagPresenter.getTagAll())
        adapter.notifyDataSetChanged()
    }
}
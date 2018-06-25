package unroll.github.io.mtoggl.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import unroll.github.io.mtoggl.R
import unroll.github.io.mtoggl.fragment.base.BaseFragment

class TagFragment: BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tag, container, false)
    }
}
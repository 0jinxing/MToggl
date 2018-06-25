package unroll.github.io.mtoggl.activity.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    protected val that = this
    open var layoutResID: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (layoutResID != null) setContentView(layoutResID!!)
        afterCreate()
    }

    open fun afterCreate() {
    }

    private fun initBar() {

    }
}
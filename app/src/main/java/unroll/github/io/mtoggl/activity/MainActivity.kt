package unroll.github.io.mtoggl.activity

import android.annotation.SuppressLint
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import com.orhanobut.dialogplus.DialogPlus
import com.orhanobut.dialogplus.ViewHolder
import unroll.github.io.mtoggl.R
import unroll.github.io.mtoggl.activity.base.BaseActivity
import unroll.github.io.mtoggl.adapter.PagerAdapter
import unroll.github.io.mtoggl.fragment.RecordFragment
import unroll.github.io.mtoggl.fragment.TagFragment
import unroll.github.io.mtoggl.presenter.RecordPresenter
import unroll.github.io.mtoggl.task.TicketTask
import android.text.method.TextKeyListener.clear


class MainActivity : BaseActivity(), TabLayout.OnTabSelectedListener {
    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        notifyDataSetChanged()
    }

    override var layoutResID: Int? = R.layout.activity_main

    private var toolbar: Toolbar? = null
    private var tabs: TabLayout? = null
    private var pager: ViewPager? = null
    private var navigation: NavigationView? = null
    private var drawer: DrawerLayout? = null

    private var recordFragment: RecordFragment? = null
    private var tagFragment: TagFragment? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    private var isStart = false
    var control: ActionMenuItemView? = null
    var ticketTask: TicketTask? = null
    var description: String? = null
    private var tagName: String? = null

    @SuppressLint("RestrictedApi")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (control == null) control = that.findViewById(R.id.control)
        when (item!!.itemId) {
            (android.R.id.home) -> drawer!!.openDrawer(Gravity.START)
            (R.id.control) -> {
                if (!isStart) {
                    val dialog = DialogPlus.newDialog(this)
                            .setContentHolder(ViewHolder(R.layout.activity_record_dialog))
                            .setExpanded(true)
                            .setPadding(120, 120, 120, 0)
                            .create()
                    val create: Button = dialog.holderView.findViewById(R.id.create)
                    create.setOnClickListener {
                        // 开始一个计时任务
                        isStart = true;
                        description = dialog.holderView.findViewById<EditText>(R.id.description).text.toString()
                        tagName = dialog.holderView.findViewById<EditText>(R.id.tag).text.toString()
                        ticketTask = if (ticketTask != null) {
                            ticketTask!!.complete()
                            TicketTask(toolbar!!)
                        } else TicketTask(toolbar!!)
                        ticketTask!!.execute()
                        control!!.setIcon(getDrawable(R.drawable.stop))
                        dialog.dismiss()
                    }
                    dialog.show()
                } else {
                    // 结束一个计时任务
                    isStart = false
                    ticketTask!!.complete()
                    control!!.setIcon(getDrawable(R.drawable.start))
                    // TODO 保存工作
                    val record = RecordPresenter.add(description!!, tagName!!, ticketTask!!.ticket)
                    recordFragment!!.addElement(record)

                    Log.d("个数", RecordPresenter.getRecordAll().size.toString())
                    ticketTask = null
                }
            }
        }
        return true
    }

    override fun afterCreate() {
        super.afterCreate()
        toolbar = findViewById(R.id.toolbar)
        tabs = findViewById(R.id.tabs)
        pager = findViewById(R.id.pager)
        navigation = findViewById(R.id.navigation)
        drawer = findViewById(R.id.drawer)

        initBar()
        initTabs()
        initNavigation()
    }

    private fun initBar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.abc_ic_menu_overflow_material)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initTabs() {
        val titles = resources.getStringArray(R.array.tabs).toList()
        for (title in titles) {
            tabs!!.addTab(tabs!!.newTab().setText(title))
        }

        val fragments = ArrayList<Fragment>()
        recordFragment = RecordFragment()
        tagFragment = TagFragment()
        fragments.add(recordFragment!!)
        fragments.add(tagFragment!!)

        val adapter = PagerAdapter(supportFragmentManager, fragments, titles);
        pager!!.adapter = adapter;
        tabs!!.setupWithViewPager(pager)

        tabs!!.addOnTabSelectedListener(this)
    }

    private fun initNavigation() {
        navigation!!.setNavigationItemSelectedListener {
            when (it.itemId) {
                (R.id.info) -> {
                    DialogPlus.newDialog(this)
                            .setContentHolder(ViewHolder(R.layout.dialog_info))
                            .setExpanded(true)
                            .setPadding(120, 120, 120, 0)
                            .create()
                            .show()
                    drawer!!.closeDrawers()
                }
            }
            true
        }
    }

    private fun notifyDataSetChanged() {
        recordFragment!!.notifyDataSetChanged()
        tagFragment!!.notifyDataSetChanged()
    }
}


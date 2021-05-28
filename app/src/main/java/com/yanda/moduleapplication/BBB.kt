package com.yanda.moduleapplication

import android.view.View
import android.widget.AdapterView
import android.widget.Button
import butterknife.BindView
import com.yanda.module_base.base.BaseActivity

/**
 * 作者：caibin
 * 时间：2021/5/20 11:34
 * 类说明：
 */
class BBB : BaseActivity(), AdapterView.OnItemClickListener {

    override fun initContentView(): Int {
        return R.layout.activity_kotlin
    }

    override fun initView() {
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener(this)
    }

    override fun addOnClick() {
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.button -> {
                showToast("店家啊啊")
            }
            R.id.twobutton -> {
            }
            else -> {
            }
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {}
}
package com.yanda.moduleapplication

import android.view.View
import android.widget.Button
import com.yanda.module_base.base.BaseActivity
import com.yanda.module_base.utils.NetWorkUtils
import kotlinx.android.synthetic.main.activity_kotlin.*

/**
 * 作者：caibin
 * 时间：2021/5/20 10:40
 * 类说明：
 */
class KotlinActivity : BaseActivity() {
    var button: Button? = null
    var twoButton: Button? = null
    override fun initContentView(): Int {
        return R.layout.activity_kotlin
    }

    override fun initView() {
        button = findViewById(R.id.button)
        twoButton = findViewById(R.id.twobutton)
    }

    override fun addOnClick() {
        button!!.setOnClickListener(this)
        button?.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.button -> {

            }
            R.id.twobutton -> {

            }
            else -> {
            }
        }
    }
}
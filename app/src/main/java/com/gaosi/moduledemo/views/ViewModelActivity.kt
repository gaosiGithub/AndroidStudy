package com.gaosi.moduledemo.views

import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.gaosi.module_base.base.BaseActivity
import com.gaosi.moduledemo.R
import com.gaosi.moduledemo.utils.Constants.TAG
import com.gaosi.moduledemo.videModel.LocationViewModel
import kotlinx.android.synthetic.main.activity_view_model.*
import kotlinx.coroutines.flow.collect

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/8/17 11:24
 *
 **/
class ViewModelActivity : BaseActivity() {

    private val viewModel by viewModels<LocationViewModel>()
    private lateinit var dialog: AlertDialog

    override fun layoutId(): Int {
        return R.layout.activity_view_model
    }

    override fun showTopBar(): Boolean {
        return true
    }

    override fun initData() {
        Log.d(TAG, "initData")

        dialog = AlertDialog.Builder(this)
            .setMessage("this is a dialog")
            .setTitle("dialog")
            .setCancelable(false)
            .create()

        lifecycleScope.launchWhenResumed {
            viewModel.locationState.collect {
                Log.d(TAG, "locationState collect : $it")
                contentTxt.text = it
            }
        }

        setTitle("viewModel", true)
    }

    override fun initListener() {

    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }
}
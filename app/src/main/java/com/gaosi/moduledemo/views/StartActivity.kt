package com.gaosi.moduledemo.views

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.gaosi.module_base.base.BaseActivity
import com.gaosi.moduledemo.R
import com.gaosi.moduledemo.adapter.MainAdapter
import com.gaosi.moduledemo.bean.MainData
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.gaosi.moduledemo.utils.Constants.TAG
import com.gaosi.moduledemo.videModel.StartPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/8/18 14:20
 *
 **/
@AndroidEntryPoint
class StartActivity : BaseActivity() {

    companion object {
        const val ANIMATOR_DURATION = 3000L
    }

    private lateinit var mainAdapter: MainAdapter
    private var mainListData = mutableListOf<MainData>()
    private val viewModel by viewModels<StartPageViewModel>()

    override fun showTopBar(): Boolean {
        return false
    }

    override fun initData() {

        setTitle("主界面", false)
        setPaddingTopAndTitleIsWhite(
            showStatusBar = true,
            decorFitsSystemWindow = false,
            isWhite = false,
            statusBarColor = ContextCompat.getColor(this, R.color.black_20)
        )


        mainAdapter = MainAdapter(this, mainListData) {
            when (mainListData[it].content) {
                "设计模式" -> {

                }
                "Hilt学习" -> {
                    try {
                        val clazz = Class.forName("com.gaosi.module_hilt.HiltActivity")
                        startActivity(Intent(this@StartActivity, clazz))
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }
                "高级UI" -> {
                    try {
                        val clazz = Class.forName("com.example.module_master_ui.MasterUiActivity")
                        startActivity(Intent(this@StartActivity, clazz))
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }
                "Jetpack学习" -> {
                    try {
                        val clazz = Class.forName("com.gaosi.module_jetpack.JetpackActivity")
                        startActivity(Intent(this@StartActivity, clazz))
                    } catch (e: Exception) {
                        e.stackTrace
                    }
                }
            }
        }

        main_recycler.layoutManager = LinearLayoutManager(this)
        main_recycler.adapter = mainAdapter

        viewModel.getSourceData()
//        viewModel.initBgAnimation()
    }

    override fun initListener() {
        lifecycleScope.launch {

            launch {
                viewModel.dataSource.collect {
                    Log.d(TAG, "database flow : $it")

                    mainListData.clear()
                    mainListData.addAll(it)
                    mainAdapter.notifyDataSetChanged()
                }
            }

//            launch(Dispatchers.Main) {
//                viewModel.bgAnimationColor.onEach {
//                    main_bg.setBackgroundColor(it)
//                }.launchIn(this)
//            }
        }

    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }
}
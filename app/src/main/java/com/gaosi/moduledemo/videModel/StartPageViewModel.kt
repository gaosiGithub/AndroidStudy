package com.gaosi.moduledemo.videModel

import android.animation.ValueAnimator
import android.app.Application
import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaosi.moduledemo.bean.MainData
import com.gaosi.moduledemo.repository.StartPageRepository
import com.gaosi.moduledemo.views.StartActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartPageViewModel @Inject constructor(
        private val startPageRepository: StartPageRepository
) : ViewModel() {

    private val _dataSource = MutableStateFlow<List<MainData>>(mutableListOf())

    var dataSource: StateFlow<List<MainData>> = _dataSource

    private val _bgAnimationColor = MutableStateFlow(0)

    var bgAnimationColor: StateFlow<Int> = _bgAnimationColor

    private var currentBgColorIndex = 0
    private var colorList = mutableListOf(
            Color.WHITE,
            Color.GRAY,
            Color.CYAN,
            Color.GREEN,
            Color.LTGRAY,
            Color.MAGENTA,
            Color.YELLOW,
            Color.RED,
            Color.DKGRAY
    )

    init {
        viewModelScope.launch {
            startPageRepository.sourceData.collect {
                _dataSource.value = it
            }
        }
    }

    fun getSourceData() {
        viewModelScope.launch {
            startPageRepository.getSourceData()
        }
    }

    private val bgAnimation = ValueAnimator.ofFloat(0f, 1f) // animate from 0 to 1
    private val hsv = FloatArray(3) // transition color
    private val from = FloatArray(3)
    private val to = FloatArray(3)
    fun initBgAnimation() {
        viewModelScope.launch {
            bgAnimation.duration = StartActivity.ANIMATOR_DURATION
            bgAnimation.addUpdateListener { animation -> // Transition along each axis of HSV (hue, saturation, value)
                hsv[0] = from[0] + (to[0] - from[0]) * animation.animatedFraction
                hsv[1] = from[1] + (to[1] - from[1]) * animation.animatedFraction
                hsv[2] = from[2] + (to[2] - from[2]) * animation.animatedFraction
                _bgAnimationColor.value = Color.HSVToColor(hsv)
            }

            while (true) {
                animateColor()
                delay(StartActivity.ANIMATOR_DURATION)
            }
        }
    }

    private fun animateColor() {
        //当前颜色转换为hsv
        Color.colorToHSV(colorList[currentBgColorIndex], from)
        //下一个要显示的颜色转换为hsv
        if (currentBgColorIndex == colorList.size - 1) {
            Color.colorToHSV(colorList[0], to)
            currentBgColorIndex = 0
        } else {
            Color.colorToHSV(colorList[currentBgColorIndex + 1], to)
            currentBgColorIndex += 1
        }
        bgAnimation.start()
    }

}
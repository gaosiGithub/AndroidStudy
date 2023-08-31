package com.gaosi.module_jetpack.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.gaosi.module_jetpack.R
import kotlinx.android.synthetic.main.fragment1.*

class Fragment1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment1_btn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragment1_to_fragment2)
        }
    }

}
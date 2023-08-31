package com.gaosi.moduledemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaosi.moduledemo.R
import com.gaosi.moduledemo.bean.MainData

/**
 *
 * @author gaosi5
 * @describe
 * @date on 2023 2023/8/18 13:51
 *
 **/
class MainAdapter(
        private val context: Context,
        private val listData: List<MainData>,
        val onItemClick: (position : Int) -> Unit
) :
        RecyclerView.Adapter<MainAdapter.MainHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MainHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_main, p0, false)

        return MainHolder(view)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val data = listData[position]

        holder.content?.text = data.content
        holder.image?.setImageResource(data.contentImage)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content: TextView? = null
        var txtLearn: TextView? = null
        var image: ImageView? = null

        init {
            content = itemView.findViewById(R.id.item_main_content)
            txtLearn = itemView.findViewById(R.id.item_main_txt_learn)
            image = itemView.findViewById(R.id.item_main_image)

            txtLearn?.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

    }
}
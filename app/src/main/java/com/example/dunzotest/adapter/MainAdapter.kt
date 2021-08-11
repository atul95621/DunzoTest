package com.example.shipsytest.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dunzotest.MainActivity
import com.example.dunzotest.R
import com.example.dunzotest.databinding.ItemLayoutBinding
import com.example.shipsytest.model.PhotoItem

class MainAdapter(
    var arrayList: MutableList<PhotoItem>,
    var mainActivity: MainActivity
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): ViewHolder {

        val binding = ItemLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position, arrayList[position])
    }

    inner class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, list: PhotoItem) {

            binding.tvTitle.text = list.title

//            https://farm66.staticflickr.com/65535/51364632332_e8f04591ff_m.jpg
            var imageUrl =
                "https://farm${list.farm}.staticflickr.com/${list.server}/${list.id}_${list.secret}_m.jpg"
            Glide.with(mainActivity).load(imageUrl).into(binding.imgImage)

        }
    }
}
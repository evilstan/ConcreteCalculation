package com.evilstan.concrete.ui.core

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.evilstan.concrete.R
import com.evilstan.concrete.data.Concrete

class ConstructionsAdapter(
    private val dataSet: MutableList<Concrete>,
    private val onItemDeleteListener: OnItemDeleteListener
) : RecyclerView.Adapter<ConstructionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val construction = dataSet[position]
        val dimension: String = (position + 1).toString() + ". " + construction.dimensions()

        holder.setDimensions(dimension)
        holder.setVolume(construction.volume())
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItem(position: Int){
        dataSet.removeAt(position)
        notifyDataSetChanged()
        onItemDeleteListener.onDelete()
    }

    override fun getItemCount(): Int = dataSet.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var dimensionsText: TextView = view.findViewById(R.id.dimensions_text)
        private var volumeText: TextView = view.findViewById(R.id.volume_text)

        fun setDimensions(text: String) {
            dimensionsText.text = text
        }

        fun setVolume(text: String) {
            volumeText.text = text
        }
    }

    interface OnItemDeleteListener{
        fun onDelete()
    }
}
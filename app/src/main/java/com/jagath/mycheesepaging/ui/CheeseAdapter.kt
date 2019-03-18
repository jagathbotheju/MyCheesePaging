package com.jagath.mycheesepaging.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jagath.mycheesepaging.R
import com.jagath.mycheesepaging.db.Cheese

class CheeseAdapter:PagedListAdapter<Cheese, CheeseAdapter.CheeseViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: CheeseViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= CheeseViewHolder(parent)

    class CheeseViewHolder(parent: ViewGroup):RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.cheese_item,parent,false)){

        private val nameView=itemView.findViewById<TextView>(R.id.name)
        var cheese:Cheese?=null

        /**
         * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
         * ViewHolder when Item is loaded.
         */
        fun bindTo(cheese: Cheese?){
            this.cheese=cheese
            nameView.text=cheese?.name
        }
    }

    companion object {
        private val diffCallback=object :DiffUtil.ItemCallback<Cheese>(){
            override fun areItemsTheSame(oldItem: Cheese, newItem: Cheese)=
                    oldItem.id==newItem.id

            override fun areContentsTheSame(oldItem: Cheese, newItem: Cheese)= oldItem==newItem
        }
    }
}
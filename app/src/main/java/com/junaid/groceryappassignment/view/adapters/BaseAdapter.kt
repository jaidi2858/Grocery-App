package com.junaid.groceryappassignment.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<T> constructor(
    var onItemClicker: OnItemClicker?,
    private val layoutResId: Int, private val adapterType: Int = -1
) : RecyclerView.Adapter<BaseAdapter.Holder>() {

    protected var itemList: ArrayList<T> = ArrayList()

    override fun getItemCount() = itemList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return Holder(view)
    }

    fun updateItem(position: Int, data: T) {
        itemList[position]=data
        notifyItemChanged(position,data)
    }

    fun removeItem(position: Int) {
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem( data: T,inLast: Boolean=false) {
        itemList.add(data)
        notifyItemInserted(itemList.size-1)
    }



    fun addItems(data: ArrayList<T>) {
        val previousPosition = itemList.size - 1
        itemList.addAll(data)
        notifyItemRangeChanged(previousPosition, itemList.size)
    }
    fun clear() {
        itemList.clear()
        notifyDataSetChanged()
    }

    fun updateItemList(updatedItemsList: ArrayList<T>) {
        itemList.clear()
        itemList.addAll(updatedItemsList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = itemList[position]
        holder.itemView.setOnClickListener {
            onItemClicker?.let {
                it.onItemClick(position, itemList[position])
            }
        }
        holder.itemView.bind(item, position)
    }


    protected open fun View.bind(item: T, position: Int) {
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)


    interface OnItemClicker {
        fun onItemClick(position: Int, data: Any?)
    }
}
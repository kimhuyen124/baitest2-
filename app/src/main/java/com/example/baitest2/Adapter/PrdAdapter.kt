package com.example.baitest2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.baitest2.ProductModel
import com.example.baitest2.R
import com.squareup.picasso.Picasso


class PrdAdapter(private val ds:ArrayList<ProductModel>) : RecyclerView.Adapter<PrdAdapter.ViewHolder>() {
    // code adapter lắng nghe sự kiện
    private lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener (clickListener: onItemClickListener){
        mListener = clickListener
    }
    // tạo class viewholder
    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
        val imgViewPrd: ImageView = itemView.findViewById(R.id.imgViewPrd)
        val tvPrdName: TextView = itemView.findViewById(R.id.tvPrdName)
        val tvPrdType: TextView = itemView.findViewById(R.id.tvPrdType)
        val tvPrdPrice: TextView = itemView.findViewById(R.id.tvPrdPrice)
    }

    // ctrl + i
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemview = LayoutInflater.from(parent.context)
            .inflate(R.layout.prd_list_item, parent, false)
        return ViewHolder(itemview, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = ds[position]

        // Hiển thị thông tin sản phẩm
        holder.tvPrdName.text = currentItem.prdName
        holder.tvPrdType.text = currentItem.prdType
        holder.tvPrdPrice.text = currentItem.prdPrice.toString()
        Picasso.get().load(currentItem.prdImg).into(holder.imgViewPrd)
    }

    override fun getItemCount(): Int {
        return ds.size
    }
}
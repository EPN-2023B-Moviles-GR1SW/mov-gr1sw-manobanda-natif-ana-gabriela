package com.example.appgoogledrive

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UnidadAdapter(
    private val contex: Context,
    private val unidadItems: List<UnidadItem>,
    private val listener: (UnidadItem) -> Unit)
    : RecyclerView.Adapter<UnidadAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(contex).inflate(R.layout.activity_unidad_item, parent, false)
    )
    override fun getItemCount(): Int = unidadItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(unidadItems[position], listener)
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name1 = view.findViewById<TextView>(R.id.name1)
        val fechaMod = view.findViewById<TextView>(R.id.fecha)
        val image = view.findViewById<ImageView>(R.id.image2)

        fun bindItem(items: UnidadItem, listener: (UnidadItem) -> Unit){
            name1.text = items.name
            fechaMod.text = items.fechaMod

            Glide.with(itemView.context)
                .load(items.image)
                .into(image)

            itemView.setOnClickListener{
                listener(items)
            }
        }
    }
}
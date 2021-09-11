package com.example.restaurantorderapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter_Products( context: Context?,
                                allProducts: Array<Product>     ) :
    RecyclerView.Adapter<RecyclerAdapter_Products.DishViewHolder>(){

    var DishID = allProducts.map { it.id }.toTypedArray()
    var DishName = allProducts.map { it.name }.toTypedArray()
    var DishShortDesc =  allProducts.map { it.short_desc }.toTypedArray()
    var DishPrice = allProducts.map { it.price }.toTypedArray()
    var DishCategory = allProducts.map { it.category }.toTypedArray()
    var DishDescription = allProducts.map { it.full_desc }.toTypedArray()

    var DishImages = allProducts.map { it.image }.toTypedArray()

    val ctx = context

    val mInflater = LayoutInflater.from(context)

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishID = itemView.findViewById<TextView>(R.id.prod_id)
        val dishName = itemView.findViewById<TextView>(R.id.cusName)
        val dishShortDesc = itemView.findViewById<TextView>(R.id.cus_phone)
        val price = itemView.findViewById<TextView>(R.id.order_total_price)
        val category = itemView.findViewById<TextView>(R.id.txt_category)

        val thumbnail = itemView.findViewById<ImageView>(R.id.order_type_thumb)

        val layout = itemView.findViewById<ConstraintLayout>(R.id.rv_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = mInflater.inflate(R.layout.dish_card_rv, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.dishID.text = DishID[position].toString()
        holder.dishName.text = DishName[position]
        holder.dishShortDesc.text = DishShortDesc[position]
        holder.category.text = DishCategory[position]
        holder.price.text = "$ " + DishPrice[position].toString()

        if(DishImages[position] != null){
            holder.thumbnail.setImageBitmap(
                    BitmapFactory.decodeByteArray(DishImages[position],0,DishImages[position]!!.size))
        } else
            holder.thumbnail.background = R.drawable.dish_thumbnail.toDrawable()

        holder.layout.setOnClickListener {
            val data = Bundle().apply {
                putString("id", DishID[position].toString())
                putString("name", DishName[position])
                putString("shortDesc", DishShortDesc[position])
                putString("fullDesc", DishDescription[position])
                putString("category", DishCategory[position])
                putString("price", DishPrice[position].toString())
                putByteArray("image", DishImages[position])
            }
            val intent =  Intent(ctx,UpdateProducts::class.java)
            intent.putExtras(data)
            startActivity(ctx!!, intent, data)
        }
    }
    override fun getItemCount(): Int {
        return DishName.size
    }
}
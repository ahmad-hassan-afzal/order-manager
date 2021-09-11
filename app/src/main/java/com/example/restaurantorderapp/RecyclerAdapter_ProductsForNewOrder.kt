package com.example.restaurantorderapp

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton


class RecyclerAdapter_ProductsForNewOrder(context: Context?,
                                          allProducts: Array<Product>) :
    RecyclerView.Adapter<RecyclerAdapter_ProductsForNewOrder.DishViewHolder>(){

    private var counter = 0
    private var isChecked = false

    var DishID = allProducts.map { it.id }.toTypedArray()
    var DishName = allProducts.map { it.name }.toTypedArray()
    var DishCategory = allProducts.map { it.name }.toTypedArray()
    var DishShortDesc =  allProducts.map { it.short_desc }.toTypedArray()
    var DishDescription =  allProducts.map { it.short_desc }.toTypedArray()
    var DishPrice = allProducts.map { it.price }.toTypedArray()
    var DishImages = allProducts.map { it.image }.toTypedArray()

    val ctx = context

    val mInflater = LayoutInflater.from(context)

    class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishName = itemView.findViewById<TextView>(R.id.cusName)
        val dishShortDesc = itemView.findViewById<TextView>(R.id.cus_phone)
        val price = itemView.findViewById<TextView>(R.id.order_total_price)

        val edtqty = itemView.findViewById<TextView>(R.id.edt_qty)
        val btnAdd = itemView.findViewById<Button>(R.id.btnQtyPlus)
        val btnSub = itemView.findViewById<Button>(R.id.btnQtyMinus)
        val btnAddToCart = itemView.findViewById<Button>(R.id.btnAddToCart)

        val thumbnail = itemView.findViewById<ImageView>(R.id.order_type_thumb)

        val layout = itemView.findViewById<ConstraintLayout>(R.id.rv_layout)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val view = mInflater.inflate(R.layout.products_for_order_rv, parent, false)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.dishName.text = DishName[position]
        holder.dishShortDesc.text = DishShortDesc[position]
        holder.price.text = DishPrice[position].toString()

        if(DishImages[position] != null){
            holder.thumbnail.setImageBitmap(
                    BitmapFactory.decodeByteArray(DishImages[position], 0, DishImages[position]!!.size))
        } else
            holder.thumbnail.background = R.drawable.dish_thumbnail.toDrawable()


        holder.btnAddToCart.setOnClickListener {
            val product = OrderItems(DishID[position], DishName[position], DishPrice[position], holder.edtqty.text.toString().toInt())

            if (holder.edtqty.text.toString().toInt() != 0) {
                if ( product !in CreateNewOrder.cart ) {
                    CreateNewOrder.cart.add(OrderItems(DishID[position], DishName[position], DishPrice[position], holder.edtqty.text.toString().toInt()))
                    Toast.makeText(ctx!!, "${DishName[position]}; Added to Cart", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(ctx!!, "${DishName[position]}; is Already in Cart", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(ctx!!, "Please Set Quantity First", Toast.LENGTH_SHORT).show()
            }

        }

        holder.btnAdd.setOnClickListener{
            counter++
            holder.edtqty.setText(counter.toString())
        }
        holder.btnSub.setOnClickListener {
            if (counter > 0){
                counter--
                holder.edtqty.setText(counter.toString())
            }
        }
        holder.thumbnail.setOnClickListener {
            val data = Bundle().apply {
                putString("id", DishID[position].toString())
                putString("name", DishName[position])
                putString("shortDesc", DishShortDesc[position])
                putString("fullDesc", DishDescription[position])
                putString("category", DishCategory[position])
                putString("price", DishPrice[position].toString())
                putByteArray("img", DishImages[position])
            }
            val intent = Intent(ctx, ProductDetails::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtras(data)

            ActivityCompat.startActivity(ctx!!, intent, data)
        }
    }
    override fun getItemCount(): Int {
        return DishName.size
    }
}
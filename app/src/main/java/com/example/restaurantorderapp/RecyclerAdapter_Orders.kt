package com.example.restaurantorderapp

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter_Orders(context: Context?, names: Array<String>, shortDesc: Array<String>, images: Array<Int>, prices: Array<Int>) :
    RecyclerView.Adapter<RecyclerAdapter_Orders.OrderViewHolder>(){

    var DishName = names
    var DishShortDesc = shortDesc
    var DishPrice = prices
    var DishImages = images

    var counter = 0

    val ctx = context

    val mInflater = LayoutInflater.from(context)

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dishName = itemView.findViewById<TextView>(R.id.cusName)
        val dishPrice = itemView.findViewById<TextView>(R.id.order_total_price)
        val dishShortDesc = itemView.findViewById<TextView>(R.id.cus_phone)
        val thumbnail = itemView.findViewById<ImageView>(R.id.order_type_thumb)

        val layout = itemView.findViewById<ConstraintLayout>(R.id.rv_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = mInflater.inflate(R.layout.order_rv, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.dishName.text = DishName[position]
        holder.dishShortDesc.text = DishShortDesc[position]
        holder.dishPrice.text ="$"+DishPrice[position].toString()
        holder.thumbnail.setImageResource(DishImages[position])

        holder.layout.setOnClickListener {

            val dialog: Dialog = Dialog(ctx!!)
            dialog.setContentView(R.layout.dialog_order_details)
            dialog.findViewById<TextView>(R.id.dlg_txt_cus_name).text = DishName[position]

            dialog.findViewById<TextView>(R.id.dlg_txt_order_names).text = "Pizza\nBiryani"
            dialog.findViewById<TextView>(R.id.dlg_txt_order_prices).text = "23\n103"
            dialog.findViewById<TextView>(R.id.dlg_txt_order_qty).text = "1\n2"
            dialog.findViewById<TextView>(R.id.dlg_txt_order_total).text = "229"

            dialog.show()
        }
    }
    override fun getItemCount(): Int {
        return DishName.size
    }
}
package com.example.restaurantorderapp.ui.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantorderapp.DatabaseHelper
import com.example.restaurantorderapp.RecyclerAdapter_Products
import com.example.restaurantorderapp.R
import com.example.restaurantorderapp.RecyclerAdapter_Orders

class OrdersFragment : Fragment() {

    private lateinit var ordersViewModel: OrdersViewModel



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ordersViewModel =
            ViewModelProvider(this).get(OrdersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_orders, container, false)

        val animUp = AnimationUtils.loadAnimation(root.context, R.anim.slide_up)
        root.findViewById<RecyclerView>(R.id.cycle).startAnimation(animUp)

        val allOrders = DatabaseHelper(root.context).getAllOrders()

        val names = allOrders.map { it.name }.toTypedArray()
        val desc = allOrders.map { it.contact }.toTypedArray()
        val prices = allOrders.map { it.totalPrice }.toTypedArray()
        val images = arrayOf(R.drawable.dish_thumb,R.drawable.takeaway_125px,R.drawable.dish_thumb,R.drawable.takeaway_125px,
            R.drawable.dish_thumb,R.drawable.takeaway_125px,R.drawable.dish_thumb,R.drawable.takeaway_125px,
            R.drawable.dish_thumb,R.drawable.takeaway_125px,R.drawable.dish_thumb,R.drawable.takeaway_125px,
            R.drawable.dish_thumb,R.drawable.takeaway_125px,R.drawable.dish_thumb,R.drawable.takeaway_125px)


        val rv = root.findViewById<RecyclerView>(R.id.cycle)
        rv.adapter = RecyclerAdapter_Orders(context, names, desc, images, prices)
        rv.layoutManager = LinearLayoutManager(context)

        return root
    }
}
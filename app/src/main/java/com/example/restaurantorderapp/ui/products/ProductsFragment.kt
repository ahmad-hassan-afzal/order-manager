package com.example.restaurantorderapp.ui.products

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
import com.example.restaurantorderapp.R
import com.example.restaurantorderapp.RecyclerAdapter_Orders
import com.example.restaurantorderapp.RecyclerAdapter_Products

class ProductsFragment : Fragment() {

    private lateinit var homeViewModel: ProductsViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter_Products

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(ProductsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_products, container, false)

        val animUp = AnimationUtils.loadAnimation(root.context, R.anim.slide_up)
        root.findViewById<RecyclerView>(R.id.cycle).startAnimation(animUp)

        val db = DatabaseHelper(root.context)
        var allProducts = db.getAllProducts()

        recyclerView = root.findViewById<RecyclerView>(R.id.cycle)
        recyclerAdapter = RecyclerAdapter_Products(context, allProducts.toTypedArray())
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
//
        return root
    }
}
package com.example.restaurantorderapp

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class ProductDetails : AppCompatActivity() {
    var qty = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Product Details"

        setContentView(R.layout.activity_product_details)

        val bundle = intent.extras

        // setting Data in Fields from bundle passed by Product_Card_Recyclerview
        findViewById<TextView>(R.id.lblDishName).text = bundle!!.getString("name")
        findViewById<TextView>(R.id.lblShortDesc).text = bundle.getString("shortDesc")
//        findViewById<TextView>(R.id.txtDescription).text = bundle.getString("fullDesc")
        findViewById<TextView>(R.id.txtPrice).text = "$ " + bundle.getString("price")
        findViewById<TextView>(R.id.txtCategory).text = bundle.getString("category")

        val img = bundle.getByteArray("img")
        findViewById<ImageView>(R.id.imageView).setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img!!.size ))

    }

    fun addToCart(view: View) {
        //Add to cart functionality
    }
}
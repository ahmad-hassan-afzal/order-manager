package com.example.restaurantorderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ManageOrder : AppCompatActivity() {
    var backPressed = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_manage_order)

        val animDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        findViewById<ImageView>(R.id.imageView2).startAnimation(animDown)
        findViewById<ImageView>(R.id.order_type_thumb3).startAnimation(animDown)
        findViewById<TextView>(R.id.txt_username4).startAnimation(animDown)
        findViewById<TextView>(R.id.lbl_username).startAnimation(animDown)
        findViewById<TextView>(R.id.lbl_user_type).startAnimation(animDown)
        findViewById<TextView>(R.id.lbl_pending_orders).startAnimation(animDown)
        findViewById<TextView>(R.id.txt_username2).startAnimation(animDown)

        val animUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        findViewById<RecyclerView>(R.id.cycle).startAnimation(animUp)

        val allOrders = DatabaseHelper(this).getAllOrders()

        val names = allOrders.map { it.name }.toTypedArray()
        val desc = allOrders.map { it.contact }.toTypedArray()
        val prices = allOrders.map { it.totalPrice }.toTypedArray()
        val images = arrayOf(R.drawable.dish_thumb,R.drawable.takeaway_125px,R.drawable.dish_thumb,R.drawable.takeaway_125px,
                R.drawable.dish_thumb,R.drawable.takeaway_125px,R.drawable.dish_thumb,R.drawable.takeaway_125px,
                R.drawable.dish_thumb,R.drawable.takeaway_125px,R.drawable.dish_thumb,R.drawable.takeaway_125px,
                R.drawable.dish_thumb,R.drawable.takeaway_125px,R.drawable.dish_thumb,R.drawable.takeaway_125px)


        val rv = findViewById<RecyclerView>(R.id.cycle)
        rv.adapter = RecyclerAdapter_Orders(this, names, desc, images, prices)
        rv.layoutManager = LinearLayoutManager(this)

    }
    fun logout(view: View){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
    fun createNewOrder(view: View) {
        startActivity(Intent(this, CreateNewOrder::class.java))
    }

    override fun onBackPressed() {
        if (LoginActivity.userType == "Waiter") {
            if (backPressed) {
                logout(this.findViewById(R.id.floatingActionButton))
            } else {
                Toast.makeText(this, "Press again to Logout", Toast.LENGTH_SHORT).show()
                backPressed = true
            }
        } else {
            super.onBackPressed()
        }
    }
}
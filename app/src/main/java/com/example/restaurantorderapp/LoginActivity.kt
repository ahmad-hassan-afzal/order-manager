package com.example.restaurantorderapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

class LoginActivity : AppCompatActivity() {
    companion object {
        var username = ""
        var userType = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = DatabaseHelper(this)

//        val image = resources.getDrawable(R.drawable.dish_thumbnail)
//        val bitmap = (image as BitmapDrawable).bitmap
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

//        db.insertProduct(Product(3, "Pizza", "Malai Boti", "", "Fast Food", 23, stream.toByteArray()))
//        db.insertProduct(Product(6, "Biryani", "Chicken", "", "Desi Food", 103, stream.toByteArray()))
//        db.insertProduct(Product(13, "Pasta", "Chicken Boneles", "", "Fast Food", 53, stream.toByteArray()))
//        db.insertProduct(Product(22, "BarBeQue", "Chicken BBQ", "", "Food", 63, stream.toByteArray()))
//        db.insertProduct(Product(7, "Pratha", "Alloo  Pratha", "", "Desi Food", 23, stream.toByteArray()))
//        db.insertProduct(Product(8, "Chicken Krahi", "Chicken Krahi", "", "Desi Food", 433, stream.toByteArray()))
//        db.insertProduct(Product(39, "Pizza", "Fajita", "", "Fast Food", 38, stream.toByteArray()))
//        db.insertProduct(Product(5, "Pizza", "Pepperoni Pizza", "", "Fast Food", 23, stream.toByteArray()))

    }

    override fun onPause() {
        findViewById<ImageView>(R.id.imageView_splash_loading).visibility = View.INVISIBLE
        super.onPause()
    }

    fun auth(view: View) {

        Glide.with(this).load(R.drawable.splash_screen).into(
                findViewById(R.id.imageView_splash_loading)
        )

        val username = findViewById<EditText>(R.id.edtUsername).text.toString()
        val password = findViewById<EditText>(R.id.edtPassword).text.toString()
        val userType = findViewById<Spinner>(R.id.spinner).selectedItem.toString()

        if (userType == "Manager") {
            LoginActivity.userType = "Manager"
            LoginActivity.username = "ahmad"

            if (username == "ahmad" && password == "123") {
                Handler().postDelayed({
                    startActivity(Intent(this, ProductManager::class.java))
                    finish()
                }, 3000)
            } else {
                findViewById<ImageView>(R.id.imageView_splash_loading).visibility = View.INVISIBLE
                Toast.makeText(this, "Login Failed\nPlease Enter Valid Credentials", Toast.LENGTH_SHORT).show()
            }
        } else {
            LoginActivity.userType = "Waiter"
            LoginActivity.username = "w"
            if (username == "w" && password == "2") {
                Handler().postDelayed({
                    startActivity(Intent(this, ManageOrder::class.java))
                    finish()
                }, 3000)
            } else {
                findViewById<ImageView>(R.id.imageView_splash_loading).visibility = View.INVISIBLE
                Toast.makeText(this, "Login Failed\nPlease Enter Valid Credentials", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
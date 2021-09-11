package com.example.restaurantorderapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProductManager : AppCompatActivity() {

    var backPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_manager)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_products, R.id.navigation_dashboard, R.id.navigation_order
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Get Permissions if not available Using Dialog

        val perms = arrayOf("android.permission.WRITE_EXTERNAL_STORAGE")
        val permsRequestCode = 200
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(perms, permsRequestCode)
        }

//        supportActionBar?.title = "OoO YEAH"
//        supportActionBar?.hide()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            200 -> {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Please Grant App Permissions\nOtherwise App will misbehave", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    fun logout(view: View) {
        startActivity(Intent(applicationContext, LoginActivity::class.java))
        finish()
    }
    fun startNewActivity(view: View) {
        if  (view.id == R.id.btn_new_order)
            startActivity(Intent(this, ManageOrder::class.java))
        else if (view.id == R.id.btn_new_product)
            startActivity(Intent(this, NewProducts::class.java))

    }

}

package com.example.restaurantorderapp

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class CreateNewOrder : AppCompatActivity() {

    companion object {
        var cart = ArrayList<OrderItems>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_order)

        val db = DatabaseHelper(applicationContext)
        val allProducts = db.getAllProducts()

        val recyclerView = findViewById<RecyclerView>(R.id.recycle_new_order)
        recyclerView.adapter = RecyclerAdapter_ProductsForNewOrder(applicationContext, allProducts.toTypedArray())
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
    }

    fun completeOrder(view: View) {
        var price = 0
        for (i in cart){
            price += i.prod_price*i.qty
        }
        if ( price != 0) {
            val dialogOrder: Dialog = Dialog(this)
            dialogOrder.setContentView(R.layout.dialog_order_details)

            dialogOrder.findViewById<TextView>(R.id.dlg_txt_order_total).text = price.toString()

            val orderNames = dialogOrder.findViewById<TextView>(R.id.dlg_txt_order_names)
            val orderPrices = dialogOrder.findViewById<TextView>(R.id.dlg_txt_order_prices)
            val orderQty = dialogOrder.findViewById<TextView>(R.id.dlg_txt_order_qty)
            for (i in cart) {
                orderNames.text = orderNames.text.toString() + "\n" + i.prod_name
                orderPrices.text = orderPrices.text.toString() + "\n" + i.prod_price.toString()
                orderQty.text = orderQty.text.toString() + "\n" + i.qty.toString()
            }

//          Show another dialog that will take customer's name and contact etc
            val dialogCustomer: Dialog = Dialog(this)
            dialogCustomer.setContentView(R.layout.dialog_customer_details)

            val customerName = dialogCustomer.findViewById<EditText>(R.id.editTextTextPersonName).text
            val customerContact = dialogCustomer.findViewById<EditText>(R.id.editTextTextPersonContact).text

            dialogCustomer.findViewById<Button>(R.id.btnOk).setOnClickListener {
                if (customerName.toString() != "") {
                    dialogOrder.findViewById<TextView>(R.id.dlg_txt_cus_name).text = "${customerName.toString()} - ${customerContact.toString()}"

                    dialogCustomer.dismiss()
                    dialogOrder.show()
                    DatabaseHelper(this).addOrder(customerName.toString(), customerContact.toString(),cart)

                }
            }
            dialogCustomer.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialogCustomer.dismiss()
            }
            dialogCustomer.show()

        }
    }

    override fun onBackPressed() {
        cart = ArrayList<OrderItems>()

        super.onBackPressed()
    }

}
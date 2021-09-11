package com.example.restaurantorderapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.core.database.getBlobOrNull
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

const val DATABASE_NAME = "restaurant.db"

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 3) {
    private val db: SQLiteDatabase = this.writableDatabase
    private val cv = ContentValues()

    val ctx = context

    val database = FirebaseDatabase.getInstance("https://restaurantorderapp-1ddcd-default-rtdb.firebaseio.com/")

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE products (id INTEGER PRIMARY KEY, name TEXT, " +
                    "short_desc TEXT, full_desc TEXT, category TEXT, price INTEGER, image BLOB)"
        )
        database.execSQL(
            "CREATE TABLE orders (id INTEGER PRIMARY KEY, name TEXT, contact TEXT, items TEXT)"
        )
    }
    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            database.execSQL("DROP TABLE IF EXISTS products")
            database.execSQL("DROP TABLE IF EXISTS orders")
            onCreate(database)
        }
    }
    fun insertProduct(product: Product): Boolean {

        cv.put("name", product.name)
        cv.put("category", product.category)
        cv.put("price", product.price)
        cv.put("short_desc", product.short_desc)
        cv.put("full_desc", product.full_desc)
        cv.put("image", product.image)

        addProduct(product)

        return db.insert("products", null, cv) != (-1).toLong()
    }
    fun updateProduct(id: Int?, product: Product): Boolean {
        cv.put("name", product.name)
        cv.put("short_desc", product.short_desc)
        cv.put("full_desc", product.full_desc)
        cv.put("category", product.category)
        cv.put("price", product.price)
        cv.put("image", product.image)
        return db.update("products", cv, "id = ? ", arrayOf((id!!).toString())) == 1
    }
    fun deleteProduct(id: Int?): Int {
        return db.delete("products", "id = ? ", arrayOf((id!!).toString()))
    }
    fun getAllProducts(): ArrayList<Product> {
        val product_list = ArrayList<Product>()

        val cur: Cursor = db.rawQuery("select * from products", null)
        cur.moveToFirst()
        while (!cur.isAfterLast) {
            product_list.add(
                Product(
                    cur.getInt(cur.getColumnIndex("id")),
                    cur.getString(cur.getColumnIndex("name")),
                    cur.getString(cur.getColumnIndex("short_desc")),
                    cur.getString(cur.getColumnIndex("full_desc")),
                    cur.getString(cur.getColumnIndex("category")),
                    cur.getInt(cur.getColumnIndex("price")),
                    cur.getBlobOrNull(cur.getColumnIndex("image"))
                )
            )
            cur.moveToNext()
        }
        return product_list
    }
//  Firebase
    fun addProduct(product: Product){
        var myRef = database.getReference("products/${product.id}/name")
        myRef.setValue(product.name)
        myRef = database.getReference("products/${product.id}/category")
        myRef.setValue(product.category)
        myRef = database.getReference("products/${product.id}/short_desc")
        myRef.setValue(product.short_desc)
        myRef = database.getReference("products/${product.id}/full_desc")
        myRef.setValue(product.full_desc)
        myRef = database.getReference("products/${product.id}/price")
        myRef.setValue(product.price)
    }
    fun addOrder(name: String, contact: String, orderItems: ArrayList<OrderItems>) {
        val path = "$name-$contact"
        var myRef = database.getReference("orders/$path")
        myRef.setValue(Order(name, contact,324, orderItems))

        cv.put("name", name)
        cv.put("contact", contact)
        cv.put("items", orderItems.map { it.prod_id }.toString())
        if (db.insert("products", null, cv) != (-1).toLong())
            Toast.makeText(ctx, "Order Created Successfully!! (:", Toast.LENGTH_SHORT).show()
    }
    fun getAllOrders() : ArrayList<Order>{
        var orders = ArrayList<Order>()
        val orderItems = ArrayList<OrderItems>()

        // Dummy Data - Fetch Data from database here
        orderItems.add(OrderItems(1,"Pizza",23, 1))
        orderItems.add(OrderItems(1,"Biryani",103, 1))
        orderItems.add(OrderItems(1,"Korma",23, 2))
        orderItems.add(OrderItems(1,"Pratha",43, 1))
        orders.add(Order("ahsan", "372864723",345, orderItems ))
        orders.add(Order("hasaan", "387246732",450,orderItems ))
        orders.add(Order("Muneeb", "238947332",324,orderItems ))
        orders.add(Order("danish", "238947332",456, orderItems ))
        orders.add(Order("danial", "238947332",234, orderItems ))
        orders.add(Order("AbdulRehman", "238947332",234,orderItems ))
        orders.add(Order("bilal", "372864723",678, orderItems ))
        orders.add(Order("mohsin", "372864723",923, orderItems ))

        return orders
    }
}
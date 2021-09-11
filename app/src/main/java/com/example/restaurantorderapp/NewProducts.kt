package com.example.restaurantorderapp

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream


class NewProducts : AppCompatActivity() {

    lateinit var viewImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Add New Products"
        setContentView(R.layout.activity_new_products)

        viewImage = findViewById(R.id.imageView)
    }

    fun addDish(view: View) {

        val name = findViewById<EditText>(R.id.edt_dish_name_add).text.toString()
        val short_desc = findViewById<EditText>(R.id.edt_short_desc_add).text.toString()
        val category = findViewById<EditText>(R.id.edt_dish_category_add).text.toString()
        val price = findViewById<EditText>(R.id.edt_dish_price_add).text.toString()
        val full_desc = findViewById<EditText>(R.id.edt_full_desc_add).text.toString()
        val image = findViewById<ImageView>(R.id.imageView).drawable

        val bitmap = (image as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        if( name != "" && short_desc != "" && category != "" && full_desc != "" && price != "") {
            val obj = DatabaseHelper(this)
            val product = Product(0, name, short_desc, full_desc, category, price.toInt(), stream.toByteArray())
            val res = obj.insertProduct(product)
            if (res)
                Toast.makeText(this, "New Product Added Successfully", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Failed to Add New Product", Toast.LENGTH_SHORT).show()
            onBackPressed()
        } else
            Toast.makeText(this, "Some Fields are Missing", Toast.LENGTH_LONG).show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.choose_source, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val selectedImage = data?.data
            val filePath = arrayOf(MediaStore.Images.Media.DATA)
            val cur: Cursor? = contentResolver.query(selectedImage!!, filePath, null, null, null)
            if (cur != null) {
                cur.moveToFirst()

                val columnIndex: Int = cur.getColumnIndex(filePath[0])
                val picturePath: String = cur.getString(columnIndex)
                cur.close()
                val thumbnail = BitmapFactory.decodeFile(picturePath)
                Log.w("Path:", picturePath + "")
                findViewById<ImageView>(R.id.imageView).setImageBitmap(thumbnail)
                Toast.makeText(this, "$thumbnail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun selectImage(view: View) {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Add Photo!")
        builder.setItems(options) { dialog, item ->
            if( options[item] == "Cancel" )
                dialog.dismiss()
            else {
                Toast.makeText(this, "Following Permission not found:\nandroid.hardware.camera.autofocus", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 1)
            }
        }
        builder.show()
    }
}
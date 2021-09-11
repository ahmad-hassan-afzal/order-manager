package com.example.restaurantorderapp

import android.app.AlertDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream


class UpdateProducts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Manage Products"

        setContentView(R.layout.activity_update_products)

        val bundle = intent.extras

        // setting Data in Fields from bundle passed by Product_Card_Recyclerview
        findViewById<TextView>(R.id.product_id_modify).text = bundle!!.getString("id")
        findViewById<EditText>(R.id.edt_dish_name_modify).setText(bundle.getString("name"))
        findViewById<EditText>(R.id.edt_short_desc_modify).setText(bundle.getString("shortDesc"))
        findViewById<EditText>(R.id.edt_full_desc_modify).setText(bundle.getString("fullDesc"))
        findViewById<EditText>(R.id.edt_dish_price_modify).setText(bundle.getString("price"))
        findViewById<EditText>(R.id.edt_dish_category_modify).setText(bundle.getString("category"))

        val img = BitmapFactory.decodeByteArray(bundle.getByteArray("image"), 0, bundle.getByteArray("image")!!.size )
        findViewById<ImageView>(R.id.dish_thumb_modify).setImageBitmap(img)

    }

    fun updateDish(view: View) {

        val id = findViewById<TextView>(R.id.product_id_modify).text.toString().toInt()
        val name = findViewById<EditText>(R.id.edt_dish_name_modify).text.toString()
        val short_desc = findViewById<EditText>(R.id.edt_short_desc_modify).text.toString()
        val category = findViewById<EditText>(R.id.edt_dish_category_modify).text.toString()
        val price = findViewById<EditText>(R.id.edt_dish_price_modify).text.toString().toInt()
        val full_desc = findViewById<EditText>(R.id.edt_full_desc_modify).text.toString()

        val image = findViewById<ImageView>(R.id.dish_thumb_modify).drawable

        val bitmap = (image as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

        val obj = DatabaseHelper(this)
        val product = Product(id, name, short_desc, full_desc, category, price, stream.toByteArray())

        val res = obj.updateProduct(id,product)

        if (res) {
            onBackPressed()
            Toast.makeText(this, "Product Update Successfull", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(this, "Product Update Failed", Toast.LENGTH_SHORT).show()
    }
    fun deleteDish(view: View) {

        val id = findViewById<TextView>(R.id.product_id_modify).text.toString().toInt()
        val obj = DatabaseHelper(this)
        val res = obj.deleteProduct(id)

        if (res > 0) {
            onBackPressed()
            Toast.makeText(this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(this, "Product Deletion Failed", Toast.LENGTH_SHORT).show()

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
                findViewById<ImageView>(R.id.dish_thumb_modify).setImageBitmap(thumbnail)
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
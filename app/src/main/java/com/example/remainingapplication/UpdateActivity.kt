package com.example.remainingapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class UpdateActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    lateinit var id_str: String
    lateinit var title_str: String
    lateinit var discription_str: String
    lateinit var price_str: String
    lateinit var date_str: String

    lateinit var title: TextInputEditText
    lateinit var discription: TextInputEditText
    lateinit var price: TextInputEditText
    lateinit var date: TextInputEditText

    lateinit var UpdateBtn: Button
    lateinit var DeleteBtn: Button

    private val calendar = Calendar.getInstance()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        databaseHelper = DatabaseHelper(this)

        title = findViewById(R.id.Update_title_text)
        discription = findViewById(R.id.Update_discription_text)
        price = findViewById(R.id.Update_price_text)
        date = findViewById(R.id.Update_date_text)

        UpdateBtn = findViewById(R.id.Update_Button)
        DeleteBtn = findViewById(R.id.Delete_Button)

        getAndSetIntentData()

        // Update Button click
        UpdateBtn.setOnClickListener {
            val myDB = DatabaseHelper(this@UpdateActivity)
            title_str = title.text.toString().trim()
            discription_str = discription.text.toString().trim()
            price_str = price.text.toString().trim()
            date_str = date.text.toString().trim()

            myDB.updateData(id_str, title_str, discription_str, price_str, date_str)
            finish()
        }

        // Delete Button click
        DeleteBtn.setOnClickListener {
            confirmDialog()
        }

        // Date field click: show DatePickerDialog
        date.setOnClickListener {
            showDatePicker()
        }

        // Setup Toolbar
        val toolbar2 = findViewById<Toolbar>(R.id.toolbar2)
        setSupportActionBar(toolbar2)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getAndSetIntentData() {
        val intent = intent
        if (
            intent.hasExtra("id") &&
            intent.hasExtra("title") &&
            intent.hasExtra("discription") &&
            intent.hasExtra("price") &&
            intent.hasExtra("date")
        ) {
            // Getting Data from Intent
            id_str = intent.getStringExtra("id").toString()
            title_str = intent.getStringExtra("title").toString()
            discription_str = intent.getStringExtra("discription").toString()
            price_str = intent.getStringExtra("price").toString()
            date_str = intent.getStringExtra("date").toString()

            // Setting Intent Data
            title.setText(title_str)
            discription.setText(discription_str)
            price.setText(price_str)
            date.setText(date_str)
        } else {
            Toast.makeText(this, "No DATA :((", Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete $title_str ?")
        builder.setMessage("Are you sure you want to delete $title_str ?")
        builder.setPositiveButton("Yes") { _, _ ->
            val myDB = DatabaseHelper(this@UpdateActivity)
            myDB.deleteOneRowData(id_str)
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
    private fun showDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)

            // Format selected date as "yyyy-MM-dd"
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            date.setText(sdf.format(calendar.time))
        }

        DatePickerDialog(
            this,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}
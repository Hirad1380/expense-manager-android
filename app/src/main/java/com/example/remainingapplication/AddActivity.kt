package com.example.remainingapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        databaseHelper = DatabaseHelper(this)

        val title: TextInputEditText = findViewById(R.id.Title_text);
        val discription: TextInputEditText  = findViewById(R.id.Discription_text);
        val price: TextInputEditText  = findViewById(R.id.Price_text);
        val date: TextInputEditText  = findViewById(R.id.Date_text);
        val AddBtn: Button = findViewById(R.id.Add_Button);

        date.setOnClickListener {
            showDatePickerDialog(date)
        }

        AddBtn.setOnClickListener {
            val myDB = DatabaseHelper(this@AddActivity)
            myDB.addData(
                title.text.toString().trim(),
                discription.text.toString().trim(),
                price.text.toString().trim().toInt(),
                date.text.toString().trim()
            )
        }


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun showDatePickerDialog(textView: TextView) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Month is 0-based, so add 1
                val cal = Calendar.getInstance()
                cal.set(selectedYear, selectedMonth, selectedDay)

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = sdf.format(cal.time)

                textView.text = formattedDate  // ✅ Set the formatted date in the TextView
            },
            year, month, day
        )

        datePickerDialog.show()
    }

}
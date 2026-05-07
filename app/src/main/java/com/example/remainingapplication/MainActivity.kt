package com.example.remainingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.remainingapplication.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    lateinit var dataId: ArrayList<String>
    lateinit var dataTitle: ArrayList<String>
    lateinit var dataDescription: ArrayList<String>
    lateinit var dataPrice: ArrayList<String>
    lateinit var dataDate: ArrayList<String>

    lateinit var customAdapter: CustomAdapter

    lateinit var empty_image: ImageView;
    lateinit var text_emptyImage: TextView;

    lateinit var FloatingBtn: FloatingActionButton;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        empty_image = findViewById(R.id.add_imageView)
        text_emptyImage = findViewById(R.id.text_imageView)
        FloatingBtn = findViewById(R.id.Add_Button)

        val toolbar = findViewById<Toolbar>(R.id.toolbar3)
        setSupportActionBar(toolbar)

        // Set title
        supportActionBar?.title = "Main Activity"

        databaseHelper = DatabaseHelper(this)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview);
        binding.AddButton.setOnClickListener(){
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        dataId = ArrayList()
        dataTitle = ArrayList()
        dataDescription = ArrayList()
        dataPrice = ArrayList()
        dataDate = ArrayList()

        storeDataInArray();


        customAdapter = CustomAdapter(
            this,
            ArrayList(dataId),
            ArrayList(dataTitle),
            ArrayList(dataDescription),
            ArrayList(dataPrice),
            ArrayList(dataDate)
        )
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    // reloads activity to reflect updated DB
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            recreate()
        }
    }

    fun storeDataInArray() {
        val cursor = databaseHelper.readAllData()
        if (cursor == null || cursor.count == 0) {
            empty_image.setVisibility(View.VISIBLE);
            text_emptyImage.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                dataId.add(cursor.getString(0))
                dataTitle.add(cursor.getString(1))
                dataDescription.add(cursor.getString(2))
                dataPrice.add(cursor.getString(3))
                dataDate.add(cursor.getString(4))
            }
            cursor.close()
            empty_image.setVisibility(View.GONE);
            text_emptyImage.setVisibility(View.GONE);
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as? SearchView

        val searchEditText = searchView?.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchEditText?.setTextColor(ContextCompat.getColor(this, R.color.black))
        searchEditText?.setHintTextColor(ContextCompat.getColor(this, R.color.black))
        searchEditText?.setBackgroundColor(ContextCompat.getColor(this, R.color.dark_blue))

        searchView?.queryHint = "Search ...."

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                customAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                customAdapter.filter.filter(newText)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.toggle_theme -> {
                val currentNightMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
                if (currentNightMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                return true
            }
            R.id.delete_all -> {
                confirmDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete All ?")
        builder.setMessage("Are you sure you want to delete all Data ?")

        builder.setPositiveButton("Yes") { _, _ ->
            val myDB = DatabaseHelper(this@MainActivity)
            myDB.deleteAllData();

            // Refresh Activity
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
            finish();
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // optional
        }

        builder.create().show()
    }
}
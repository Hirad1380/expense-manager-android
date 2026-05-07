package com.example.remainingapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
class CustomAdapter(
    var context: Context,
    var dataId: ArrayList<String>,
    var dataTitle: ArrayList<String>,
    var dataDescription: ArrayList<String>,
    var dataPrice: ArrayList<String>,
    var dataDate: ArrayList<String>
) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>(), Filterable {

    private var fullDataTitle = ArrayList<String>(dataTitle)
    private var fullDataId = ArrayList<String>(dataId)
    private var fullDataDescription = ArrayList<String>(dataDescription)
    private var fullDataPrice = ArrayList<String>(dataPrice)
    private var fullDataDate = ArrayList<String>(dataDate)

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainLayout: LinearLayout = itemView.findViewById(R.id.mainLayout)
        val id: TextView = itemView.findViewById(R.id.Data_Id_txt)
        val title: TextView = itemView.findViewById(R.id.Data_title_txt)
        val discription: TextView = itemView.findViewById(R.id.Data_discription_txt)
        val price: TextView = itemView.findViewById(R.id.Data_price_txt)
        val date: TextView = itemView.findViewById(R.id.Data_date_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = dataTitle.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text = dataId[position]
        holder.title.text = dataTitle[position]
        holder.discription.text = dataDescription[position]
        holder.price.text = dataPrice[position]
        holder.date.text = dataDate[position]

        holder.mainLayout.setOnClickListener {
            val intent = Intent(context, UpdateActivity::class.java).apply {
                putExtra("id", dataId[position])
                putExtra("title", dataTitle[position])
                putExtra("discription", dataDescription[position])
                putExtra("price", dataPrice[position])
                putExtra("date", dataDate[position])
            }
            (context as Activity).startActivityForResult(intent, 1)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredTitles = ArrayList<String>()
                val filteredIds = ArrayList<String>()
                val filteredDescriptions = ArrayList<String>()
                val filteredPrices = ArrayList<String>()
                val filteredDates = ArrayList<String>()

                if (constraint.isNullOrEmpty()) {
                    filteredTitles.addAll(fullDataTitle)
                    filteredIds.addAll(fullDataId)
                    filteredDescriptions.addAll(fullDataDescription)
                    filteredPrices.addAll(fullDataPrice)
                    filteredDates.addAll(fullDataDate)
                } else {
                    val filterPattern = constraint.toString().lowercase().trim()

                    for (i in fullDataTitle.indices) {
                        val title = fullDataTitle[i].lowercase()
                        val description = fullDataDescription[i].lowercase()
                        val price = fullDataPrice[i].lowercase()
                        val date = fullDataDate[i].lowercase()

                        if (title.contains(filterPattern) || description.contains(filterPattern) || price.contains(filterPattern) || date.contains(filterPattern)) {
                            filteredTitles.add(fullDataTitle[i])
                            filteredIds.add(fullDataId[i])
                            filteredDescriptions.add(fullDataDescription[i])
                            filteredPrices.add(fullDataPrice[i])
                            filteredDates.add(fullDataDate[i])
                        }
                    }
                }

                val results = FilterResults()
                results.values = listOf(filteredIds, filteredTitles, filteredDescriptions, filteredPrices, filteredDates)
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredList = results?.values as? List<ArrayList<String>>
                if (filteredList != null && filteredList.size == 5) {
                    dataId.clear()
                    dataTitle.clear()
                    dataDescription.clear()
                    dataPrice.clear()
                    dataDate.clear()

                    dataId.addAll(filteredList[0])
                    dataTitle.addAll(filteredList[1])
                    dataDescription.addAll(filteredList[2])
                    dataPrice.addAll(filteredList[3])
                    dataDate.addAll(filteredList[4])

                    notifyDataSetChanged()
                }
            }
        }
    }
}
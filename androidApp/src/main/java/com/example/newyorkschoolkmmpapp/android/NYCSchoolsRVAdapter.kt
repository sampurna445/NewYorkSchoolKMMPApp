package com.example.newyorkschoolkmmpapp.android

import SchoolsListData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class NYCSchoolsRVAdapter(
    var schools: List<SchoolsListData>, val onClick: (String) -> Unit
) : RecyclerView.Adapter<NYCSchoolsRVAdapter.NYCSchoolsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NYCSchoolsViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_schools, parent, false)
            .run(::NYCSchoolsViewHolder)
    }

    override fun getItemCount(): Int = schools.count()

    override fun onBindViewHolder(holder: NYCSchoolsViewHolder, position: Int) {
        holder.bindData(schools[position]) { schoolName -> onClick.invoke(schoolName) }
    }

    inner class NYCSchoolsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val schoolNameTextView = itemView.findViewById<TextView>(R.id.school_name)
        private val cityTextView = itemView.findViewById<TextView>(R.id.cityName)
        private val dbnTextView = itemView.findViewById<TextView>(R.id.dbn)


        fun bindData(school: SchoolsListData, onClick: (String) -> Unit) {
            val ctx = itemView.context
            dbnTextView.text = school.dbn
            schoolNameTextView.text = ctx.getString(R.string.school_name, school.schoolName)
            cityTextView.text = ctx.getString(R.string.city, school.city)
            itemView.setOnClickListener { onClick(school.schoolName.toString()) }
        }
    }
}

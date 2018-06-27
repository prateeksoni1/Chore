package com.example.prateek.chore.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.prateek.chore.R
import com.example.prateek.chore.model.Chore

class ChoresListAdapter(var context: Context, var choresList: ArrayList<Chore>) : RecyclerView.Adapter<ChoresListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(holder != null)
            holder.bindView(choresList[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{

        var view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return choresList.count()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var choreName = itemView.findViewById<TextView>(R.id.nameTxt)
        var choreAssBy = itemView.findViewById<TextView>(R.id.assignedbyTxt)
        var choreDate = itemView.findViewById<TextView>(R.id.choreDate)
        var choreAssTo = itemView.findViewById<TextView>(R.id.assignedtoTxt)


        fun bindView(chore: Chore) {
            choreName.text = chore.name
            choreAssBy.text = "By: " + chore.assBy
            choreAssTo.text = "To: " + chore.assTo
            choreDate.text = chore.showHumanDate(chore.time!!)
        }

    }
}
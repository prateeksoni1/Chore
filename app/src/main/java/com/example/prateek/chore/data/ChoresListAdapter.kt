package com.example.prateek.chore.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.prateek.chore.R
import com.example.prateek.chore.model.Chore

class ChoresListAdapter(var context: Context, var choresList: ArrayList<Chore>) : RecyclerView.Adapter<ChoresListAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.bindView(choresList[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{

        var view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return choresList.count()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var choreName = itemView.findViewById<TextView>(R.id.nameTxt)
        var choreAssBy = itemView.findViewById<TextView>(R.id.assignedbyTxt)
        var choreDate = itemView.findViewById<TextView>(R.id.choreDate)
        var choreAssTo = itemView.findViewById<TextView>(R.id.assignedtoTxt)
        var choreDelete = itemView.findViewById<Button>(R.id.deletebtn)
        var choreEdit = itemView.findViewById<Button>(R.id.editbtn)

        override fun onClick(v: View?) {

            var pos = adapterPosition
            var clone = choresList[pos]

            when (v!!.id) {
                choreDelete.id -> {
                    deleteChore(clone.id!!)
                    choresList.removeAt(pos)
                    notifyItemRemoved(pos)

                }
                choreEdit.id -> {
                    Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
                }


            }

        }

        fun deleteChore(id: Int) {
            var handler = ChoresDatabaseHandler(context)
            handler.deleteChore(id)
        }

        fun bindView(chore: Chore) {
            choreName.text = chore.name
            choreAssBy.text = "By: " + chore.assBy
            choreAssTo.text = "To: " + chore.assTo
            choreDate.text = chore.showHumanDate(chore.time!!)



            choreDelete.setOnClickListener(this)
            choreEdit.setOnClickListener(this)
        }

    }
}
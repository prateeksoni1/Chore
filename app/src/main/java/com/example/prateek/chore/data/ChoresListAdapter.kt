package com.example.prateek.chore.data

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.prateek.chore.R
import com.example.prateek.chore.activity.ChoreListActivity
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
                    editChore(clone)

                }


            }

        }

        fun deleteChore(id: Int) {
            var handler = ChoresDatabaseHandler(context)
            handler.deleteChore(id)
        }

        fun editChore(chore: Chore) {

            lateinit var dialog: AlertDialog
            lateinit var dialogBuilder: AlertDialog.Builder
            var handler = ChoresDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.dialog, null)
            var choreName = view.findViewById<TextView>(R.id.dialogChoreName)
            var choreAssTo = view.findViewById<TextView>(R.id.dialogAssToTxt)
            var choreAssby = view.findViewById<TextView>(R.id.dialogAssByTxt)
            var savebtn = view.findViewById<TextView>(R.id.dialogSavebtn)

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder.create()
            dialog.show()

            savebtn.setOnClickListener {
                if(choreName.text.isNotEmpty() && choreAssTo.text.isNotEmpty() && choreAssby.text.isNotEmpty()) {

                    chore.name = choreName.text.toString()
                    chore.assBy = choreAssby.text.toString()
                    chore.assTo = choreAssTo.text.toString()
                    handler.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore)
                    dialog.dismiss()

                }
            }
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
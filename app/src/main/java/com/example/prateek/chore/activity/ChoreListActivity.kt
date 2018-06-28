package com.example.prateek.chore.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prateek.chore.R
import com.example.prateek.chore.data.ChoresDatabaseHandler
import com.example.prateek.chore.data.ChoresListAdapter
import com.example.prateek.chore.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*

class ChoreListActivity : AppCompatActivity() {

    lateinit var adapter: ChoresListAdapter
    lateinit var choreList: ArrayList<Chore>
    lateinit var handler: ChoresDatabaseHandler
    lateinit var dialog: AlertDialog
    lateinit var dialogBuilder: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)



        handler = ChoresDatabaseHandler(this)
        choreList = ArrayList<Chore>()
        choreList = handler.readChores()
        choreList.reverse()

        adapter = ChoresListAdapter(this, choreList)

        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if(item!!.itemId == R.id.menu_addbtn) {
            Log.d("MENU", "Clicked add button")
            createDialog()
        }
        return super.onOptionsItemSelected(item)

    }

    fun createDialog() {
        var view = LayoutInflater.from(this).inflate(R.layout.dialog, null)
        var choreName = view.findViewById<TextView>(R.id.dialogChoreName)
        var choreAssTo = view.findViewById<TextView>(R.id.dialogAssToTxt)
        var choreAssby = view.findViewById<TextView>(R.id.dialogAssByTxt)
        var savebtn = view.findViewById<TextView>(R.id.dialogSavebtn)

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder.create()
        dialog.show()

        savebtn.setOnClickListener {
            if(choreName.text.isNotEmpty() && choreAssTo.text.isNotEmpty() && choreAssby.text.isNotEmpty()) {
                var chore = Chore()
                chore.name = choreName.text.toString()
                chore.assBy = choreAssby.text.toString()
                chore.assTo = choreAssTo.text.toString()
                handler.createChore(chore)
                dialog.dismiss()
                startActivity(Intent(this, ChoreListActivity::class.java))
                finish()
            }
        }
    }
}

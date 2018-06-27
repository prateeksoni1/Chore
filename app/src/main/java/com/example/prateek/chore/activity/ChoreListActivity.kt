package com.example.prateek.chore.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)



        handler = ChoresDatabaseHandler(this)
        choreList = ArrayList<Chore>()
        choreList = handler.readChores()

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
        }
        return super.onOptionsItemSelected(item)

    }
}

package com.example.prateek.chore.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.prateek.chore.R
import com.example.prateek.chore.data.ChoresDatabaseHandler
import com.example.prateek.chore.data.ChoresListAdapter
import com.example.prateek.chore.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var choresDatabaseHandler: ChoresDatabaseHandler


//    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.INVISIBLE
        progresstext.visibility = View.INVISIBLE
//        progressDialog = ProgressDialog(this)


        choresDatabaseHandler = ChoresDatabaseHandler(this)

        button.setOnClickListener() {
            if(choreName.text.isNotEmpty() && asstotxt.text.isNotEmpty() && assbytxt.text.isNotEmpty()) {

                progressBar.visibility = View.VISIBLE
                progresstext.visibility = View.VISIBLE
                button.isEnabled = false
//                progressDialog!!.setMessage("Saving...")
//                progressDialog!!.show()
                var chore = Chore()
                chore.name = choreName.text.toString()
                chore.assBy = assbytxt.text.toString()
                chore.assTo = asstotxt.text.toString()

                choresDatabaseHandler.createChore(chore)

//                progressDialog!!.cancel()


                var chores = choresDatabaseHandler.readChores()
        //
                for(c in chores) {
                    Log.d("DB ITEM: ", "Name: ${c.name}, Time: ${c.time}")
                }

                progressBar.visibility = View.INVISIBLE
                progresstext.visibility = View.INVISIBLE

                startActivity(Intent(this, ChoreListActivity::class.java))
            } else {
                Toast.makeText(this, "Fill it bish", Toast.LENGTH_SHORT).show()
            }
        }



//        var chore = Chore()
//        chore.name = "DieHard"
//        chore.assTo = "Pro"
//        chore.assBy = "Pro"
//
//
//        //choresDatabaseHandler.createChore(chore)
//
//        var chores = choresDatabaseHandler.readChores()
//
//        for(c in chores) {
//            Log.d("DB ITEM: ", "Name: ${c.name}, Time: ${c.time}")
//        }

//        var chores = choresDatabaseHandler.readAChore(2)
//        Log.d("CHORE", "ID: ${chores.id.toString()}, Name: ${chores.name}, Time: ${chores.time.toString()}")
    }
}

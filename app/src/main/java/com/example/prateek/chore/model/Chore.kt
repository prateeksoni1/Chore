package com.example.prateek.chore.model

import java.text.DateFormat
import java.util.*

class Chore (){

    var id: Int? = null
    var name: String? = null
    var assTo: String? = null
    var assBy: String? = null
    var time: Long? = null

    constructor(id: Int, name: String, assTo: String, assBy: String, time: Long) : this() {
        this.id = id
        this.assBy = assBy
        this.assTo = assTo
        this.name = name
        this.time = time
    }

    fun showHumanDate(time: Long) :  String{
        var dateFormat = DateFormat.getDateInstance()
        var formattedDate = dateFormat.format(Date(time).time)
        var time = "Date: $formattedDate"
        return time
    }

}
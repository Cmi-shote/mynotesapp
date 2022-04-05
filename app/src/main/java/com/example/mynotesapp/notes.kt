package com.example.mynotesapp

class notes(title: String?, date: String?, color: String?) {
    var mtitle : String? = title
    var mdate : String? = date
    var mcolor : String? = color

    fun getTitle() : String? {
        return mtitle
    }

    fun getDate() : String? {
        return mdate
    }

    fun getColor() : String? {
        return mcolor
    }
}
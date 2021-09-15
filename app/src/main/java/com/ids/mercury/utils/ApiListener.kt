package com.ids.mercury.utils


interface ApiListener {
    fun onDataRetrieved(success:Boolean,response: Any)
}
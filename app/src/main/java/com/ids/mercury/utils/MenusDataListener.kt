package com.ids.mercury.utils

import com.ids.mercury.model.response.ResponseMenus

interface MenusDataListener {
    fun onDataMenuRetrieved(success:Boolean,responseMenus: ResponseMenus)
}
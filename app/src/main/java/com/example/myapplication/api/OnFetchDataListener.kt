package com.example.myapplication.api

import com.example.myapplication.model.GitUser

interface OnFetchDataListener {

    fun onFetchDataSuccess(arrayList: ArrayList<GitUser>)

    fun onFetchDataError()

}

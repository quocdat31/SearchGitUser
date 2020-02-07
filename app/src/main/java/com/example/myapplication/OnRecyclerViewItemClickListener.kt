package com.example.myapplication

import com.example.myapplication.model.GitUser

interface OnRecyclerViewItemClickListener {

    fun onItemClick(gitUser: GitUser)

}
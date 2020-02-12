package com.example.myapplication.userdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity


class UserDetailPresenter(var context: Context, userDetailView: UserDetailContract.View) : UserDetailContract.Presenter {

    companion object {
        val USER_PER_PAGE = 20
    }

    init {
        userDetailView.presenter = this
    }

    override fun openRepoUrl(url: String) {

        val uri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)

    }


    override fun getUSer() {

    }

    override fun loadUserImage() {

    }

    override fun start() {
        getUSer()
    }

}


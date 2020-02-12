package com.example.myapplication.userdetail

import com.example.myapplication.base.BasePresenter
import com.example.myapplication.base.BaseView
import com.example.myapplication.model.GitUser

interface UserDetailContract {

    interface View : BaseView<Presenter> {

        fun showUserInfo(gitUser: GitUser)

        fun setLoadingProgress(isLoading: Boolean)

        fun showUserImage()

        fun onRepoButtonClick(url: String)
    }

    interface Presenter : BasePresenter {

        fun openRepoUrl(url: String)

        fun getUSer()

        fun loadUserImage()

    }

}
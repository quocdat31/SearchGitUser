package com.example.myapplication.userdetail

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.myapplication.GitUserAdapter
import com.example.myapplication.R
import com.example.myapplication.model.GitUser
import java.time.LocalDate

class UserDetailDialog(var gitUser: GitUser) : AppCompatDialogFragment(), UserDetailContract.View {


    override lateinit var presenter: UserDetailContract.Presenter
    lateinit var gitUserNameTextView: TextView
    lateinit var gitUserRepoTextView: TextView
    lateinit var progressBar: ProgressBar
    lateinit var openRepoButton: TextView
    lateinit var gitUserAvatarImageView: ImageView

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        presenter.start()

        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.gituser_detail_dialog, null)

        if (view != null) {
            initView(view)
        }

        showUserInfo(gitUser)
        showUserImage()

        openRepoButton.setOnClickListener {
            onRepoButtonClick(gitUser.reposUrl.toString())
        }

        return builder.setView(view).create()
    }


    private fun initView(view: View) {
        gitUserAvatarImageView = view.findViewById(R.id.gitUserAvatarImageView)
        gitUserNameTextView = view.findViewById(R.id.gitUserNameTextView)
        gitUserRepoTextView = view.findViewById(R.id.gitUserRepoTextView)
        progressBar = view.findViewById(R.id.loadingGitUserImageProgressBar)
        openRepoButton = view.findViewById(R.id.openRepoButton)
    }

    override fun showUserInfo(gitUser: GitUser) {
        gitUserNameTextView.text = gitUser.login
        gitUserRepoTextView.text = gitUser.reposUrl
    }

    override fun setLoadingProgress(isLoading: Boolean) {

        if (!isLoading) {
            gitUserAvatarImageView.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        } else {
            progressBar.visibility = View.VISIBLE
            gitUserAvatarImageView.visibility = View.GONE
        }

    }

    override fun showUserImage() {

        Glide.with(this).load(gitUser.avatarUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    setLoadingProgress(true)
                    gitUserAvatarImageView.setImageResource(R.drawable.ic_error_black_24dp)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    setLoadingProgress(false)
                    return false
                }

            })
            .into(gitUserAvatarImageView)
    }

    override fun onRepoButtonClick(url: String) {
        presenter.openRepoUrl(url)
    }
}

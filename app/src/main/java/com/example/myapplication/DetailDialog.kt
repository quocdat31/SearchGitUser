package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
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
import com.example.myapplication.model.GitUser

class DetailDialog(var gitUser: GitUser) : AppCompatDialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.gituser_detail_dialog, null)

        val gitUserAvatarImageView = view?.findViewById<ImageView>(R.id.gitUserAvatarImageView)
        val gitUserNameTextView = view?.findViewById<TextView>(R.id.gitUserNameTextView)
        val gitUserRepoTextView = view?.findViewById<TextView>(R.id.gitUserRepoTextView)
        val progressBar = view?.findViewById<ProgressBar>(R.id.loadingGitUserImageProgressBar)
        val openRepoButton = view?.findViewById<TextView>(R.id.openRepoButton)

        Glide.with(this).load(gitUser.avatarUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    gitUserAvatarImageView?.setImageResource(R.drawable.ic_error_black_24dp)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    gitUserAvatarImageView?.visibility = View.VISIBLE
                    progressBar?.visibility = View.GONE
                    return false
                }

            })
            .into(gitUserAvatarImageView!!)
        gitUserNameTextView?.text = gitUser.login
        gitUserRepoTextView?.text = gitUser.reposUrl

        openRepoButton?.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(gitUser.reposUrl)
            startActivity(openURL)
        }

        return builder.setView(view).create()

    }

}

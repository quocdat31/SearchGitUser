package com.example.myapplication

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.GitUser
import kotlinx.android.synthetic.main.gituser_item.view.*


class GitUserAdapter(
    private val items: ArrayList<GitUser>,
    private val context: Context,
    private val onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener
) :
    RecyclerView.Adapter<GitUserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemName = itemView.gitUserNameTextView
        val itemScore = itemView.gitUserScoreTextView
        val itemRepo = itemView.gitUserRepoTextView

        val expandButton = itemView.expandButton
        val itemCollapsedView = itemView.itemCollapsedView
        val itemExpandedView = itemView.expandableView
        val itemLayout = itemView.itemLayout

        fun bind(
            gitUser: GitUser,
            onRecyclerViewItemClickListener: OnRecyclerViewItemClickListener
        ) {
            itemCollapsedView.setOnClickListener {
                onRecyclerViewItemClickListener.onItemClick(gitUser)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.gituser_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            itemRepo.text = items[position].reposUrl
            itemScore.text = items[position].score.toString()
            itemName.text = items[position].login.toString()
        }

        val gitUser = items[position]
        holder.bind(gitUser, onRecyclerViewItemClickListener)

        holder.expandButton.setOnClickListener {

            if (holder.itemExpandedView.visibility == View.GONE) {
                holder.itemExpandedView.visibility = View.VISIBLE
                holder.expandButton.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp)
            } else {
                holder.itemExpandedView.visibility = View.GONE
                holder.expandButton.setImageResource(R.drawable.ic_expand_more_black_24dp)
            }

        }
    }

}

package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.api.FetchDataFromUrl
import com.example.myapplication.api.GitService
import com.example.myapplication.api.OnFetchDataListener
import com.example.myapplication.model.GitUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.searchEditText
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), OnFetchDataListener, OnRecyclerViewItemClickListener {

    private val mPageLimiter = 100
    private lateinit var progressDialog: ProgressDialog
    lateinit var dialogCreator: DialogCreator

    @SuppressLint("ShowToast")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        getUser()

        setSupportActionBar(toolBar)

        searchEditText.editText?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (isNetworkAvailable(this)) {
                    fetchGitUserData()
                } else {
                    dialogCreator = DialogCreator(
                        this,
                        getString(R.string.errorConnection),
                        "Try again"
                    ) { fetchGitUserData() }
                    dialogCreator.build()
                    if (isNetworkAvailable(this)) fetchGitUserData()
                }

            }
            false
        }
    }

    override fun onFetchDataSuccess(arrayList: ArrayList<GitUser>) {
        val adapter = GitUserAdapter(arrayList, this, this)
        if (adapter.itemCount == 0 && isNetworkAvailable(this)) {
            DialogCreator(
                this,
                "User ${searchEditText.editText?.text} not found, try different one",
                "Try different"
            ) {
                clearAndFocusEditText(searchEditText)

            }
                .build()
        }

        progressDialog.dismiss()
        gitUserRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        gitUserRecyclerView.adapter = adapter

    }

    override fun onFetchDataError() {

    }

    override fun onItemClick(gitUser: GitUser) {
        DetailDialog(gitUser).show(supportFragmentManager, null)
    }

    private fun fetchGitUserData() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("On searching")
        progressDialog.show()
        FetchDataFromUrl(this).execute("${App.API}search/users?per_page=$mPageLimiter&q=${searchEditText.editText?.text}")
    }

    fun getUser() {

        val service = GitService.create()

        service?.getGitUser(10,"asd")?.enqueue(object : Callback<List<GitUser>>{

            override fun onFailure(call: Call<List<GitUser>>, t: Throwable) {
                Log.d("asd","fail")
            }

            override fun onResponse(call: Call<List<GitUser>>, response: Response<List<GitUser>>) {
                Log.d("asd","$response")
            }

        })
    }
}

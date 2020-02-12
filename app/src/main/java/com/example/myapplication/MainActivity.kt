package com.example.myapplication

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.api.FetchDataFromUrl
import com.example.myapplication.api.NetworkManager
import com.example.myapplication.api.OnFetchDataListener
import com.example.myapplication.model.GitUser
import com.example.myapplication.model.Response
import com.example.myapplication.ultis.DialogCreator
import com.example.myapplication.ultis.clearAndFocusEditText
import com.example.myapplication.ultis.isNetworkAvailable
import com.example.myapplication.ultis.log
import com.example.myapplication.userdetail.UserDetailDialog
import com.example.myapplication.userdetail.UserDetailPresenter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.searchEditText


class MainActivity : AppCompatActivity(), OnFetchDataListener, OnRecyclerViewItemClickListener {

    private val mPageLimiter = 100
    private lateinit var progressDialog: ProgressDialog
    lateinit var dialogCreator: DialogCreator

    @SuppressLint("ShowToast")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(toolBar)

        searchEditText.editText?.setOnEditorActionListener { _, actionId, _ ->
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
        if (adapter.itemCount == 0 && isNetworkAvailable(
                this
            )
        ) {
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
        val detailDialog = UserDetailDialog(gitUser)
        UserDetailPresenter(this, detailDialog)

        detailDialog.show(supportFragmentManager, null)
    }

    @SuppressLint("CheckResult")
    private fun fetchGitUserData() {

        NetworkManager.create()
            .getUser("asd", 10)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { object : Observer<List<GitUser>>{
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: List<GitUser>) {
                    log(msg = t.size.toString())
                }


                override fun onError(e: Throwable) {

                }

            } }

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("searching")
        progressDialog.show()
        FetchDataFromUrl(this).execute("${App.API}search/users?per_page=$mPageLimiter&q=${searchEditText.editText?.text}")
    }
}

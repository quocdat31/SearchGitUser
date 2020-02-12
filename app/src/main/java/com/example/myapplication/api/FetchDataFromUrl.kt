package com.example.myapplication.api

import android.os.AsyncTask
import com.example.myapplication.model.GitUser
import com.example.myapplication.model.Response
import com.google.gson.Gson
import org.json.JSONException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class FetchDataFromUrl(var onFetchDataListener: OnFetchDataListener) :
    AsyncTask<String, Void, ArrayList<GitUser>>() {

    override fun doInBackground(vararg params: String?): ArrayList<GitUser>? {
        var list = ArrayList<GitUser>()
        try {
            val json = getJSONStringFromURL(params[0].toString())
            val userList: Response = Gson().fromJson(json, Response::class.java)

            list = userList.items as ArrayList<GitUser>

            } catch (e: IOException) {
            e.printStackTrace()
        }
        return list
    }

    override fun onPostExecute(result: ArrayList<GitUser>?) {
        if (result != null) {
            onFetchDataListener.onFetchDataSuccess(result)
        }
    }



    @Throws(IOException::class, JSONException::class)
    private fun getJSONStringFromURL(urlString: String): String? {
        val urlConnection: HttpURLConnection?
        val url = URL(urlString)

        urlConnection = url.openConnection() as HttpURLConnection
        return urlConnection.inputStream.bufferedReader().use { it.readText() }
    }

}

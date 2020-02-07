package com.example.myapplication.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("total_count")
    @Expose
    val totalCount: Int?,
    @SerializedName("incomplete_results")
    @Expose
    val incompleteResults: Boolean?,
    @SerializedName("items")
    @Expose
    val items: List<GitUser>
)

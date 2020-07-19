package com.bookstore.client.model.response.book

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookCategory(
    val code: String,
    val createdBy: String,
    val createdTime: String,
    val id: Int,
    val name: String,
    val updatedBy: String?,
    val updatedTime: String
) : Parcelable
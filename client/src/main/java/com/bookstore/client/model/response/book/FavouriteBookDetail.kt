package com.bookstore.model.response.book

import android.os.Parcelable
import com.bookstore.client.model.response.book.Book
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavouriteBookDetail(
    val bookModel: Book,
    val createdBy: String,
    val createdTime: String,
    val id: Int,
    val updatedBy: String,
    val updatedTime: String
) : Parcelable
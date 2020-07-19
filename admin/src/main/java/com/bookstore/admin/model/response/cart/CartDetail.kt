package com.bookstore.admin.model.response.cart

import android.os.Parcelable
import com.bookstore.admin.model.response.book.BookModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartDetail(
    val bookModelModel: BookModel,
    val cartDetailStatus: String,
    val createdBy: String?,
    val createdTime: String,
    val id: Int,
    val updatedBy: String?,
    val updatedTime: String
): Parcelable
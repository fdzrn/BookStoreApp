package com.bookstore.admin.model.response.transaction

import android.os.Parcelable
import com.bookstore.admin.model.response.book.BookModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransactionDetail(
    val bookModelModel: BookModel,
    val createdBy: String?,
    val createdTime: String,
    val id: Int,
    val price: Double,
    val updatedBy: String?,
    val updatedTime: String
): Parcelable
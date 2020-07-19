package com.bookstore.client.model.formatted.book

import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.model.response.book.FavouriteBook

data class FavouriteBookResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val favouriteBook: FavouriteBook? = null
)
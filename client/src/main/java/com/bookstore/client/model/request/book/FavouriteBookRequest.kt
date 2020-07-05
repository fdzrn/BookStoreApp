package com.bookstore.client.model.request.book

import com.bookstore.client.config.AppConfig

data class FavouriteBookRequest(
    val bookId: Int,
    val userId: Int = AppConfig.OAUTH_DEFAULT_CUSTOMER_ID
)
package com.bookstore.client.model.request.cart

import com.bookstore.client.config.AppConfig

data class CartRequest(
    val bookId: Int,
    val userId: Int = AppConfig.OAUTH_DEFAULT_CUSTOMER_ID
)
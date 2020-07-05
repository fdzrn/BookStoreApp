package com.bookstore.client.model.request.transaction

import com.bookstore.client.config.AppConfig

data class CheckoutRequest(
    val cartDetailIds: List<Int>,
    val userId: Int = AppConfig.OAUTH_DEFAULT_CUSTOMER_ID
)
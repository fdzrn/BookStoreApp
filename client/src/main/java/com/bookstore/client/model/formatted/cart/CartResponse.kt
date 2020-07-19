package com.bookstore.client.model.formatted.cart

import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.model.response.cart.Cart

data class CartResponse(
    val status: RetrofitStatus = RetrofitStatus.UNKNOWN,
    val cart: Cart? = null
)
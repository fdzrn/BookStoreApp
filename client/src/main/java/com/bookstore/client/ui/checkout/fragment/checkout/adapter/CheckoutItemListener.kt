package com.bookstore.client.ui.checkout.fragment.checkout.adapter

import com.bookstore.client.model.response.cart.CartDetail

interface CheckoutItemListener {
    fun onItemClick(cartDetail: CartDetail)
    fun onItemDraw(cartDetails : List<CartDetail>)
}
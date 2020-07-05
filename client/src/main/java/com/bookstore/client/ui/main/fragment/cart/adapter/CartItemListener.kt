package com.bookstore.client.ui.main.fragment.cart.adapter

import com.bookstore.client.model.response.cart.CartDetail

interface CartItemListener {
    fun onItemSearch(empty: Boolean)
    fun onItemClick(cartDetail: CartDetail)
    fun onItemRemove(position: Int, cartDetail: CartDetail)
    fun onItemDraw(cartDetails: List<CartDetail>)
}
package com.bookstore.client.ui.wishlist.adapter

import com.bookstore.client.model.response.book.Book

interface WishlistItemListener {
    fun onItemSearch(empty: Boolean)
    fun onItemClick(book:Book)
}
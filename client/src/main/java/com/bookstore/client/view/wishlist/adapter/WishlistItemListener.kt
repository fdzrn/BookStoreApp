package com.bookstore.client.view.wishlist.adapter

import com.bookstore.client.model.response.book.Book

interface WishlistItemListener {
    fun onItemSearch(empty: Boolean)
    fun onItemClick(book:Book)
}
package com.bookstore.client.ui.main.fragment.book.adapter

import com.bookstore.client.model.response.book.Book

interface BookItemListener {
    fun onItemClick(book: Book)
}
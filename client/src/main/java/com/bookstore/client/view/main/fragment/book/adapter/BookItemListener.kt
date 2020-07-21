package com.bookstore.client.view.main.fragment.book.adapter

import com.bookstore.client.model.response.book.Book

interface BookItemListener {
    fun onItemClick(book: Book)
}
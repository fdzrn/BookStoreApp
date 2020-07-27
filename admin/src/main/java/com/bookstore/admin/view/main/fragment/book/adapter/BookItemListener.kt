package com.bookstore.admin.view.main.fragment.book.adapter

import com.bookstore.admin.model.response.book.BookModel

interface BookItemListener {
    fun onItemSearch(empty: Boolean)
    fun onItemClick(book: BookModel)
    fun onItemDraw(books: List<BookModel>)
}
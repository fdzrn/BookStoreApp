package com.bookstore.client.ui.search.adapter

import com.bookstore.client.constant.BookType
import com.bookstore.client.model.response.book.Book

interface SearchBookItemListener {
    fun onItemSearch(empty: Boolean)
    fun onItemClick(book: Book)
    fun onFilterByType(bookTypes: List<BookType>)
}
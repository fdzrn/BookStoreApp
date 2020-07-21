package com.bookstore.client.view.search.adapter

import com.bookstore.client.constant.BookType
import com.bookstore.client.model.response.book.Book

interface SearchBookItemListener {
    fun onItemSearch(empty: Boolean)
    fun onItemClick(book: Book)
    fun onFilterByType(bookTypes: List<BookType>)
}
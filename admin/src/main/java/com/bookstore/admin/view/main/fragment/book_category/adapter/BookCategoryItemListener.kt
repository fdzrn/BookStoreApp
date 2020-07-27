package com.bookstore.admin.view.main.fragment.book_category.adapter

import com.bookstore.admin.model.response.book.BookCategory

interface BookCategoryItemListener {
    fun onItemSearch(empty: Boolean)
    fun onItemDraw(bookCategory: List<BookCategory>)
    fun onItemClick(bookCategories: BookCategory)
}
package com.bookstore

class BooksModel : ArrayList<BooksModelItem>()

data class BooksModelItem(
    val authorName: String,
    val bookCategory: BookCategory,
    val bookCategoryId: Int,
    val bookStatus: String,
    val createdBy: String,
    val createdTime: String,
    val id: Int,
    val imageUrl: String,
    val isbn: String,
    val price: Double,
    val publicationDate: String,
    val synopsis: String,
    val title: String,
    val updatedBy: String,
    val updatedTime: String
)

data class BookCategory(
    val code: String,
    val createdBy: String,
    val createdTime: String,
    val id: Int,
    val name: String,
    val updatedBy: Any,
    val updatedTime: String
)
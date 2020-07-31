package com.bookstore.admin.repository

import com.bookstore.admin.constant.BookStatus
import com.bookstore.admin.dao.remote.RemoteBookDAO
import com.bookstore.admin.model.request.book.AddBookCategoryRequest
import com.bookstore.admin.model.request.book.AddBookRequest
import com.bookstore.admin.model.request.book.UpdateBookCategoryRequest
import com.bookstore.admin.model.request.book.UpdateBookRequest
import com.bookstore.admin.model.response.book.BookCategory
import com.bookstore.admin.model.response.book.BookModel
import com.bookstore.admin.utils.SessionHelper
import com.bookstore.admin.utils.SessionHelper.asBearer
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import java.io.File

class BookRepository(
    private val remoteBookDAO: RemoteBookDAO,
    private val userRepository: UserRepository
) {
    suspend fun getBook(): List<BookModel> = userRepository.checkAccessToken().let {
        if (it != null) return remoteBookDAO.getBook(it.asBearer())
        else throw SessionHelper.unauthorizedException
    }

    fun getBookStatus() :List<BookStatus> = BookStatus.values().toList()

    suspend fun uploadImageBook(bookId: Int, image: File): Response<ResponseBody> =
        userRepository.checkAccessToken().let {
            val formattedImage = MultipartBody.Part.createFormData(
                "file",
                image.name,
                RequestBody.create(MediaType.parse("image/*"), image)
            )
            if (it != null) return remoteBookDAO.uploadImageBook(
                it.asBearer(),
                bookId,
                formattedImage
            )
            else throw SessionHelper.unauthorizedException
        }

    suspend fun addBook(addBookRequest: AddBookRequest): BookModel =
        userRepository.checkAccessToken().let {
            if (it != null) return remoteBookDAO.addBook(it.asBearer(), addBookRequest)
            else throw SessionHelper.unauthorizedException
        }

    suspend fun updateBook(updateBookRequest: UpdateBookRequest): Response<ResponseBody> =
        userRepository.checkAccessToken().let {
            if (it != null) return remoteBookDAO.updateBook(it.asBearer(), updateBookRequest)
            else throw  SessionHelper.unauthorizedException
        }

    suspend fun deleteBook(bookId: Int): Response<ResponseBody> =
        userRepository.checkAccessToken().let {
            if (it != null) return remoteBookDAO.deleteBook(it.asBearer(), bookId)
            else throw SessionHelper.unauthorizedException
        }

    suspend fun getBookCategory(): List<BookCategory> = userRepository.checkAccessToken().let {
        if (it != null) return remoteBookDAO.getBookCategory(it.asBearer())
        else throw SessionHelper.unauthorizedException
    }

    suspend fun addBookCategory(addBookCategoryRequest: AddBookCategoryRequest): Response<ResponseBody> =
        userRepository.checkAccessToken().let {
            if (it != null) return remoteBookDAO.addBookCategory(
                it.asBearer(),
                addBookCategoryRequest
            )
            else throw SessionHelper.unauthorizedException
        }

    suspend fun updateBookCategory(updateBookCategoryRequest: UpdateBookCategoryRequest): Response<ResponseBody> =
        userRepository.checkAccessToken().let {
            if (it != null) return remoteBookDAO.updateBookCategory(
                it.asBearer(),
                updateBookCategoryRequest
            )
            else throw SessionHelper.unauthorizedException
        }

    suspend fun deleteBookCategory(bookCategoryId: Int): Response<ResponseBody> =
        userRepository.checkAccessToken().let {
            if (it != null) return remoteBookDAO.deleteBookCategory(it.asBearer(), bookCategoryId)
            else throw SessionHelper.unauthorizedException
        }
}
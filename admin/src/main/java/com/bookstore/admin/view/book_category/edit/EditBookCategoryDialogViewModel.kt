package com.bookstore.admin.view.book_category.edit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.formated.book.DeleteBookCategoryResponse
import com.bookstore.admin.model.formated.book.UpdateBookCategoryResponse
import com.bookstore.admin.model.request.book.UpdateBookCategoryRequest
import com.bookstore.admin.repository.BookRepository
import com.bookstore.admin.utils.Retrofit.printRetrofitError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class EditBookCategoryDialogViewModel(
    application: Application,
    private val bookRepository: BookRepository
) : AndroidViewModel(application) {
    private val _updateBookCategoryResponse = MutableLiveData<UpdateBookCategoryResponse>()
    var updateBookCategoryResponse: LiveData<UpdateBookCategoryResponse> =
        _updateBookCategoryResponse

    fun updateBookCategory(updateBookCategoryRequest: UpdateBookCategoryRequest) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = bookRepository.updateBookCategory(updateBookCategoryRequest)
                if (result.isSuccessful)
                    _updateBookCategoryResponse.postValue(UpdateBookCategoryResponse(RetrofitStatus.SUCCESS))
                else {
                    _updateBookCategoryResponse.postValue(UpdateBookCategoryResponse(RetrofitStatus.FAILURE))
                    Log.e(this::class.java.simpleName, result.toString())
                }
            } catch (throwable: Throwable) {
                if (throwable is HttpException && throwable.code() == 401)
                    _updateBookCategoryResponse.postValue(UpdateBookCategoryResponse(RetrofitStatus.UNAUTHORIZED))
                else
                    _updateBookCategoryResponse.postValue(UpdateBookCategoryResponse(RetrofitStatus.EMPTY))
                throwable.printRetrofitError()
            }
        }

    private val _deleteBookCategoryResponse = MutableLiveData<DeleteBookCategoryResponse>()
    val deleteBookCategoryResponse: LiveData<DeleteBookCategoryResponse> =
        _deleteBookCategoryResponse

    fun deleteBookCategory(bookCategoryId: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val beforeDelete = bookRepository.getBookCategory().filter { it.id == bookCategoryId }
            if (beforeDelete.isNotEmpty()) {
                val deletedBookCategory = bookRepository.deleteBookCategory(bookCategoryId)
                if (deletedBookCategory.isSuccessful)
                    _deleteBookCategoryResponse.postValue(DeleteBookCategoryResponse(RetrofitStatus.SUCCESS))
                else {
                    _deleteBookCategoryResponse.postValue(DeleteBookCategoryResponse(RetrofitStatus.FAILURE))
                    Log.e(this::class.java.simpleName, deletedBookCategory.toString())
                }
            }
            else _deleteBookCategoryResponse.postValue(DeleteBookCategoryResponse(RetrofitStatus.CONSTRAINT_DETECTED))
        } catch (throwable: Throwable) {
            if (throwable is HttpException && throwable.code() == 401) _deleteBookCategoryResponse.postValue(
                DeleteBookCategoryResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _deleteBookCategoryResponse.postValue(DeleteBookCategoryResponse(RetrofitStatus.EMPTY))
            throwable.printRetrofitError()
        }
    }
}
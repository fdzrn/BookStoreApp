package com.bookstore.admin.view.book_category.add

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.formated.book.AddBookCategoryStatus
import com.bookstore.admin.model.request.book.AddBookCategoryRequest
import com.bookstore.admin.repository.BookRepository
import com.bookstore.admin.utils.Retrofit.printRetrofitError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AddBookCategoryDialogViewModel(
    application: Application,
    private val bookRepository: BookRepository
): AndroidViewModel(application) {
    private val _addBookCategoryResponse = MutableLiveData<AddBookCategoryStatus>()
    var addBookCategoryResponse : LiveData<AddBookCategoryStatus> = _addBookCategoryResponse

    fun addBookCategory(addBookCategoryRequest: AddBookCategoryRequest) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = bookRepository.addBookCategory(addBookCategoryRequest)
            if (result.isSuccessful)
                _addBookCategoryResponse.postValue(AddBookCategoryStatus(RetrofitStatus.SUCCESS))
            else{
                _addBookCategoryResponse.postValue(AddBookCategoryStatus(RetrofitStatus.FAILURE))
                Log.e(this::class.java.simpleName,result.toString())
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException && throwable.code() == 401)
                _addBookCategoryResponse.postValue(AddBookCategoryStatus(RetrofitStatus.UNAUTHORIZED))
            else
                _addBookCategoryResponse.postValue(AddBookCategoryStatus(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }
}
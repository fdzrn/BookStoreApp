package com.bookstore.admin.view.book.edit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.formated.book.BookCategoryResponse
import com.bookstore.admin.model.formated.book.DeleteBookResponse
import com.bookstore.admin.model.formated.book.UpdateBookResponse
import com.bookstore.admin.model.formated.book.UploadBookImageResponse
import com.bookstore.admin.model.request.book.UpdateBookRequest
import com.bookstore.admin.model.response.book.BookCategory
import com.bookstore.admin.repository.BookRepository
import com.bookstore.admin.utils.Retrofit.printRetrofitError
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.File

class EditBookViewModel (application: Application, private val bookRepository: BookRepository): AndroidViewModel(application) {

    private val _bookCategoryResponse = MutableLiveData<BookCategoryResponse>()
    val bookCategoryResponse: LiveData<BookCategoryResponse> = _bookCategoryResponse
    val currentBookCategory = mutableListOf<BookCategory>()

    fun getBookCategory() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = bookRepository.getBookCategory().sortedBy { it.id }
            currentBookCategory.clear()
            currentBookCategory.addAll(result)
            if (result.isNotEmpty()) _bookCategoryResponse.postValue(BookCategoryResponse(RetrofitStatus.SUCCESS, result))
            else {
                _bookCategoryResponse.postValue(BookCategoryResponse(RetrofitStatus.EMPTY))
                Log.e(this::class.java.simpleName, result.toString())
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException && throwable.code() == 401)
                _bookCategoryResponse.postValue(BookCategoryResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _bookCategoryResponse.postValue(BookCategoryResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }

    fun getBookStatus() = bookRepository.getBookStatus()

    private val _uploadBookImageResponse = MutableLiveData<UploadBookImageResponse>()
    val uploadBookImageResponse: LiveData<UploadBookImageResponse> = _uploadBookImageResponse
    var newBookCoverImage: File? = null
//    private val fileName = "string"
//    var newBookCoverImage = File(fileName)
    //TODO: Masuk ke Failure dan return null: open failed: ENOENT (No such file or directory) alias FileNotFoundException
    fun uploadBookImage(bookId: Int, image: File) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = bookRepository.uploadImageBook(bookId, image)
            if (result.code() == 200)
                _uploadBookImageResponse.postValue(UploadBookImageResponse(RetrofitStatus.SUCCESS))
            else {
                _uploadBookImageResponse.postValue(UploadBookImageResponse(RetrofitStatus.FAILURE))
                Log.e(this::class.java.simpleName, result.toString())
            }
        } catch (throwable: Throwable){
            if (throwable is HttpException && throwable.code() == 401)
                _uploadBookImageResponse.postValue(UploadBookImageResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _uploadBookImageResponse.postValue(UploadBookImageResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }

    private val _updateBookResponse = MutableLiveData<UpdateBookResponse>()
    val updateBookResponse: LiveData<UpdateBookResponse> = _updateBookResponse

    fun updateBook(updateBookRequest: UpdateBookRequest) = viewModelScope.launch(Dispatchers.IO) {
        try{
            val result = bookRepository.updateBook(updateBookRequest)
            // Log.i("test6", "onCreate: ${Gson().toJson(result)}")
            if (result.code() == 200)
                _updateBookResponse.postValue(UpdateBookResponse(RetrofitStatus.SUCCESS))
            else {
                _updateBookResponse.postValue(UpdateBookResponse(RetrofitStatus.FAILURE))
                Log.e(this::class.java.simpleName, result.toString())
            }
        } catch (throwable: Throwable) {
           // Log.i("test7", "onCreate: ${Gson().toJson(throwable)}")
            if (throwable is HttpException && throwable.code() == 401)
                _updateBookResponse.postValue(UpdateBookResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _updateBookResponse.postValue(UpdateBookResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }

    private val _deleteBookResponse = MutableLiveData<DeleteBookResponse>()
    val deleteBookResponse: LiveData<DeleteBookResponse> = _deleteBookResponse

    fun deleteBook(bookId: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = bookRepository.deleteBook(bookId)
            if (result.isSuccessful) _deleteBookResponse.postValue(DeleteBookResponse(RetrofitStatus.SUCCESS))
            else {
                _deleteBookResponse.postValue(DeleteBookResponse(RetrofitStatus.FAILURE))
                Log.e(this::class.java.simpleName, result.toString())
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException && throwable.code() == 401)
                _deleteBookResponse.postValue(DeleteBookResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _deleteBookResponse.postValue(DeleteBookResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }

    private val _changeBookCover = MutableLiveData<File>()
    val changeBookCover: LiveData<File> = _changeBookCover

    fun changeBookCoverAction(file: File) = viewModelScope.launch(Dispatchers.IO) {
        newBookCoverImage = file
        _changeBookCover.postValue(file)
    }
}
package com.bookstore.client.view.main.fragment.cart

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.repository.CartRepository
import com.bookstore.client.utils.Retrofit.printRetrofitError
import com.bookstore.client.constant.CartStatus
import com.bookstore.client.model.formatted.cart.CartResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CartViewModel(application: Application, private val cartRepository: CartRepository) :
    AndroidViewModel(application) {
    private val _cartResponse = MutableLiveData<CartResponse>()
    val cartResponse: LiveData<CartResponse> = _cartResponse
    fun getCart() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = cartRepository.getCart()
            if (result.details.any { it.cartDetailStatus == CartStatus.CARTED.toString() })
                _cartResponse.postValue(CartResponse(RetrofitStatus.SUCCESS, result))
            else
                _cartResponse.postValue(CartResponse(RetrofitStatus.EMPTY))
        } catch (throwable: Throwable) {
            if (throwable is HttpException && throwable.code() == 401)
                _cartResponse.postValue(CartResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _cartResponse.postValue(CartResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }

    private val _removeCartResponse = MutableLiveData<CartResponse>()
    val removeCartResponse: LiveData<CartResponse> = _removeCartResponse

    fun removeBookFromCart(bookId: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val cart = cartRepository.getCart()
            cart.details.firstOrNull { it.bookModel.id == bookId }?.id.let { detailId ->
                if (detailId != null) {
                    val result = cartRepository.removeBookFromCart(detailId)
                    if (result.isSuccessful) _removeCartResponse.postValue(
                        CartResponse(RetrofitStatus.SUCCESS)
                    )
                    else {
                        _removeCartResponse.postValue(CartResponse(RetrofitStatus.FAILURE))
                        Log.e(
                            this::class.java.simpleName,
                            result.toString()
                        )
                    }
                } else {
                    _removeCartResponse.postValue(CartResponse(RetrofitStatus.FAILURE))
                    Log.e(this::class.java.simpleName, "Can't find book id: $bookId in cart data")
                }
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException && throwable.code() == 401)
                _removeCartResponse.postValue(CartResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _removeCartResponse.postValue(CartResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }
}
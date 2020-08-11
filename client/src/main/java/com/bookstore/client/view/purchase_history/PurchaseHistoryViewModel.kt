package com.bookstore.client.view.purchase_history

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.model.formatted.transaction.TransactionResponse
import com.bookstore.client.repository.TransactionRepository
import com.bookstore.client.utils.Retrofit.printRetrofitError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PurchaseHistoryViewModel(
    application: Application,
    private val transactionRepository: TransactionRepository
) : AndroidViewModel(application) {

    private val _purchaseHistory = MutableLiveData<TransactionResponse>()
    var purchaseHistory : LiveData<TransactionResponse> = _purchaseHistory

    fun callPurchaseHistory() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = transactionRepository.getPurchaseHistory().sortedByDescending { it.id }
            if (result.isNotEmpty()) _purchaseHistory.postValue(TransactionResponse(RetrofitStatus.SUCCESS,result))
            else {
                _purchaseHistory.postValue(TransactionResponse(RetrofitStatus.EMPTY))
                Log.e(this::class.java.simpleName,result.toString())
            }
        } catch (throwable: Throwable) {
            if (throwable is HttpException && throwable.code() == 401)
                _purchaseHistory.postValue(TransactionResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _purchaseHistory.postValue(TransactionResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }

    }
}
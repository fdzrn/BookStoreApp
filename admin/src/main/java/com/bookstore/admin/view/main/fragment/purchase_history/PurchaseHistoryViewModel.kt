package com.bookstore.admin.view.main.fragment.purchase_history

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.formated.transaction.PurchaseHistoryResponse
import com.bookstore.admin.repository.TransactionRepository
import com.bookstore.admin.utils.Retrofit.printRetrofitError
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class PurchaseHistoryViewModel(
    application: Application,
    private val transaction: TransactionRepository
) : AndroidViewModel(application) {
    private val _purchaseHistory = MutableLiveData<PurchaseHistoryResponse>()
    val purchaseHistory : LiveData<PurchaseHistoryResponse> = _purchaseHistory

    fun purchaseHistoryList() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val result = transaction.getCheckoutHistory().sortedByDescending { it.createdTime }
            Log.i("test1", "purchaseHistoryList: ${Gson().toJson(result)}")
            if (result.isNotEmpty()) _purchaseHistory.postValue(PurchaseHistoryResponse(RetrofitStatus.SUCCESS, result))
            else {
                _purchaseHistory.postValue(PurchaseHistoryResponse(RetrofitStatus.EMPTY))
                Log.e(this::class.java.simpleName, result.toString())
            }
        } catch(throwable: Throwable) {
            Log.i("test2", "purchaseHistoryList: ${throwable.message}")
            if (throwable is HttpException && throwable.code() == 401)
                _purchaseHistory.postValue(PurchaseHistoryResponse(RetrofitStatus.UNAUTHORIZED))
            else
                _purchaseHistory.postValue(PurchaseHistoryResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }
}
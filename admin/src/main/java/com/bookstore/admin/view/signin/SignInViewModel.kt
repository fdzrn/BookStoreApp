package com.bookstore.admin.view.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.model.formated.user.SignInResponse
import com.bookstore.admin.model.request.user.AccessTokenRequest
import com.bookstore.admin.model.response.user.AccessToken
import com.bookstore.admin.repository.UserRepository
import com.bookstore.admin.utils.Retrofit.printRetrofitError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {
    private val _signInResponse = MutableLiveData<SignInResponse>()
    val signInResponse: LiveData<SignInResponse> = _signInResponse

    fun signIn(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val accessTokenRequest = AccessTokenRequest(username = username,password = password)
            userRepository.getAccessToken(accessTokenRequest).run {
                userRepository.saveAccessToken(this)
                _signInResponse.postValue(SignInResponse(RetrofitStatus.SUCCESS))
            }
        } catch (throwable: Throwable) {
            _signInResponse.postValue(SignInResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }
}
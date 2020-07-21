package com.bookstore.client.view.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.client.repository.UserRepository
import com.bookstore.client.utils.Retrofit.printRetrofitError
import com.bookstore.client.constant.RetrofitStatus
import com.bookstore.client.model.formatted.user.SignInResponse
import com.bookstore.client.model.request.user.AccessTokenRequest
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
            val accessTokenRequest = AccessTokenRequest(username = username, password = password)
            userRepository.getAccessToken(accessTokenRequest).run {
                userRepository.saveSessions(this)
                _signInResponse.postValue(SignInResponse(RetrofitStatus.SUCCESS))
            }
        } catch (throwable: Throwable) {
            _signInResponse.postValue(SignInResponse(RetrofitStatus.FAILURE))
            throwable.printRetrofitError()
        }
    }

}
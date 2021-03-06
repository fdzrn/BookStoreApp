package com.bookstore.admin.view.main

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.constant.SessionStatus
import com.bookstore.admin.model.formated.user.SessionResponse
import com.bookstore.admin.repository.UserRepository
import com.bookstore.admin.view.signin.SignInActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {

    private val _sessionResponse = MutableLiveData<SessionResponse>()
    val sessionResponse: LiveData<SessionResponse> = _sessionResponse

    fun checkSession() = viewModelScope.launch(Dispatchers.IO) {
        userRepository.checkAccessToken().run {
            if(this != null) _sessionResponse.postValue(SessionResponse(SessionStatus.AVAILABLE, this))
            else _sessionResponse.postValue(SessionResponse(SessionStatus.UNAVAILABLE))
        }
    }

    fun logout(currentActivity: Activity) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.checkAccessToken()?.let{
            userRepository.deleteAccessToken(it)
        }
        currentActivity.run {
            startActivity(Intent(this, SignInActivity::class.java))
            finishAffinity()
        }
    }
}
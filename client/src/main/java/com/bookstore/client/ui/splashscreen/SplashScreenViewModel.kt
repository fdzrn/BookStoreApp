package com.bookstore.client.ui.splashscreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bookstore.client.repository.UserRepository
import com.bookstore.client.constant.SessionStatus
import com.bookstore.client.model.formatted.user.SessionResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenViewModel(
    application: Application,
    private val userRepository: UserRepository
) : AndroidViewModel(application) {
    private val _sessionResponse = MutableLiveData<SessionResponse>()
    val sessionResponse: LiveData<SessionResponse> = _sessionResponse

    fun checkSession() = viewModelScope.launch(Dispatchers.IO) {
        try {
            userRepository.checkSession().run {
                if (this != null) _sessionResponse.postValue(SessionResponse(SessionStatus.AVAILABLE, this))
                else _sessionResponse.postValue(SessionResponse(SessionStatus.UNAVAILABLE))
            }
        } catch (throwable: Throwable) {
            _sessionResponse.postValue(SessionResponse(SessionStatus.UNAVAILABLE))
            throwable.printStackTrace()
        }
    }
}
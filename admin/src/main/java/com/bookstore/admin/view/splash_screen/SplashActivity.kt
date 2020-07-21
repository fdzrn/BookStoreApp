package com.bookstore.admin.view.splash_screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bookstore.admin.constant.SessionStatus
import com.bookstore.admin.view.main.MainActivity
import com.bookstore.admin.view.signin.SignInActivity
import com.bookstore.admin.view.signin.SignInViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity: AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashViewModel.sessionResponse.observe(this, Observer {
            if (it.status == SessionStatus.AVAILABLE) startActivity(Intent(this, MainActivity::class.java))
            else startActivity(Intent(this, SignInActivity::class.java))
            finish()
        })
    }

    override fun onStart() {
        super.onStart()
        splashViewModel.checkSession()
    }
}
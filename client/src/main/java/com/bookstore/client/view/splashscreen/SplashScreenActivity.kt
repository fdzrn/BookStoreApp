package com.bookstore.client.view.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bookstore.client.view.main.MainActivity
import com.bookstore.client.view.signin.SignInActivity
import com.bookstore.client.constant.SessionStatus
import org.koin.android.viewmodel.ext.android.viewModel

class SplashScreenActivity : AppCompatActivity() {

    private val splashScreenViewModel :SplashScreenViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreenViewModel.sessionResponse.observe(this, Observer {
            if (it.status == SessionStatus.AVAILABLE) startActivity(Intent(this,MainActivity::class.java))
            else startActivity(Intent(this,SignInActivity::class.java))
            finish()
        })
    }

    override fun onStart() {
        super.onStart()
        splashScreenViewModel.checkSession()
    }
}
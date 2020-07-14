package com.bookstore.client.ui.signin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bookstore.R
import com.bookstore.client.config.AppConfig
import com.bookstore.client.ui.main.MainActivity
import com.bookstore.client.utils.ViewHelper.invisible
import com.bookstore.client.utils.ViewHelper.show
import com.bookstore.client.constant.RetrofitStatus
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.fragment_book.loading
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {

    private val signInViewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        signInViewModel.signInResponse.observe(this, Observer {
            if (it.status == RetrofitStatus.SUCCESS) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                loading.invisible()
                button_signin.isEnabled = true
                Snackbar.make(parent_layout, "there is an error when trying to sign In", Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        button_signin.setOnClickListener {
            val username = input_username.editText?.text.toString()
            val password = input_password.editText?.text.toString()
            input_username.error = if (username.isEmpty()) "Please type your username" else null
            input_password.error = if (password.isEmpty()) "Please type your password" else null
            if (username == AppConfig.OAUTH_DEFAULT_ACCOUNT_USERNAME && password == AppConfig.OAUTH_DEFAULT_ACCOUNT_PASSWORD) {
                loading.show()
                button_signin.isEnabled = false
                signInViewModel.signIn(username.trim(), password.trim())
            } else {
                input_username.error = if (username == AppConfig.OAUTH_DEFAULT_ACCOUNT_USERNAME) "We can't find your username" else null
                input_password.error = if (password == AppConfig.OAUTH_DEFAULT_ACCOUNT_PASSWORD) "We can't find your password" else null
                Snackbar.make(parent_layout, "Your username of password is wrong, please recheck", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
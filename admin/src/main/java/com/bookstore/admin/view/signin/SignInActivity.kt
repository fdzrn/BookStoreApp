package com.bookstore.admin.view.signin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bookstore.admin.R
import com.bookstore.admin.config.AppConfig
import com.bookstore.admin.constant.RetrofitStatus
import com.bookstore.admin.utils.ViewHelper.invisible
import com.bookstore.admin.utils.ViewHelper.show
import com.bookstore.admin.view.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_signin.*
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {

    private val signInViewModel: SignInViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        signInViewModel.signInResponse.observe(this, Observer {
            if (it.status == RetrofitStatus.SUCCESS) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                loading.invisible()
                button_signin.isEnabled = true
                Snackbar.make(parent_layout, "Error when trying to Sign-In", Snackbar.LENGTH_SHORT)
                    .show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        button_signin.setOnClickListener {
            val username = input_username.editText?.text.toString()
            val password = input_password.editText?.text.toString()
            input_username.error = if (username.isEmpty()) "Please type your Username" else null
            input_password.error = if (password.isEmpty()) "Please type your Password" else null
            if (username == AppConfig.OAUTH_DEFAULT_ACCOUNT_USERNAME && password == AppConfig.OAUTH_DEFAULT_ACCOUNT_PASSWORD) {
                loading.show()
                button_signin.isEnabled = false
                signInViewModel.signIn(username.trim(), password.trim())
            } else {
                input_username.error = if (username != AppConfig.OAUTH_DEFAULT_ACCOUNT_USERNAME) "Please type your Username" else null
                input_password.error = if (password != AppConfig.OAUTH_DEFAULT_ACCOUNT_PASSWORD) "Please type your Password" else null
                Snackbar.make(parent_layout,"The username or password you entered is incorrect, please check again",Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
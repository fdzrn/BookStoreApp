package com.bookstore.admin.di

import com.bookstore.admin.dao.remote.RemoteBookDAO
import com.bookstore.admin.database.local.LocalDatabase
import com.bookstore.admin.database.remote.RemoteDatabase
import com.bookstore.admin.repository.BookRepository
import com.bookstore.admin.repository.CartRepository
import com.bookstore.admin.repository.TransactionRepository
import com.bookstore.admin.repository.UserRepository
import com.bookstore.admin.view.main.MainViewModel
import com.bookstore.admin.view.signin.SignInViewModel
import com.bookstore.admin.view.splash_screen.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //DAO
    single { RemoteDatabase.userDAO }
    single { RemoteDatabase.bookDAO }
    single { RemoteDatabase.cartDAO }
    single { RemoteDatabase.remoteTransactionDAO }
    single { LocalDatabase.userDAO }

    // Repository
    single { BookRepository(get(),get()) }
    single { UserRepository(get(),get()) }
    single { CartRepository(get(),get()) }
    single {TransactionRepository(get(),get())}

    //ViewModel
    viewModel { SignInViewModel(get(),get()) }
    viewModel { SplashViewModel(get(),get()) }
    viewModel { MainViewModel(get(),get()) }
}
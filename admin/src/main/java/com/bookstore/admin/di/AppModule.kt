package com.bookstore.admin.di

import com.bookstore.admin.database.local.LocalDatabase
import com.bookstore.admin.database.remote.RemoteDatabase
import com.bookstore.admin.repository.BookRepository
import com.bookstore.admin.repository.CartRepository
import com.bookstore.admin.repository.TransactionRepository
import com.bookstore.admin.repository.UserRepository
import com.bookstore.admin.view.book_category.add.AddBookCategoryDialogViewModel
import com.bookstore.admin.view.book_category.edit.EditBookCategoryDialogViewModel
import com.bookstore.admin.view.main.MainViewModel
import com.bookstore.admin.view.main.fragment.book.BookViewModel
import com.bookstore.admin.view.main.fragment.book_category.BookCategoryViewModel
import com.bookstore.admin.view.main.fragment.home.HomeViewModel
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
    single { BookRepository(get(), get()) }
    single { UserRepository(get(), get()) }
    single { CartRepository(get(), get()) }
    single { TransactionRepository(get(), get()) }

    //ViewModel
    viewModel { SignInViewModel(get(), get()) }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { BookViewModel(get(), get()) }
    viewModel { BookCategoryViewModel(get(), get()) }
    viewModel { AddBookCategoryDialogViewModel(get(), get()) }
    viewModel { EditBookCategoryDialogViewModel(get(),get()) }
}
package com.bookstore.client.di

import com.bookstore.client.database.local.LocalDatabase
import com.bookstore.client.database.remote.RemoteDatabase
import com.bookstore.client.repository.BookRepository
import com.bookstore.client.repository.CartRepository
import com.bookstore.client.repository.TransactionRepository
import com.bookstore.client.repository.UserRepository
import com.bookstore.client.ui.book.DetailBookViewModel
import com.bookstore.client.ui.checkout.CheckoutViewModel
import com.bookstore.client.ui.main.MainViewModel
import com.bookstore.client.ui.main.fragment.book.BookViewModel
import com.bookstore.client.ui.main.fragment.cart.CardViewModel
import com.bookstore.client.ui.search.SearchBookViewModel
import com.bookstore.client.ui.signin.SignInViewModel
import com.bookstore.client.ui.splashscreen.SplashScreenViewModel
import com.bookstore.client.ui.wishlist.WishlistViewModel
import com.bookstore.model.formatted.transaction.CheckoutResponse
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //DAO
    single { RemoteDatabase.userDAO }
    single { RemoteDatabase.bookDAO }
    single { RemoteDatabase.cartDAO }
    single { RemoteDatabase.transactionDAO }
    single { LocalDatabase.userDAO }

    // Repository
    single { UserRepository(get(), get()) }
    single { BookRepository(get(), get()) }
    single { CartRepository(get(), get()) }
    single { TransactionRepository(get(), get()) }

    // ViewModel
    viewModel { SplashScreenViewModel(get(), get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { BookViewModel(get(), get()) }
    viewModel { CardViewModel(get(), get()) }
    viewModel { DetailBookViewModel(get(), get(), get()) }
    viewModel { SearchBookViewModel(get(), get()) }
    viewModel { WishlistViewModel(get(), get()) }
    viewModel { CheckoutViewModel(get(), get(), get()) }
}
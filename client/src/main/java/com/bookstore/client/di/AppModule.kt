package com.bookstore.client.di

import com.bookstore.client.database.local.LocalDatabase
import com.bookstore.client.database.remote.RemoteDatabase
import com.bookstore.client.repository.BookRepository
import com.bookstore.client.repository.CartRepository
import com.bookstore.client.repository.TransactionRepository
import com.bookstore.client.repository.UserRepository
import com.bookstore.client.view.book.DetailBookViewModel
import com.bookstore.client.view.checkout.CheckoutViewModel
import com.bookstore.client.view.main.MainViewModel
import com.bookstore.client.view.main.fragment.book.BookViewModel
import com.bookstore.client.view.main.fragment.cart.CartViewModel
import com.bookstore.client.view.purchase_history.PurchaseHistoryViewModel
import com.bookstore.client.view.search.SearchBookViewModel
import com.bookstore.client.view.signin.SignInViewModel
import com.bookstore.client.view.splashscreen.SplashScreenViewModel
import com.bookstore.client.view.wishlist.WishlistViewModel
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
    viewModel { CartViewModel(get(), get()) }
    viewModel { DetailBookViewModel(get(), get(), get()) }
    viewModel { SearchBookViewModel(get(), get()) }
    viewModel { WishlistViewModel(get(), get()) }
    viewModel { CheckoutViewModel(get(), get(), get()) }
    viewModel { PurchaseHistoryViewModel(get(),get()) }
}
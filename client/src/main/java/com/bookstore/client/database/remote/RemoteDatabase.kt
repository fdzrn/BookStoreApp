package com.bookstore.client.database.remote

import com.bookstore.client.dao.remote.RemoteBookDAO
import com.bookstore.client.dao.remote.RemoteCartDAO
import com.bookstore.client.dao.remote.RemoteTransactionDAO
import com.bookstore.client.dao.remote.RemoteUserDAO
import com.bookstore.client.utils.Retrofit

object RemoteDatabase {
    val userDAO: RemoteUserDAO = Retrofit.getClient().create(RemoteUserDAO::class.java)
    val bookDAO: RemoteBookDAO = Retrofit.getClient().create(RemoteBookDAO::class.java)
    val cartDAO: RemoteCartDAO = Retrofit.getClient().create(RemoteCartDAO::class.java)
    val transactionDAO: RemoteTransactionDAO = Retrofit.getClient().create(RemoteTransactionDAO::class.java)
}
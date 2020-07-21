package com.bookstore.admin.database.remote

import com.bookstore.admin.dao.remote.RemoteBookDAO
import com.bookstore.admin.dao.remote.RemoteCartDAO
import com.bookstore.admin.dao.remote.RemoteTransactionDAO
import com.bookstore.admin.dao.remote.RemoteUserDAO
import com.bookstore.admin.utils.Retrofit

object RemoteDatabase {
    val userDAO: RemoteUserDAO = Retrofit.getClientAdmin().create(RemoteUserDAO::class.java)
    val bookDAO: RemoteBookDAO = Retrofit.getClientAdmin().create(RemoteBookDAO::class.java)
    val cartDAO: RemoteCartDAO = Retrofit.getClientAdmin().create(RemoteCartDAO::class.java)
    val remoteTransactionDAO: RemoteTransactionDAO = Retrofit.getClientAdmin().create(RemoteTransactionDAO::class.java)
}
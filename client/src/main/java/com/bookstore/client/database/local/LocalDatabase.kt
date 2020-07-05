package com.bookstore.client.database.local

import com.bookstore.client.dao.local.LocalUserDAO

object LocalDatabase {
    val userDAO: LocalUserDAO = LocalDatabaseImpl.database.userDAO
}
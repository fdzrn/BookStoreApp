package com.bookstore.client.repository

import com.bookstore.client.dao.local.LocalUserDAO
import com.bookstore.client.dao.remote.RemoteUserDAO
import com.bookstore.client.model.request.user.AccessTokenRequest
import com.bookstore.model.response.user.AccessToken

class UserRepository(
    private val remoteUserDAO: RemoteUserDAO,
    private val localUserDAO: LocalUserDAO
) {
    suspend fun getAccessToken(accessTokenRequest: AccessTokenRequest) =
        remoteUserDAO.getAccessToken(
            accessTokenRequest.grantType,
            accessTokenRequest.scope,
            accessTokenRequest.username,
            accessTokenRequest.password
        )

    suspend fun saveSessions(accessToken: AccessToken) = localUserDAO.saveAccessToken(accessToken)
    suspend fun destroySession(accessToken: AccessToken) = localUserDAO.removeCurrentAccessToken(accessToken)
    suspend fun checkSession() = localUserDAO.getCurrentAccessToken()
}
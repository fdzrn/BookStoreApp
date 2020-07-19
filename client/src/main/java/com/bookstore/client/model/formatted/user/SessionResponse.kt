package com.bookstore.client.model.formatted.user

import com.bookstore.client.constant.SessionStatus
import com.bookstore.model.response.user.AccessToken

data class SessionResponse(
    val status: SessionStatus = SessionStatus.UNKNOWN,
    val accessToken: AccessToken? = null
)
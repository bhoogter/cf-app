package com.wincdspro.app.model.auth

import com.wincdspro.app.mapper.ToJson

data class AuthResponse(
    val token: String? = null
) : ToJson()

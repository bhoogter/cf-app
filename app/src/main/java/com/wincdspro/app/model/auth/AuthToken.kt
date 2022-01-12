package com.wincdspro.app.model.auth

import com.fasterxml.jackson.annotation.JsonIgnore
import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.util.Mapper

data class AuthToken(
    var iss: String? = null,
    var iat: String? = null,
    var exp: String? = null,
    var admin: Boolean = false,
    var groups: List<String> = listOf(),
    var usr: String? = null,
    var aud: String? = null,
    var store: Int = 1,
) : ToJson() {
    @JsonIgnore
    var token: String? = null

    companion object {
        @JvmStatic
        fun decode(token: String): AuthToken {
            val parts = token.split(".")
            if (parts.size != 3) return AuthToken()
            val payloadJsonBytes = android.util.Base64.decode(parts[1], android.util.Base64.NO_PADDING)
            val result = Mapper.fromJson(payloadJsonBytes, AuthToken::class.java) ?: return AuthToken()
            result.token = token
            return result
        }
    }
}

package com.wincdspro.app.model.settings.sections

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.auth.AuthToken

data class ConnectionSettings(
    var loggedIn: Boolean = false,
    var authToken: AuthToken = AuthToken(),
) : ToJson()

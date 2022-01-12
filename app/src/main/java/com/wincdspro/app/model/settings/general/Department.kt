package com.wincdspro.app.model.settings.general

import com.wincdspro.app.mapper.ToJson

data class Department(
    var name: String? = null,
    var id: Int? = null,
) : ToJson()

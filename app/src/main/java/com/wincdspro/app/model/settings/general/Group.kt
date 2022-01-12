package com.wincdspro.app.model.settings.general

import com.wincdspro.app.mapper.ToJson

data class Group(
    var id: String = "",
    var privs: List<Int> = listOf(),
    var name: String = "",
) : ToJson()

package com.wincdspro.app.model.settings.general

import com.wincdspro.app.mapper.ToJson

data class PermissionZone(
    var id: Int = 0,
    var name: String = "",
) : ToJson()

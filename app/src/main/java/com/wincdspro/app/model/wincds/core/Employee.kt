package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.mapper.ToJson

data class Employee(
    var id: Long? = 0,
    var lastName: String? = "",
    var salesId: String? = "",
    var commRate: Double? = 0.0,
    var pwd: String? = "",
    var privs: String? = "",
    var active: Boolean? = false,
    var commTable: String? = "",
) : ToJson()

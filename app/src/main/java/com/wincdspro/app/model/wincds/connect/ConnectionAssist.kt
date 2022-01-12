package com.wincdspro.app.model.wincds.connect

import com.wincdspro.app.mapper.ToJson

data class ConnectionAssist(
    val host: String = "",
    val port: String = "0",
    val pass: String = "",
) : ToJson()

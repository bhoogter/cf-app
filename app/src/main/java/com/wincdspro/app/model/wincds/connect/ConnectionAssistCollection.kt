package com.wincdspro.app.model.wincds.connect

import com.wincdspro.app.mapper.ToJson

data class ConnectionAssistCollection(val results: List<ConnectionAssist> = listOf()) : ToJson()

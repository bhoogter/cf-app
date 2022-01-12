package com.wincdspro.app.model.wincds.search

import com.wincdspro.app.mapper.ToJson

data class SearchMail(
    val index: Int = 0,
    val last: String = "",
    val first: String = "",
    val tele: String = "",
    val city: String = "",
) : ToJson()

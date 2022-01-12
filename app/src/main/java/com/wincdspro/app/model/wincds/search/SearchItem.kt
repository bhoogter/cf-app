package com.wincdspro.app.model.wincds.search

import com.wincdspro.app.mapper.ToJson

data class SearchItem(
    val rn: Int = 0,
    val style: String = "",
    val vendor: String = "",
    val vendorNo: String = "",
    val dept: String = "",
    val desc: String = "",
) : ToJson()

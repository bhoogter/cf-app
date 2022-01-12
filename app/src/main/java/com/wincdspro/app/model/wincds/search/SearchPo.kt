package com.wincdspro.app.model.wincds.search

import com.wincdspro.app.mapper.ToJson

data class SearchPo(
    val poNo: Int = 0,
    val leaseNo: String? = null,
    val poDate: String? = null,
    val name: String? = null,
    val vendor: String? = null,
    val location: Int = 1,
) : ToJson()

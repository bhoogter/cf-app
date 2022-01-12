package com.wincdspro.app.model.wincds.request.newsale

import com.wincdspro.app.mapper.ToJson

class NewSaleItem(
    val style: String = "",
    val status: String = "",
    val loc: Int = 1,
    val quantity: Double = 0.0,
    val price: Double? = null,
) : ToJson()

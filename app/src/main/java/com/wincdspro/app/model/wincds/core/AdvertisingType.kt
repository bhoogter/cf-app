package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.mapper.ToJson

data class AdvertisingType(
    var advertisingTypeId: Long? = 0,
    var advertisingType: String? = "",
    var oldTypeId: Long? = 0,
) : ToJson()

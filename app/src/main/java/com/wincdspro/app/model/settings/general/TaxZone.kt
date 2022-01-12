package com.wincdspro.app.model.settings.general

import com.wincdspro.app.mapper.ToJson

data class TaxZone(
    var rate: Double? = null,
    var id: String? = null,
) : ToJson()

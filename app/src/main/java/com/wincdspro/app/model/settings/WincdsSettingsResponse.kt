package com.wincdspro.app.model.settings

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.settings.sections.StoreSettings
import com.wincdspro.app.model.settings.sections.WincdsSettings

data class WincdsSettingsResponse(
    var wincds: WincdsSettings = WincdsSettings(),
    var stores: List<StoreSettings> = listOf(),
) : ToJson()

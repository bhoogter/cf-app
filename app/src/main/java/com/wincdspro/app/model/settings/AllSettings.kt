package com.wincdspro.app.model.settings

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.settings.sections.ConnectionSettings
import com.wincdspro.app.model.settings.sections.ServerSettings
import com.wincdspro.app.model.settings.sections.StoreSettings
import com.wincdspro.app.model.settings.sections.UserSettings
import com.wincdspro.app.model.settings.sections.WincdsSettings

data class AllSettings(
    var serversettings: ServerSettings = ServerSettings(),
    var userSettings: UserSettings = UserSettings(),
    var connectionSettings: ConnectionSettings = ConnectionSettings(),
    var wincdsSettings: WincdsSettings = WincdsSettings(),
    var storeSettings: List<StoreSettings> = listOf(),
) : ToJson()

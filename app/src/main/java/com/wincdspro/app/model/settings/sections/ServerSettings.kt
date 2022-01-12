package com.wincdspro.app.model.settings.sections

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.settings.support.SettingSection
import com.wincdspro.app.model.settings.support.SettingSectionFieldInfo
import com.wincdspro.app.model.settings.support.SettingSectionInfo

@SettingSectionInfo(key = "server")
data class ServerSettings(
    @SettingSectionFieldInfo(name = "serverIsSetup", def = "false")
    var serverIsSetup: Boolean = false,
    @SettingSectionFieldInfo(name = "serverHost", def = "192.168.52.49")
    var serverHost: String = "",
    @SettingSectionFieldInfo(name = "serverPort", def = "8082")
    var serverPort: Int = 0,
    @SettingSectionFieldInfo(name = "serverPass")
    var serverPass: String? = null,
) : ToJson(), SettingSection<ServerSettings>

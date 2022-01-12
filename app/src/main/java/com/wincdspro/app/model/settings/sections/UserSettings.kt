package com.wincdspro.app.model.settings.sections

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.settings.support.SettingSection
import com.wincdspro.app.model.settings.support.SettingSectionFieldInfo
import com.wincdspro.app.model.settings.support.SettingSectionInfo

@SettingSectionInfo(key = "user")
data class UserSettings(
    @SettingSectionFieldInfo(name = "storeno", def = "1")
    var storeNo: Int = 1,
    @SettingSectionFieldInfo(name = "salesman")
    var salesman: String? = null,
    @SettingSectionFieldInfo(name = "salesmanPass")
    var salesmanPass: String? = null,
) : ToJson(), SettingSection<UserSettings>

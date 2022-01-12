package com.wincdspro.app.model.settings.sections

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.settings.general.Group
import com.wincdspro.app.model.settings.general.PermissionZone
import com.wincdspro.app.model.settings.support.SettingSection

data class WincdsSettings(
    var loaded: Boolean = false,
    var departments: List<String>? = listOf(),
    var groups: List<Group> = listOf(),
    var advertisingTypes: List<String>? = listOf(),
    var maxStoresLicensed: Int = 1,
    var maxStoresAvailable: Int = 1,
    var permissionZones: List<PermissionZone>? = listOf(),
) : ToJson(), SettingSection<WincdsSettings>

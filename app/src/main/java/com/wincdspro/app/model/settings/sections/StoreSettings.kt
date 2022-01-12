package com.wincdspro.app.model.settings.sections

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.settings.general.Department
import com.wincdspro.app.model.settings.general.Salesman
import com.wincdspro.app.model.settings.general.StoreInfo
import com.wincdspro.app.model.settings.general.TaxZone
import com.wincdspro.app.model.settings.support.SettingSection

data class StoreSettings(
    var loaded: Boolean = false,
    var salesmen: List<Salesman> = listOf(),
    var taxRate: Double = 0.0,
    var taxRates: List<TaxZone> = listOf(),
    var departments: List<Department> = listOf(),
    var storeInfo: StoreInfo = StoreInfo(),
) : ToJson(), SettingSection<StoreSettings>

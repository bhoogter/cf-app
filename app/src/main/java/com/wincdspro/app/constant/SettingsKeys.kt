package com.wincdspro.app.constant

import com.wincdspro.app.constant.AppValues.Companion.APP_PACKAGE

class SettingsKeys {
    companion object {
        const val prefsPath = APP_PACKAGE

        const val keyHost = "serverhost"
        const val defHost = "192.168.0.2"
        const val keyPort = "serverport2"
        const val defPort = 8080
        const val keyPass = "serverpass"

        const val keySalesman = "salesman"
        const val keySalesmanPass = "salesmanPass"
        const val keyStoreNo = "storeno"

        const val keyStoreCount = "storeCount"
    }
}

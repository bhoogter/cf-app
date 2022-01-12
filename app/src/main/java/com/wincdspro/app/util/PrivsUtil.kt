package com.wincdspro.app.util

import android.app.Activity
import android.widget.Toast
import com.wincdspro.app.constant.PrivZones
import com.wincdspro.app.model.settings.general.Group

class PrivsUtil {
    companion object {
        private const val GROUP_ADMIN = "A"
        private val isAdmin: Boolean get() = SettingsManager.authToken.admin

        private val userGroups: List<String> get() = SettingsManager.authToken.groups

        private val groups: List<Group> get() = SettingsManager.wincdsSettings.groups

        @JvmStatic
        fun checkAccess(zone: PrivZones, notifyingActivity: Activity? = null): Boolean {
            if (isAdmin) return true
            for (g in userGroups) {
                val group = groups.firstOrNull { it.id == g } ?: return false
                if (group.id == GROUP_ADMIN || group.privs.contains(zone.privId)) return true
            }

            if (notifyingActivity != null)
                Toast.makeText(notifyingActivity, "You do not have access to ${zone.text}", Toast.LENGTH_LONG).show()

            return false
        }
    }
}

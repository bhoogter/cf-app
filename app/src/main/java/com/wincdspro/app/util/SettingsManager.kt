package com.wincdspro.app.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.constant.SettingsKeys
import com.wincdspro.app.exception.SettingsManagerException
import com.wincdspro.app.model.auth.AuthToken
import com.wincdspro.app.model.settings.AllSettings
import com.wincdspro.app.model.settings.sections.ServerSettings
import com.wincdspro.app.model.settings.sections.StoreSettings
import com.wincdspro.app.model.settings.sections.UserSettings
import com.wincdspro.app.model.settings.sections.WincdsSettings
import java.lang.reflect.Field

class SettingsManager {
    companion object {
        @JvmStatic
        var settings: AllSettings = AllSettings()

        @JvmStatic
        var loggingIn: Boolean = false

        @JvmStatic
        var loggedIn: Boolean
            get() = settings.connectionSettings.loggedIn && settings.connectionSettings.authToken.exp != null
            set(v) {
                loggingIn = false
                settings.connectionSettings.loggedIn = v
                settings.connectionSettings.authToken.exp = "-"
            }

        @JvmStatic
        val loggedInUser: String?
            get() = settings.connectionSettings.authToken.aud

        @JvmStatic
        val isAdmin: Boolean
            get() = settings.connectionSettings.authToken.admin

        @JvmStatic
        var serverSettings: ServerSettings
            get() = settings.serversettings
            set(v) {
                settings.serversettings = v
            }

        @JvmStatic
        var userSettings: UserSettings
            get() = settings.userSettings
            set(v) {
                settings.userSettings = v
            }

        @JvmStatic
        var authToken: AuthToken
            get() = settings.connectionSettings.authToken
            set(v) {
                loggingIn = false
                settings.connectionSettings.authToken = v
            }

        @JvmStatic
        var wincdsSettings: WincdsSettings
            get() = settings.wincdsSettings
            set(v) {
                settings.wincdsSettings = v
            }

        @JvmStatic
        val storeNo: Int
            get() = userSettings.storeNo

        @JvmStatic
        val maxStoreLicensed: Int
            get() = settings.wincdsSettings.maxStoresLicensed

        @JvmStatic
        val maxStoreAvailable: Int
            get() = settings.wincdsSettings.maxStoresLicensed

        @JvmStatic
        fun getStoreInfo(i: Int): StoreSettings = settings.storeSettings.getOrElse(i - 1) { StoreSettings() }

        @JvmStatic
        fun getActivityPreferences(activity: Activity): SharedPreferences {
            return activity.getSharedPreferences(SettingsKeys.prefsPath, Context.MODE_PRIVATE)
        }

        @JvmStatic
        fun load(activity: Activity? = null): AllSettings = load(getActivityPreferences(activity ?: getActivity() ?: throw SettingsManagerException("No activity found for load")))

        @JvmStatic
        fun load(preferences: SharedPreferences): AllSettings {
            settings = AllSettings(
                ServerSettings().apply { load(preferences) },
                UserSettings().apply { load(preferences) }
            )

            WincdsClient().getSettings { status, info ->
                loggedIn = if (status == SC_OK && info != null) {
                    wincdsSettings = info.wincdsSettings
                    settings.storeSettings = info.storeSettings
                    true
                } else false
            }

            return settings
        }

        @JvmStatic
        fun save(activity: Activity? = null) = save(getActivityPreferences(activity ?: getActivity() ?: throw SettingsManagerException("No activity found for save")))

        @JvmStatic
        fun save(preferences: SharedPreferences) {
            serverSettings.save(preferences)
            userSettings.save(preferences)
        }

        @SuppressLint("PrivateApi")
        @Suppress("UNCHECKED_CAST")
        fun getActivity(): Activity? {
            val activityThreadClass = Class.forName("android.desktop.ico.ActivityThread")
            val activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null)
            val activitiesField: Field = activityThreadClass.getDeclaredField("mActivities")
            activitiesField.isAccessible = true
            val activities = activitiesField.get(activityThread) as Map<Any, Any>? ?: return null
            for (activityRecord in activities.values) {
                val activityRecordClass: Class<*> = activityRecord.javaClass
                val pausedField: Field = activityRecordClass.getDeclaredField("paused")
                pausedField.isAccessible = true
                if (!pausedField.getBoolean(activityRecord)) {
                    val activityField: Field = activityRecordClass.getDeclaredField("activity")
                    activityField.isAccessible = true
                    return activityField.get(activityRecord) as Activity?
                }
            }
            return null
        }
    }
}

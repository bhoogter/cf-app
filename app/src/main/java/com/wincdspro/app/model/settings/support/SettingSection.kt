package com.wincdspro.app.model.settings.support

import android.content.SharedPreferences
import com.wincdspro.app.constant.AppValues.Companion.APP_PACKAGE
import com.wincdspro.app.exception.SettingsManagerException
import java.lang.reflect.Field

interface SettingSection<T> {
    companion object {
        private const val TYPES_ALLOWED: String = "String/Int/Long/Double/Boolean"
    }

    fun load(preferences: SharedPreferences): T? {
        this.javaClass.declaredFields.forEach {
            it.isAccessible = true
            when (it.type) {
                String::class.java -> it.set(this, preferences.getString(fieldKey(it), fieldDef(it)))
                Int::class.java -> it.set(this, preferences.getInt(fieldKey(it), fieldDef(it).toIntOrNull() ?: 0))
                Long::class.java -> it.set(this, preferences.getLong(fieldKey(it), fieldDef(it).toLongOrNull() ?: 0L))
                Double::class.java -> it.set(this, preferences.getFloat(fieldKey(it), fieldDef(it).toFloat() ?: 0.0F))
                Boolean::class.java -> it.set(this, preferences.getBoolean(fieldKey(it), fieldDef(it).toBoolean() ?: false))
                else -> throw SettingsManagerException("Unknown type in load (use $TYPES_ALLOWED): ${it.type.simpleName}")
            }
        }
        return null
    }

    fun save(preferences: SharedPreferences) {
        val editor = preferences.edit()
        this.javaClass.declaredFields.forEach {
            it.isAccessible = true
            when (it.type) {
                String::class.java -> editor.putString(fieldKey(it), it.get(this) as String)
                Int::class.java -> editor.putInt(fieldKey(it), it.get(this) as Int)
                Long::class.java -> editor.putLong(fieldKey(it), it.get(this) as Long)
                Double::class.java -> editor.putFloat(fieldKey(it), it.get(this) as Float)
                Boolean::class.java -> editor.putBoolean(fieldKey(it), it.get(this) as Boolean)
                else -> throw SettingsManagerException("Unknown type in save (use $TYPES_ALLOWED): ${it.type.simpleName}")
            }
        }
        editor.apply()
    }

    private fun fieldKey(f: Field): String = "$APP_PACKAGE.${sectionName()}.${fieldNam(f)}"
    private fun fieldNam(f: Field): String = f.getAnnotation(SettingSectionFieldInfo::class.java)?.name ?: f.name
    private fun fieldDef(f: Field): String = f.getAnnotation(SettingSectionFieldInfo::class.java)?.def ?: ""
    private fun sectionName(): String = this.javaClass.getAnnotation(SettingSectionInfo::class.java)?.key ?: this.javaClass.simpleName
}

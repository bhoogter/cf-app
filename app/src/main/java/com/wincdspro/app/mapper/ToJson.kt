package com.wincdspro.app.mapper

import com.wincdspro.app.util.Mapper

open class ToJson {
    fun toJson(): String = Companion.toJson(this)
    fun fromJson(json: String): ToJson = Companion.fromJson(json, this.javaClass)
    fun of(o: ToJson): ToJson = Companion.of(o, this.javaClass)

    companion object {
        @JvmStatic
        fun toJson(o: Any): String = Mapper.toJson(o)

        @JvmStatic
        fun <U> fromJson(o: String, c: Class<U>): U = Mapper.fromJson(o, c)

        @JvmStatic
        fun <U> of(o: ToJson, c: Class<U>): U = Mapper.fromJson(Mapper.toJson(o), c)
    }
}

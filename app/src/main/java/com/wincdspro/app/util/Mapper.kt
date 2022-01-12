package com.wincdspro.app.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.json.JsonReadFeature
import com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT
import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.databind.DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS
import com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS

open class Mapper {
    companion object {
        //        val xmlMapper = XmlMapper()
        val jsonMapper = ObjectMapper().apply {
            enable(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature())
            enable(ORDER_MAP_ENTRIES_BY_KEYS)
            enable(READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            disable(FAIL_ON_UNKNOWN_PROPERTIES)
            enable(ACCEPT_CASE_INSENSITIVE_ENUMS, ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }

        fun getMapper() = jsonMapper

//        @JvmStatic
//        fun ToXml(o: Any): String {
//            return xmlMapper.writeValueAsString(o)
//        }

        // String version inspect-able via:  String(bytes!!)
        @JvmStatic
        fun <T> fromJson(bytes: ByteArray?, c: Class<T>): T? = if (bytes == null) null else jsonMapper.readValue(bytes, c)

        @JvmStatic
        fun <T> fromJson(str: String?, c: Class<T>): T = jsonMapper.readValue(str ?: "", c)

        @JvmStatic
        fun toJson(o: Any?): String = if (o == null) "" else jsonMapper.writeValueAsString(o)

        @JvmStatic
        fun <T> convert(o: Any, c: Class<T>): T = fromJson(toJson(o), c)

        @JvmStatic
        fun fromJsonExisting(json: String, o: Any): Any = jsonMapper.readerForUpdating(o).readValue(json)

        @JvmStatic
        fun <T> fromJsonExisting(json: String, o: T, c: Class<T>): Any = jsonMapper.readerForUpdating(o).readValue(json)
    }
}

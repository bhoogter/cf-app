package com.wincdspro.app.data

import android.content.Context
import com.wincdspro.app.util.FileUtil
import com.wincdspro.app.util.Mapper
import java.io.File

abstract class StorageBacking {
    companion object {
        @JvmStatic
        fun <T> init(utilId: String, context: Context, c: Class<T>, data: T?, empty: T?): T? {
            var iData = data
            if (iData == null) iData = load(utilId, context, c, empty)
            if (iData == null) iData = empty
            return iData
        }

        @JvmStatic
        fun <T> save(utilId: String, context: Context, data: T?, empty: T?): T? {
            var iData = data
            if (iData == null) iData = empty
            val contents = Mapper.toJson(iData)
            FileUtil.writeFile(storageFileName(context, utilId), contents, true)
            return iData
        }

        @JvmStatic
        fun <T> load(utilId: String, context: Context, c: Class<T>, empty: T?): T? {
            val contents = FileUtil.readEntireFile(storageFileName(context, utilId))
            return if (contents.isEmpty()) empty else Mapper.fromJson(contents, c)
        }

        private fun storageBaseName(utilId: String): String = "util-$utilId.json"
        private fun storageBaseDir(): String = "util"
        private fun storageFileName(context: Context, utilId: String): String = internalFileName(context, storageBaseName(utilId), storageBaseDir())

        @JvmStatic
        fun cacheFileName(context: Context, filename: String, subFolder: String = ""): String {
            val f = File("${context.cacheDir}${File.separator}$subFolder")
            if (!f.exists()) f.mkdir()
            return "${context.cacheDir}${File.separator}${subFolder}${if (subFolder.isEmpty()) "" else File.separator}$filename"
        }

        @JvmStatic
        fun internalFileName(context: Context, filename: String, subFolder: String = ""): String {
            val f = File("${context.filesDir}${File.separator}$subFolder")
            if (!f.exists()) f.mkdir()
            return "${context.filesDir}${File.separator}${subFolder}${if (subFolder.isEmpty()) "" else File.separator}$filename"
        }

        @JvmStatic
        fun cacheFile(context: Context, filename: String, subFolder: String = "") = File(cacheFileName(context, filename, subFolder))

        @JvmStatic
        fun internalFile(context: Context, filename: String, subFolder: String = "") = File(internalFileName(context, filename, subFolder))
    }
}

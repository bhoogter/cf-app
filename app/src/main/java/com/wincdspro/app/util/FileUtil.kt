package com.wincdspro.app.util

import java.io.File

class FileUtil {
    companion object {
        fun readEntireFile(fn: String): String = readEntireFile(File(fn))
        fun readEntireFile(f: File): String = if (f.exists()) f.readText() else ""

        fun readFile(fn: String, startLine: Int = 0, numLines: Int = 1): String = readFile(File(fn), startLine, numLines)
        fun readFile(f: File, startLine: Int = 0, numLines: Int = 1): String = if (f.exists()) f.bufferedReader().readLines().subList(startLine, startLine + numLines - 1).joinToString("\n") else ""

        fun writeFile(fn: String, data: String, overwrite: Boolean = false, preventNl: Boolean = false): Boolean = writeFile(File(fn), data, overwrite, preventNl)
        fun writeFile(f: File, data: String, overwrite: Boolean = false, preventNl: Boolean = false): Boolean {
            var localData = data
            while (preventNl && localData.isNotEmpty() && localData.endsWith("\n")) localData = data.substring(0, localData.length - 1)
            if (overwrite) f.writeText(data) else f.appendText(data)
            return true
        }
    }
}

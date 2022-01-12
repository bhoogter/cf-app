package com.wincdspro.app.util

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter
import java.util.Locale

@Suppress("UNUSED_PARAMETER")
class WLogger {
    companion object {
        private const val TAG = "WinCDSPro"
        private const val MAX_SIZE = 50

        private val logs = mutableListOf<String>()

        private fun recordLog(lvl: String, msg: String): String {
            logs.add("${lvl.uppercase(Locale.ROOT)}: $msg")
            while (logs.size > MAX_SIZE) logs.removeAt(0)
            return msg
        }

        fun getLogs() = logs.toList()
        fun clear() = logs.clear()

        @JvmStatic
        fun fatal(m: String): Boolean = Log.e(TAG, recordLog("FATAL", m)) != -1 || true

        @JvmStatic
        fun fatal(m: String, e: Throwable): Boolean = Log.e(TAG, recordLog("FATAL", m), e) != -1 || true

        @JvmStatic
        fun error(m: String): Boolean = Log.e(TAG, recordLog("ERROR", m)) != -1 || true

        @JvmStatic
        fun error(m: String, e: Throwable): Boolean = Log.e(TAG, recordLog("ERROR", m), e) != -1 || true

        @JvmStatic
        fun warn(m: String): Boolean = Log.w(TAG, recordLog("WARN", m)) != -1 || true

        @JvmStatic
        fun warn(m: String, e: Throwable): Boolean = Log.w(TAG, recordLog("WARN", m), e) != -1 || true

        @JvmStatic
        fun info(m: String): Boolean = Log.i(TAG, recordLog("INFO", m)) != -1 || true

        @JvmStatic
        fun info(m: String, e: Throwable): Boolean = Log.i(TAG, recordLog("INFO", m), e) != -1 || true

        @JvmStatic
        fun debug(m: String): Boolean = Log.d(TAG, recordLog("DEBUG", m)) != -1 || true

        @JvmStatic
        fun debug(m: String, e: Throwable): Boolean = Log.d(TAG, recordLog("DEBUG", m), e) != -1 || true

        @JvmStatic
        fun trace(m: String): Boolean = Log.v(TAG, recordLog("DEBUG", m)) != -1 || true

        @JvmStatic
        fun trace(m: String, e: Throwable): Boolean = Log.v(TAG, recordLog("DEBUG", m), e) != -1 || true

        @JvmStatic
        fun stackTraceString(t: Throwable?): String = if (t == null) "" else {
            val sw = StringWriter()
            t.printStackTrace(PrintWriter(sw))

            if (t.cause != null) {
                sw.append("\n--> Caused by [${t.cause?.javaClass?.simpleName ?: "UNKNOWN"}]: ${t.cause?.message ?: "..."}\n${stackTraceString(t.cause)}")
            }
            sw.toString()
        }
    }

    fun isLoggingEnabled(): Boolean = true
    fun setLoggingEnabled(loggingEnabled: Boolean) {}
    fun getLoggingLevel(): Int = 0
    fun setLoggingLevel(loggingLevel: Int) {}
    fun shouldLog(logLevel: Int): Boolean = true
    fun v(tag: String?, msg: String?) = if (trace((tag ?: "") + (msg ?: ""))) Unit else Unit
    fun v(tag: String?, msg: String?, t: Throwable?) = if (trace((tag ?: "") + (msg ?: ""), t ?: RuntimeException("..."))) Unit else Unit
    fun d(tag: String?, msg: String?) = if (debug((tag ?: "") + (msg ?: ""))) Unit else Unit
    fun d(tag: String?, msg: String?, t: Throwable?) = if (debug((tag ?: "") + (msg ?: ""), t ?: RuntimeException("..."))) Unit else Unit
    fun i(tag: String?, msg: String?) = if (info((tag ?: "") + (msg ?: ""))) Unit else Unit
    fun i(tag: String?, msg: String?, t: Throwable?) = if (info((tag ?: "") + (msg ?: ""), t ?: RuntimeException("..."))) Unit else Unit
    fun w(tag: String?, msg: String?) = if (warn((tag ?: "") + (msg ?: ""))) Unit else Unit
    fun w(tag: String?, msg: String?, t: Throwable?) = if (warn((tag ?: "") + (msg ?: ""), t ?: RuntimeException("..."))) Unit else Unit
    fun e(tag: String?, msg: String?) = if (error((tag ?: "") + (msg ?: ""))) Unit else Unit
    fun e(tag: String?, msg: String?, t: Throwable?) = if (error((tag ?: "") + (msg ?: ""), t ?: RuntimeException("..."))) Unit else Unit
    fun wtf(tag: String?, msg: String?) = if (fatal((tag ?: "") + (msg ?: ""))) Unit else Unit
    fun wtf(tag: String?, msg: String?, t: Throwable?) = if (fatal((tag ?: "") + (msg ?: ""), t ?: RuntimeException("..."))) Unit else Unit
}

package com.wincdspro.app.data

import android.content.Context
import com.wincdspro.app.util.SettingsManager

class PhysicalInventoryData {
    companion object {
        private const val ID = "PhysicalInventory"

        private var data: PhysicalInventoryInfo? = null

        private fun load(context: Context) = StorageBacking.load(ID, context, PhysicalInventoryInfo::class.java, PhysicalInventoryInfo())
        private fun save(context: Context, setData: PhysicalInventoryInfo? = null) = StorageBacking.save(ID, context, setData ?: data, PhysicalInventoryInfo())
        private fun init(context: Context): PhysicalInventoryInfo {
            data = StorageBacking.init(ID, context, PhysicalInventoryInfo::class.java, data, PhysicalInventoryInfo())
            if (data == null) data = load(context)
            if (data == null) data = save(context)
            return data!!
        }

        fun clearStyles(context: Context) = save(context, init(context).apply { styles = mutableMapOf(); lastStyle = "" })
        fun getLastStyle(context: Context): String = init(context).lastStyle

        fun recordStyle(context: Context, style: String) = save(context, init(context).apply { styles[style] = styles.computeIfAbsent(style) { 0 } + 1; lastStyle = style })

        fun totalStyles(context: Context): Int = init(context).styles.keys.count()
        fun totalItems(context: Context): Int = init(context).styles.values.sum()

        fun generateReport(context: Context): String = init(context).styles.keys.joinToString("\n") { k -> "${SettingsManager.storeNo},$k,${init(context).styles[k]}" }
    }

    data class PhysicalInventoryInfo(
        var styles: MutableMap<String, Int> = mutableMapOf(),
        var lastStyle: String = "",
    )
}

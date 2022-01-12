package com.wincdspro.app.util

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser

class CsvUtil {
    companion object {
        @JvmStatic
        fun csvField(line: String, field: Int = 1, dflt: String = ""): String {
            val csv = CSVParser.parse(line, CSVFormat.DEFAULT)
            val records = csv.records
            val record = records[0]
            if (field < 0 || field >= record.count()) return dflt
            return record[field].toString()
        }

        @JvmStatic
        fun csvLine(vararg parts: String): String {
            return parts
                .map { if (!it.contains("\"")) it else ("\"" + it.replace("\"", "\"\"") + "\"") }
                .joinToString(",")
        }
    }
}

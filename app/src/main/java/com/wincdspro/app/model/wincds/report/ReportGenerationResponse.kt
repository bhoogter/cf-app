package com.wincdspro.app.model.wincds.report

import com.wincdspro.app.mapper.ToJson
import java.util.Date

class ReportGenerationResponse(
    var result: Boolean? = false,
    var date: Date? = null,
    var report: String? = null,
    var reportName: String? = null,
    var resultId: String? = null,
    var ready: Boolean? = null,
) : ToJson()

package com.wincdspro.app.client.api.v1

import com.wincdspro.app.client.api.ApiBase.Companion.API_V1_L
import com.wincdspro.app.model.wincds.report.ReportGenerationResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ReportApi {
    companion object {
        private const val REPORT = "${API_V1_L}report"
    }

    @POST("$REPORT/generate/{reportType}")
    fun postReportRequest(@Path("storeNo") storeNo: Int, @Path("reportType") reportType: String, @QueryMap(encoded = true) args: Map<String, String>): Call<ReportGenerationResponse?>

    @HEAD("$REPORT/result/{resultId}")
    fun headReportResultReady(@Path("storeNo") storeNo: Int, @Path("resultId") resultId: String): Call<Void>

    @GET("$REPORT/result/{resultId}")
    fun getReportResult(@Path("storeNo") storeNo: Int, @Path("resultId") resultId: String): Call<ResponseBody>
}

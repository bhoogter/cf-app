package com.wincdspro.app.client.api.v1

import com.wincdspro.app.client.api.ApiBase.Companion.API_V1
import com.wincdspro.app.model.wincds.request.PhysicalInventoryResponse
import com.wincdspro.app.model.wincds.request.tracking.PullOrderRequest
import com.wincdspro.app.model.wincds.request.tracking.RecPoRequest
import com.wincdspro.app.model.wincds.request.tracking.ResultStatusResponse
import com.wincdspro.app.model.wincds.request.tracking.StkLocRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PhysicalInventoryApi {
    companion object {
        private const val PHYSINV = API_V1
    }

    @POST("${PHYSINV}physicalinv")
    fun postPhysicalInventoryReport(@Body report: String): Call<PhysicalInventoryResponse?>

    @POST("${PHYSINV}recpo")
    fun postTrackingRecPo(@Body report: RecPoRequest): Call<ResultStatusResponse?>

    @POST("${PHYSINV}pullorders")
    fun postTrackingPullOrder(@Body report: PullOrderRequest): Call<ResultStatusResponse?>

    @POST("${PHYSINV}stocklocations")
    fun postTrackingStkLoc(@Body report: StkLocRequest): Call<ResultStatusResponse?>
}

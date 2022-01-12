package com.wincdspro.app.client.api.v1

import com.wincdspro.app.client.api.ApiBase.Companion.API_V1_L
import com.wincdspro.app.model.wincds.collection.PoNoCollection
import com.wincdspro.app.model.wincds.core.Po
import com.wincdspro.app.model.wincds.request.newsale.NewSaleRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PoApi {
    companion object {
        private const val POS = "${API_V1_L}po"
    }

    @GET("$POS")
    fun getPoNoList(): Call<PoNoCollection?>

    @GET("$POS/{poNo}")
    fun getPoByPoNo(@Path("poNo") poNo: Int): Call<Po?>

    @POST(POS)
    fun putPoUpdateByPoNo(@Path("poNo") poNo: Int, @Body saleRequest: NewSaleRequest): Call<Po?>
}

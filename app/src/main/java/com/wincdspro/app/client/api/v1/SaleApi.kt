package com.wincdspro.app.client.api.v1

import com.wincdspro.app.client.api.ApiBase.Companion.API_V1_L
import com.wincdspro.app.model.wincds.collection.SaleNoCollection
import com.wincdspro.app.model.wincds.core.Sale
import com.wincdspro.app.model.wincds.request.newsale.NewSaleRequest
import com.wincdspro.app.model.wincds.request.newsale.NewSaleResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SaleApi {
    companion object {
        private const val SALES = "${API_V1_L}sale"
    }

    @GET("$SALES")
    fun getSaleList(@Path("storeNo") storeNo: Int): Call<SaleNoCollection?>

    @GET("$SALES/{saleNo}")
    fun getSale(@Path("storeNo") storeNo: Int, @Path("saleNo") saleNo: String): Call<Sale?>

    @POST(SALES)
    fun postSaleCreate(@Path("storeNo") storeNo: Int, @Body saleRequest: NewSaleRequest): Call<NewSaleResponse?>
}

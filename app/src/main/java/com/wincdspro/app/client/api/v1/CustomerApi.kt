package com.wincdspro.app.client.api.v1

import com.wincdspro.app.client.api.ApiBase
import com.wincdspro.app.client.api.ApiBase.Companion.API_V1_L
import com.wincdspro.app.model.wincds.core.Mail
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerApi : ApiBase {
    companion object {
        private const val CUSTOMER = "${API_V1_L}customer"
    }

    @GET("$CUSTOMER/{mailIndex}")
    fun getCustomer(@Path("storeNo") storeNo: Int, @Path("mailIndex") mailIndex: Int): Call<Mail?>

    @GET("$CUSTOMER/phone/{ani}")
    fun getCustomerByPhone(@Path("storeNo") storeNo: Int, @Path("ani") ani: String): Call<Mail?>

    @PUT("$CUSTOMER/{mailIndex}")
    fun putCustomerUpdate(@Path("storeNo") storeNo: Int, @Path("mailIndex") mailIndex: Int, @Body mail: Mail): Call<Mail>

    @POST(CUSTOMER)
    fun postCustomerCreate(@Path("storeNo") storeNo: Int, @Body mail: Mail): Call<Mail>
}

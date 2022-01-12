package com.wincdspro.app.client.api.v1

import com.wincdspro.app.client.api.ApiBase
import com.wincdspro.app.client.api.ApiBase.Companion.API_V1
import com.wincdspro.app.client.api.ApiBase.Companion.API_V1_L
import com.wincdspro.app.model.wincds.collection.SearchItemCollection
import com.wincdspro.app.model.wincds.collection.SearchMailCollection
import com.wincdspro.app.model.wincds.collection.SearchPoCollection
import com.wincdspro.app.model.wincds.collection.SearchSaleCollection
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApi : ApiBase {
    companion object {
        private const val SEARCH = "${API_V1}find"
        private const val SEARCH_L = "${API_V1_L}find"
    }

    @GET("$SEARCH_L/customer/{key}/{search}")
    fun searchCustomer(@Path("storeNo") storeNo: Int, @Path("key") key: String, @Path("search") search: String): Call<SearchMailCollection?>

    @GET("$SEARCH_L/sale/{key}/{search}")
    fun searchSale(@Path("storeNo") storeNo: Int, @Path("key") key: String, @Path("search") search: String): Call<SearchSaleCollection?>

    @GET("$SEARCH/item/{key}/{search}")
    fun searchItem(@Path("key") key: String, @Path("search") search: String): Call<SearchItemCollection?>

    @GET("$SEARCH/po/{key}/{search}")
    fun searchPo(@Path("key") key: String, @Path("search") search: String): Call<SearchPoCollection?>
}

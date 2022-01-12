package com.wincdspro.app.client.api.v1

import com.wincdspro.app.client.api.ApiBase.Companion.API_V1
import com.wincdspro.app.model.wincds.core.Item
import com.wincdspro.app.model.wincds.core.collection.ItemDetailCollection
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InventoryApi {
    companion object {
        private const val INVENTORY = "${API_V1}inventory"
    }

    @GET("$INVENTORY/{style}")
    fun getItem(@Path("style") style: String): Call<Item?>

    @GET("$INVENTORY/detail/{style}")
    fun getItemDetail(@Path("style") style: String): Call<ItemDetailCollection?>

    @GET("$INVENTORY/image/{style}")
    fun getItemImage(@Path("style") style: String): Call<ResponseBody>
}

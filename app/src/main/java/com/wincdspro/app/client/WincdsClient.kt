package com.wincdspro.app.client

import android.os.Handler
import android.os.Looper
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.client.api.v1.ConnectionApi
import com.wincdspro.app.client.api.v1.ConnectionAssistApi
import com.wincdspro.app.client.api.v1.CustomerApi
import com.wincdspro.app.client.api.v1.InventoryApi
import com.wincdspro.app.client.api.v1.PhysicalInventoryApi
import com.wincdspro.app.client.api.v1.PoApi
import com.wincdspro.app.client.api.v1.ReportApi
import com.wincdspro.app.client.api.v1.SaleApi
import com.wincdspro.app.client.api.v1.SearchApi
import com.wincdspro.app.client.base.BaseClient
import com.wincdspro.app.client.website.RestErrorApi
import com.wincdspro.app.client.website.WebsiteClient
import com.wincdspro.app.constant.WincdsApi.Companion.PATH_ITEM_IMAGE
import com.wincdspro.app.model.auth.AuthResponse
import com.wincdspro.app.model.auth.AuthToken
import com.wincdspro.app.model.settings.AllSettings
import com.wincdspro.app.model.wincds.collection.PoNoCollection
import com.wincdspro.app.model.wincds.collection.SaleNoCollection
import com.wincdspro.app.model.wincds.collection.SearchItemCollection
import com.wincdspro.app.model.wincds.collection.SearchMailCollection
import com.wincdspro.app.model.wincds.collection.SearchPoCollection
import com.wincdspro.app.model.wincds.collection.SearchSaleCollection
import com.wincdspro.app.model.wincds.connect.ConnectionAssistCollection
import com.wincdspro.app.model.wincds.core.Item
import com.wincdspro.app.model.wincds.core.Mail
import com.wincdspro.app.model.wincds.core.Po
import com.wincdspro.app.model.wincds.core.Sale
import com.wincdspro.app.model.wincds.core.collection.ItemDetailCollection
import com.wincdspro.app.model.wincds.report.ReportGenerationResponse
import com.wincdspro.app.model.wincds.request.PhysicalInventoryResponse
import com.wincdspro.app.model.wincds.request.newsale.NewSaleRequest
import com.wincdspro.app.model.wincds.request.newsale.NewSaleResponse
import com.wincdspro.app.model.wincds.request.tracking.PullOrderRequest
import com.wincdspro.app.model.wincds.request.tracking.RecPoRequest
import com.wincdspro.app.model.wincds.request.tracking.ResultStatusResponse
import com.wincdspro.app.model.wincds.request.tracking.StkLocRequest
import com.wincdspro.app.util.Mapper
import com.wincdspro.app.util.SettingsManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

open class WincdsClient(
    lScheme: String,
    lHost: String,
    lPort: Int,
    lPass: String?,
) : BaseClient(lScheme, lHost, lPort) {
    companion object {
        private const val DEFAULT_TIMEOUT = 30_000
        private const val AUTHORIZATION_HEADER = "authorization"
        private const val BEARER = "Bearer"

        private const val DEFAULT_PORT = 8080

        private const val PARAM_ACCESS_KEY = "access_key"
        private const val PARAM_USERNAME = "username"
        private const val PARAM_PASSWORD = "password"
        private const val PARAM_STORE = "store"

        var additionalInterceptors: List<Interceptor> = listOf()
        private var pass: String? = null

        private var token: String?
            get() = SettingsManager.authToken.token
            set(v) {
                SettingsManager.authToken = if (v == null) AuthToken() else AuthToken.decode(v)
            }

        var lastRequest: String = ""
        var lastStatusCode: Int = DEFAULT_PORT
        var lastResponse: String = ""
        var lastError: String = ""

        @JvmStatic
        fun threaded(r: () -> Unit) {
            Handler(Looper.getMainLooper()).post {
                r.invoke()
            }
        }
    }

    constructor() : this(HTTP, SettingsManager.serverSettings.serverHost, SettingsManager.serverSettings.serverPort, SettingsManager.serverSettings.serverPass)

    constructor(host: String, port: Int, pass: String) : this(HTTP, host, port, pass)

    init {
        pass = lPass
    }

    private var clientInternal: Retrofit? = null
    fun refreshClient() {
        clientInternal = null
    }

    val client: Retrofit
        get() {
            if (clientInternal == null) clientInternal = createClient()
            return clientInternal!!
        }

    private val httpClient = OkHttpClient.Builder().apply {
        addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            if ((token ?: "").isNotEmpty())
                requestBuilder.addHeader(AUTHORIZATION_HEADER, "$BEARER $token")
            requestBuilder.addHeader("Connection", "close")
            chain.proceed(requestBuilder.build())
        }
        additionalInterceptors.forEach { addInterceptor(it) }
    }.build()

    private fun createClient(): Retrofit = Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(httpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(JacksonConverterFactory.create(Mapper.getMapper()))
        .build()

    private val apiConnectionAssist: ConnectionAssistApi = client.create(ConnectionAssistApi::class.java)
    private val apiConnection: ConnectionApi = client.create(ConnectionApi::class.java)
    private val apiCustomer: CustomerApi = client.create(CustomerApi::class.java)
    private val apiSearch: SearchApi = client.create(SearchApi::class.java)
    private val apiPo: PoApi = client.create(PoApi::class.java)
    private val apiInventory: InventoryApi = client.create(InventoryApi::class.java)
    private val apiPhysicalInv: PhysicalInventoryApi = client.create(PhysicalInventoryApi::class.java)
    private val apiSale: SaleApi = client.create(SaleApi::class.java)
    private val apiReport: ReportApi = client.create(ReportApi::class.java)
    private val apiRestError: RestErrorApi = client.create(RestErrorApi::class.java)

// ////////////////////////////////////////////////////////////////////////////////
// Api Access
// ////////////////////////////////////////////////////////////////////////////////

    fun postConnectionAssist(name: String, host: String, port: String, pass: String, result: (status: Int, result: String?) -> Unit) =
        enqueueForString(result) { apiConnectionAssist.postConnectionAssist(name, host, port, pass) }

    fun getConnectionAssist(result: (status: Int, result: ConnectionAssistCollection?) -> Unit) =
        enqueueForObject(result) { apiConnectionAssist.getConnectionAssist() }

    fun deleteConnectionAssist(result: (status: Int, result: String?) -> Unit) =
        enqueueForString(result) { apiConnectionAssist.deleteConnectionAssist() }

    fun verify(result: (status: Int, result: String?) -> Unit) = enqueueForString(result) { apiConnection.verify() }
    fun help(result: (status: Int, result: String?) -> Unit) = enqueueForString(result) { apiConnection.help() }
    fun connectionTest(result: (status: Int, result: String?) -> Unit) = enqueueForString(result) { apiConnection.connectionTest() }
    fun getSettings(result: ((status: Int, info: AllSettings?) -> Unit)) = enqueueForObject(result) { apiConnection.getSettings() }

    fun getCustomerByPhone(store: Int, ani: String, result: (status: Int, info: Mail?) -> Unit) = enqueueForObject(result) { apiCustomer.getCustomerByPhone(store, ani) }

    fun getCustomer(store: Int, mailIndex: Int, result: (status: Int, info: Mail?) -> Unit) = enqueueForObject(result) { apiCustomer.getCustomer(store, mailIndex) }

    fun putCustomerUpdate(store: Int, mailIndex: Int, mail: Mail, result: (status: Int, info: Mail?) -> Unit) =
        enqueueForObject(result) { apiCustomer.putCustomerUpdate(store, mailIndex, mail) }

    fun postCustomerCreate(store: Int, mail: Mail, result: (status: Int, info: Mail?) -> Unit) = enqueueForObject(result) { apiCustomer.postCustomerCreate(store, mail) }

    fun searchCustomer(store: Int, key: WincdsSearchType, ref: String, result: ((status: Int, info: SearchMailCollection?) -> Unit)) =
        enqueueForObject(result) { apiSearch.searchCustomer(store, key.toString().toLowerCase(), ref) }

    fun searchSale(store: Int, key: WincdsSearchType, ref: String, result: ((status: Int, info: SearchSaleCollection?) -> Unit)) =
        enqueueForObject(result) { apiSearch.searchSale(store, key.toString().toLowerCase(), ref) }

    fun searchItem(key: WincdsSearchType, ref: String, result: ((status: Int, info: SearchItemCollection?) -> Unit)) =
        enqueueForObject(result) { apiSearch.searchItem(key.toString().toLowerCase(), ref) }

    fun searchPo(key: WincdsSearchType, ref: String, result: ((status: Int, info: SearchPoCollection?) -> Unit)) =
        enqueueForObject(result) { apiSearch.searchPo(key.toString().toLowerCase(), ref) }

    fun getPoNoList(result: ((status: Int, info: PoNoCollection?) -> Unit)) = enqueueForObject(result) { apiPo.getPoNoList() }
    fun getPoByPoNo(poNo: Int, result: ((status: Int, info: Po?) -> Unit)) = enqueueForObject(result) { apiPo.getPoByPoNo(poNo) }
    fun putPoUpdateByPoNo(poNo: Int, request: NewSaleRequest, result: ((status: Int, info: Po?) -> Unit)) = enqueueForObject(result) { apiPo.putPoUpdateByPoNo(poNo, request) }

    fun getItem(style: String, result: ((status: Int, info: Item?) -> Unit)) = enqueueForObject(result) { apiInventory.getItem(style) }
    fun getItemDetail(style: String, result: ((status: Int, info: ItemDetailCollection?) -> Unit)) = enqueueForObject(result) { apiInventory.getItemDetail(style) }

    fun itemImageUrl(style: String, withBase: Boolean = true) = "${if (withBase) getBaseUrl() else ""}${pInv(PATH_ITEM_IMAGE, style)}"
    fun getItemImage(style: String, result: ((status: Int, info: InputStream?) -> Unit)) = enqueueForInputStream(result) { apiInventory.getItemImage(style) }

    fun getFxFile(filename: String, result: ((status: Int, info: InputStream?) -> Unit)) = enqueueForInputStream(result) { apiConnection.getFxFile(filename) }
    fun getStoreLogo(storeNo: Int, result: ((status: Int, info: InputStream?) -> Unit)) = enqueueForInputStream(result) { apiConnection.getStoreLogo(storeNo) }

    fun postPhysicalInventoryReport(request: String, result: ((status: Int, PhysicalInventoryResponse?) -> Unit)) = enqueueForObject(result) { apiPhysicalInv.postPhysicalInventoryReport(request) }
    fun postTrackingRecPo(request: RecPoRequest, result: ((status: Int, ResultStatusResponse?) -> Unit)) = enqueueForObject(result) { apiPhysicalInv.postTrackingRecPo(request) }
    fun postTrackingPullOrder(request: PullOrderRequest, result: ((status: Int, ResultStatusResponse?) -> Unit)) = enqueueForObject(result) { apiPhysicalInv.postTrackingPullOrder(request) }
    fun postTrackingStkLoc(request: StkLocRequest, result: ((status: Int, ResultStatusResponse?) -> Unit)) = enqueueForObject(result) { apiPhysicalInv.postTrackingStkLoc(request) }

    fun getSaleNoList(store: Int, result: ((status: Int, info: SaleNoCollection?) -> Unit)) = enqueueForObject(result) { apiSale.getSaleList(store) }

    fun getSale(store: Int, saleNo: String, result: ((status: Int, info: Sale?) -> Unit)) = enqueueForObject(result) { apiSale.getSale(store, saleNo) }

    fun postSaleCreate(store: Int, request: NewSaleRequest, result: ((status: Int, NewSaleResponse?) -> Unit)) = enqueueForObject(result) { apiSale.postSaleCreate(store, request) }

    fun postReportRequest(store: Int, reportType: String, args: Map<String, String>, result: ((status: Int, ReportGenerationResponse?) -> Unit)) =
        enqueueForObject(result) { apiReport.postReportRequest(store, reportType, args) }

    fun headReportResultReady(store: Int, resultId: String, result: ((status: Int) -> Unit)) = enqueueForStatus(result) { apiReport.headReportResultReady(store, resultId) }
    fun getReportResult(store: Int, resultId: String, result: ((status: Int, info: InputStream?) -> Unit)) = enqueueForInputStream(result) { apiReport.getReportResult(store, resultId) }

// ////////////////////////////////////////////////////////////////////////////////
// Path Support
// ////////////////////////////////////////////////////////////////////////////////

    private fun pLoc(p: String, l: Int, v1: String = "", v2: String = "", v3: String = "") =
        p.replace("#", l.toString()).replace("$3", v3).replace("$2", v2).replace("$1", v1).replace("$", v1)

    private fun pInv(p: String, v1: String = "", v2: String = "", v3: String = "") =
        p.replace("$3", v3).replace("$2", v2).replace("$1", v1).replace("$", v1)

    private fun qs(args: Map<String, String>): String = "?" + args.map { (key, value) -> "$key=$value" }.joinToString("&")

// ////////////////////////////////////////////////////////////////////////////////
// Error Recording Support
// ////////////////////////////////////////////////////////////////////////////////

    override fun recordFailure(errorText: String, t: Throwable?, call: Call<*>?) {
        super.recordFailure(errorText, t, call)
        val request = call?.request()
        WebsiteClient.recordRestError(request?.method() ?: "", request?.url().toString(), request?.body().toString(), errorText)
    }

// ////////////////////////////////////////////////////////////////////////////////
// Auth Methods
// ////////////////////////////////////////////////////////////////////////////////

    override fun authCheck(statusCode: Int, retryCallback: () -> Unit?, failCallback: () -> Unit?): Boolean {
        if (statusCode != HTTP_UNAUTHORIZED) return true
        authWincds(retryCallback, failCallback)
        return false
    }

    private fun authWincds(retryCallback: () -> Unit?, failCallback: () -> Unit?) {
        token = null
        val userAuth = if (SettingsManager.userSettings.salesman.toString() == "") ""
        else "&$PARAM_USERNAME=${SettingsManager.userSettings.salesman}&$PARAM_PASSWORD=${SettingsManager.userSettings.salesmanPass}&$PARAM_STORE=${SettingsManager.userSettings.storeNo}"
        val params = "$PARAM_ACCESS_KEY=$pass$userAuth"

        SettingsManager.loggingIn = true
        val call = apiConnection.authorize(params)
        call.enqueue(object : Callback<AuthResponse?> {
            override fun onResponse(call: Call<AuthResponse?>, response: Response<AuthResponse?>) {
                val code = response.code()
                val auth = response.body()
                if (code == SC_OK) recordSuccess(call, response.code(), auth?.toString() ?: "<UNPARSABLE>")
                else recordFailure("Login failure [$code]: body=${response.body()}")
                authResponse(retryCallback, failCallback, response.code(), response.body())
            }

            override fun onFailure(call: Call<AuthResponse?>, t: Throwable) {
                recordFailure("", t)
                authResponse(retryCallback, failCallback, 0, null)
            }
        })
    }

    private fun authResponse(retryCallback: () -> Unit?, failCallback: () -> Unit?, status: Int, body: AuthResponse?) {
        if (status == HTTP_OK && body != null) {
            token = body.token
            retryCallback.invoke()
        } else
            failCallback.invoke()
    }
}

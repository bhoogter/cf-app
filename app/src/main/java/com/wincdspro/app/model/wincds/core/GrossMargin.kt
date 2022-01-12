package com.wincdspro.app.model.wincds.core

import com.fasterxml.jackson.annotation.JsonProperty
import com.wincdspro.app.mapper.ToJson

data class GrossMargin(
    var marginLine: Int? = 0,
    var saleNo: String? = "",
    var quantity: Double? = 0.0,
    var style: String? = "",
    var vendor: String? = "",
    var desc: String? = "",
    var porD: String? = "",
    var deptNo: String? = "",
    var vendorNo: String? = "",
    var commission: String? = "",
    var cost: Double? = 0.0,
    var itemFreight: Double? = 0.0,
    var sellPrice: Double? = 0.0,
    var salesman: String? = "",
    var status: String? = "",
    var location: Int? = 0,
    var sellDate: String? = "",
    var delDate: String? = "",
    var rn: Int? = 0,
    var store: Int? = 0,
    var name: String? = "",
    var shipDate: String? = "",
    var gM: Double? = 0.0,
    var tele: String? = "",
    var detail: Int? = 0,
    var mailIndex: Int? = 0,
    var SS: String? = "",
    var delPrint: String? = "",
    var pullPrint: String? = "",
    var commPd: Double? = 0.0,
    var fldPosted: Int? = 0,
    var spiff: Double? = 0.0,
    var salesSplit: String? = "",
    var stopStart: String? = "",
    var stopEnd: String? = "",
    @get:JsonProperty("isPackage")
    @set:JsonProperty("isPackage")
    var isPackage: Int? = 0,
    var packSell: Double? = 0.0,
    var packSellGm: Double? = 0.0,
    var packSaleGm: Double? = 0.0,
    var transId: String? = "",
) : ToJson()

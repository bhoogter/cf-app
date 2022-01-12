package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.mapper.ToJson

data class Mail(
    var index: Int? = 0,
    var last: String? = "",
    var first: String? = "",
    var address: String? = "",
    var addAddress: String? = "",
    var city: String? = "",
    var zip: String? = "",
    var tele: String? = "",
    var tele2: String? = "",
    var tele3: String? = null,
    var special: String? = "",
    var type: String? = "",
    var custType: String? = "",
    var typeIndex: Long? = 0,
    var email: String? = "",
    var blank: String? = "",
    var business: Boolean? = false,
    var creditCard: String? = null,
    var expDate: String? = null,
    var taxRate: String? = "",
    var taxCode: Long? = 0,
    var taxZone: Long? = 0,
    var phoneLabel1: String? = null,
    var phoneLabel2: String? = null,
    var phoneLabel3: String? = null,

    var shipToLast: String? = null,
    var shipToFirst: String? = null,
    var shipToAddress: String? = null,
    var shipToCity: String? = null,
    var shipToZip: String? = null,
) : ToJson()

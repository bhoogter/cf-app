package com.wincdspro.app.model.wincds.core

import com.wincdspro.app.mapper.ToJson

class PoItem(
    var pOID: Int = 0,
    var poNo: Int? = null,
    var leaseNo: String? = null,
    var poDate: String? = null,
    var name: String? = null,
    var vendor: String? = null,
    var initialQuantity: Double = 0.0,
    var quantity: Double = 0.0,
    var style: String? = null,
    var desc: String? = null,
    var cost: Double = 0.0,
    var location: Int? = null,
    var soldTo: Int? = null,
    var shipTo: Int? = null,
    var note1: Int? = null,
    var note2: Int? = null,
    var note3: Int? = null,
    var note4: Int? = null,
    var poNotes: String? = null,
    var ackInv: String? = null,
    var printPO: String? = null,
    var posted: String? = null,
    var wcost: String? = null,
    var rn: String? = null,
    var detailNo: Int? = null,
    var marginLine: Int? = null,
    var shiptoName: String? = null,
    var shipToAddress: String? = null,
    var shipToCity: String? = null,
    var shipToTele: String? = null,
    var specialNote: String? = null,
    var dueDate: String? = null,
    var poRecDate: String? = null,
    var blank: String? = null,
) : ToJson()
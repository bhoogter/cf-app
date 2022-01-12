package com.wincdspro.app.constant

class WincdsApi {
    companion object {
        const val PATH_AUTHORIZE = "api/v1/authorize"
        const val PATH_TEST = "api/v1/connection-test"

        const val PATH_SETTINGS = "api/v1/settings"

        const val PATH_CUST_ANI = "api/v1/l/#/customer/phone/$1"
        const val PATH_CUST_CREATE = "api/v1/l/#/customer"
        const val PATH_CUST = "api/v1/l/#/customer/$1"
        const val PATH_SALE = "api/v1/l/#/sale/$"

        const val PATH_ITEM = "api/v1/inventory/$"
        const val PATH_ITEM_IMAGE = "api/v1/inventory/image/$"

        const val PATH_FIND_SALE = "api/v1/l/#/find/sale/$1/$2"
        const val PATH_FIND_CUST = "api/v1/l/#/find/customer/$1/$2"
        const val PATH_FIND_ITEM = "api/v1/find/item/$1/$2"

        const val PATH_POST_PHYSICALINV = "api/v1/physicalinv"
        const val PATH_POST_REC_PO = "api/v1/recpo"
        const val PATH_POST_PULL_ORDER = "api/v1/pullorders"
        const val PATH_POST_STOCK_LOCATIONS = "api/v1/stocklocations"

        const val PATH_POST_REPORT_GENERATE = "api/v1/report/generate/$1"
        const val PATH_GET_REPORT_RESULT = "api/v1/report/result/$1"

        const val PATH_POST_SALE_CREATE = "api/v1/l/#/sale"
    }
}

package com.wincdspro.app.client.api

interface ApiBase {
    companion object {
        private const val API_PREFIX_BASE = "api/"
        const val API_V1 = "${API_PREFIX_BASE}v1/"
        const val API_V1_L = "${API_V1}l/{storeNo}/"
    }
}

package com.wincdspro.app.model.wincds.collection

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.wincds.search.SearchSale

data class SearchSaleCollection(var results: List<SearchSale> = mutableListOf()) : ToJson()

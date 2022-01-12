package com.wincdspro.app.model.wincds.collection

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.wincds.search.SearchItem

data class SearchItemCollection(var results: List<SearchItem> = mutableListOf()) : ToJson()

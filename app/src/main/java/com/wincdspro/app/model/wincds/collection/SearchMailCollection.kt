package com.wincdspro.app.model.wincds.collection

import com.wincdspro.app.mapper.ToJson
import com.wincdspro.app.model.wincds.search.SearchMail

data class SearchMailCollection(var results: List<SearchMail> = mutableListOf()) : ToJson()

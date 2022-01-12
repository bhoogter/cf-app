package com.wincdspro.app.fragment.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.wincdspro.app.R
import com.wincdspro.app.activity.InventoryActivity
import com.wincdspro.app.client.WincdsSearchType
import com.wincdspro.app.fragment.autotype.LookupFragment
import com.wincdspro.app.model.wincds.collection.SearchPoCollection
import com.wincdspro.app.model.wincds.search.SearchPo

class PoLookupFragment : LookupFragment<SearchPoCollection, SearchPo, Int>() {
    private val inventoryActivity get() = (requireActivity() as InventoryActivity)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inventory_po_lookup, container, false)
    }

    override fun idTxtInput(): Int = R.id.inventory_po_text_polookup
    override fun getSearchOption(): WincdsSearchType = WincdsSearchType.PONO
    override fun performLookup(searchType: WincdsSearchType, input: String, result: (status: Int, body: SearchPoCollection?) -> Unit) = client.searchPo(getSearchOption(), input, result)
    override fun mapObjectToLines(c: SearchPoCollection): List<String> = c.results.map(::lineFunc)
    override fun lineFunc(i: SearchPo): String = "${i.poNo.toString().padEnd(7)} - ${i.vendor?.padEnd(15)} - ${i.poDate} - ${i.leaseNo}"
    override fun lineValue(s: String): Int = s.take(7).trim().toInt()
    override fun lookupFailed() {}
    override fun selected(item: Int, f: Fragment) = inventoryActivity.receivedPoNo(item, this)
}

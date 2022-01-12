package com.wincdspro.app.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.wincdspro.app.constant.ArgNames.Companion.ARG_SALENO
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_CUSTINFO
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_ITEMSELECT
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_LOOKUP
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_LOOKUP_CUSTNO
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_NEWSALEFINISH
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_NEWSALEITEMS
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_VIEWSALE
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_OPERATION_NEWSALE
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_OPERATION_VIEWSALE
import com.wincdspro.app.exception.OrderEntryException
import com.wincdspro.app.fragment.order.CustomerInfoFragment
import com.wincdspro.app.fragment.order.ItemSelectFragment
import com.wincdspro.app.fragment.order.SaleCreateFragment
import com.wincdspro.app.fragment.order.SaleFinishFragment
import com.wincdspro.app.fragment.order.SaleLookupFragment
import com.wincdspro.app.fragment.order.SaleViewFragment

class OrderActivity : FragmentActivity() {

    var mailIndex: Int = 0

    override fun initialFragment(operation: String): String = when (operation) {
        ORDER_OPERATION_VIEWSALE -> ORDER_FRAGMENT_LOOKUP
        ORDER_OPERATION_NEWSALE -> ORDER_FRAGMENT_NEWSALEITEMS
        else -> throw OrderEntryException("No initial segment: $operation")
    }

    override fun lookupFragment(fragmentName: String): Fragment = when (fragmentName) {
        ORDER_FRAGMENT_LOOKUP -> SaleLookupFragment()
        ORDER_FRAGMENT_VIEWSALE -> SaleViewFragment()
        ORDER_FRAGMENT_NEWSALEITEMS -> SaleCreateFragment()

        ORDER_FRAGMENT_LOOKUP_CUSTNO -> SaleLookupFragment()
        ORDER_FRAGMENT_CUSTINFO -> CustomerInfoFragment()
        ORDER_FRAGMENT_ITEMSELECT -> ItemSelectFragment()
        ORDER_FRAGMENT_NEWSALEFINISH -> SaleFinishFragment()
        else -> throw OrderEntryException("Invalid fragment: $fragmentName")
    }

    fun receivedSale(leaseNo: String, source: Fragment? = null) {
        val args = Bundle()
        args.putString(ARG_SALENO, leaseNo)

        val key = when (operation) {
            ORDER_OPERATION_VIEWSALE -> ORDER_FRAGMENT_VIEWSALE
            ORDER_OPERATION_NEWSALE -> ORDER_FRAGMENT_VIEWSALE
            else -> throw OrderEntryException("Invalid operation from receivedSale: $operation")
        }

        setFragment(key, args, source)
    }

    fun receivedCust(mailIndex: Int, source: Fragment) {
        this.mailIndex = mailIndex
        pushFragment(ORDER_FRAGMENT_CUSTINFO, null, popFragment(source))
    }
}

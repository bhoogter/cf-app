package com.wincdspro.app.activity

import androidx.fragment.app.Fragment
import com.wincdspro.app.constant.ArgNames.Companion.ARG_PONO
import com.wincdspro.app.constant.ArgNames.Companion.ARG_STYLE
import com.wincdspro.app.constant.OperationNames.Companion.INV_FRAGMENT_LOOKUP
import com.wincdspro.app.constant.OperationNames.Companion.INV_FRAGMENT_PHYSICALINV
import com.wincdspro.app.constant.OperationNames.Companion.INV_FRAGMENT_PURCHASE_ORDERS
import com.wincdspro.app.constant.OperationNames.Companion.INV_FRAGMENT_PURCHASE_ORDERS_LOOKUP
import com.wincdspro.app.constant.OperationNames.Companion.INV_FRAGMENT_VIEWITEM
import com.wincdspro.app.constant.OperationNames.Companion.INV_FRAGMENT_VIEWITEMDETAIL
import com.wincdspro.app.constant.OperationNames.Companion.INV_OPERATION_PHYSICAL_INV
import com.wincdspro.app.constant.OperationNames.Companion.INV_OPERATION_PURCHASE_ORDERS
import com.wincdspro.app.constant.OperationNames.Companion.INV_OPERATION_VIEWITEM
import com.wincdspro.app.exception.InventoryException
import com.wincdspro.app.fragment.inventory.ItemLookupFragment
import com.wincdspro.app.fragment.inventory.ItemViewDetailFragment
import com.wincdspro.app.fragment.inventory.ItemViewFragment
import com.wincdspro.app.fragment.inventory.PhysicalInventoryFragment
import com.wincdspro.app.fragment.inventory.PoLookupFragment
import com.wincdspro.app.fragment.inventory.PoViewFragment

class InventoryActivity : FragmentActivity() {
    override fun initialFragment(operation: String): String = when (operation) {
        INV_OPERATION_VIEWITEM -> INV_FRAGMENT_LOOKUP
        INV_OPERATION_PHYSICAL_INV -> INV_FRAGMENT_PHYSICALINV
        INV_OPERATION_PURCHASE_ORDERS -> INV_FRAGMENT_PURCHASE_ORDERS_LOOKUP
        else -> throw InventoryException("No initial segment: $operation")
    }

    override fun lookupFragment(fragmentName: String): Fragment = when (fragmentName) {
        INV_FRAGMENT_LOOKUP -> ItemLookupFragment()
        INV_FRAGMENT_VIEWITEM -> ItemViewFragment()
        INV_FRAGMENT_VIEWITEMDETAIL -> ItemViewDetailFragment()
        INV_FRAGMENT_PHYSICALINV -> PhysicalInventoryFragment()
        INV_FRAGMENT_PURCHASE_ORDERS_LOOKUP -> PoLookupFragment()
        INV_FRAGMENT_PURCHASE_ORDERS -> PoViewFragment()
        else -> throw InventoryException("Invalid fragment: $fragmentName")
    }

    fun receivedItemStyle(style: String, source: Fragment? = null) {
        val key = when (operation) {
            INV_OPERATION_VIEWITEM -> INV_FRAGMENT_VIEWITEM
            else -> throw InventoryException("Invalid operation: $operation")
        }

        setFragment(key, bundleArgs(mapOf(ARG_STYLE to style)), source)
    }

    fun receivedPoNo(poNo: Int, source: PoLookupFragment) {
        val key = when (operation) {
            INV_OPERATION_PURCHASE_ORDERS -> INV_FRAGMENT_PURCHASE_ORDERS
            else -> throw InventoryException("Invalid operation: $operation")
        }

        setFragment(key, bundleArgs(mapOf(ARG_PONO to poNo)), source)
    }
}

package com.wincdspro.app.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.activity.InventoryActivity
import com.wincdspro.app.constant.OperationNames.Companion.INV_OPERATION_PHYSICAL_INV
import com.wincdspro.app.constant.OperationNames.Companion.INV_OPERATION_PURCHASE_ORDERS
import com.wincdspro.app.constant.OperationNames.Companion.INV_OPERATION_VIEWITEM
import com.wincdspro.app.constant.PrivZones
import com.wincdspro.app.fragment.autotype.WincdsFragment

class MainMenuInventoryFragment : WincdsFragment() {
    private val mainActivity get() = (activity as MainActivity?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_inventory, container, false)
    }

    override fun onStart() {
        super.onStart()

        btnViewItem?.setOnClickListener { mainActivity?.launchOperation(PrivZones.INVENTORY_MANAGEMENT, InventoryActivity::class.java, INV_OPERATION_VIEWITEM) }
        btnPos?.setOnClickListener { mainActivity?.launchOperation(PrivZones.INVENTORY_MANAGEMENT_MANAGE_PURCHASE_ORDERS, InventoryActivity::class.java, INV_OPERATION_PURCHASE_ORDERS) }
        btnPhysInv?.setOnClickListener { mainActivity?.launchOperation(PrivZones.INVENTORY_MANAGEMENT, InventoryActivity::class.java, INV_OPERATION_PHYSICAL_INV) }
    }

    private val btnViewItem get() = activity?.findViewById<Button>(R.id.main_inventory_button_itemlookup)
    private val btnPos get() = activity?.findViewById<Button>(R.id.main_inventory_button_pos)
    private val btnPhysInv get() = activity?.findViewById<Button>(R.id.main_inventory_button_physicalinv)
}

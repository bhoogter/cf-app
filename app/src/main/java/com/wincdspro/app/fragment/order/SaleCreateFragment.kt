package com.wincdspro.app.fragment.order

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.wincdspro.app.R
import com.wincdspro.app.activity.OrderActivity
import com.wincdspro.app.adapter.BosLineAdapter
import com.wincdspro.app.adapter.BosLineModel
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_CREATED
import com.wincdspro.app.constant.ArgNames.Companion.ARG_LOC
import com.wincdspro.app.constant.ArgNames.Companion.ARG_POSITION
import com.wincdspro.app.constant.ArgNames.Companion.ARG_QUAN
import com.wincdspro.app.constant.ArgNames.Companion.ARG_SALENO
import com.wincdspro.app.constant.ArgNames.Companion.ARG_STATUS
import com.wincdspro.app.constant.ArgNames.Companion.ARG_STYLE
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_CUSTINFO
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_ITEMSELECT
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_LOOKUP_CUSTNO
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_NEWSALEFINISH
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_VIEWSALE
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.request.newsale.NewSaleFinishInfo
import com.wincdspro.app.model.wincds.request.newsale.NewSaleItem
import com.wincdspro.app.model.wincds.request.newsale.NewSaleRequest
import com.wincdspro.app.model.wincds.request.newsale.NewSaleResponse
import com.wincdspro.app.util.Mapper
import com.wincdspro.app.util.SettingsManager

class SaleCreateFragment : WincdsFragment() {
    private val orderActivity get() = requireActivity() as OrderActivity
    private val mailIndex get() = orderActivity.mailIndex
    private val itemCount: Int get() = lstItems?.count ?: 0
    private var items = mutableListOf<NewSaleItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_newsale_items, container, false)
    }

    override fun onStart() {
        super.onStart()
        setupSwipeMenu()

        txtName?.setOnClickListener { doCustInfo() }

        doCustLookup()
    }

    override fun onPause() {
        super.onPause()
        orderActivity.setActionButton()
        orderActivity.setActionButtonLeft()
    }

    override fun onResume() {
        super.onResume()
        orderActivity.setActionButton("add") { doSelect() }
        orderActivity.setActionButtonLeft("map") { doFinish() }
    }

    private fun setupSwipeMenu() {
        val creator = SwipeMenuCreator { menu -> // create "open" item
            val openItem = SwipeMenuItem(requireActivity().applicationContext)
            openItem.background = ColorDrawable(Color.rgb(0xC9, 0xC9, 0xCE)) // set item background
            openItem.width = 90 // set item width (px)
            openItem.title = "Open" // set item title
            openItem.titleSize = 18 // set item title fontsize
            openItem.titleColor = Color.WHITE // set item title font color
            menu.addMenuItem(openItem) // add to menu

            val deleteItem = SwipeMenuItem(requireActivity().applicationContext)
            deleteItem.background = ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25))
            deleteItem.width = 90 // set item width (px)
            deleteItem.title = "Delete" // set item title
            deleteItem.titleSize = 18 // set item title fontsize
            deleteItem.titleColor = Color.WHITE // set item title font color
            menu.addMenuItem(deleteItem) // add to menu
        }

        lstItems?.setMenuCreator(creator)

        lstItems?.setOnMenuItemClickListener { position, _, index ->
            when (index) {
                0 -> swipeOpen(position)
                1 -> swipeDelete(position)
                else -> true
            }
        }

        lstItems?.setOnItemLongClickListener { _, _, position, _ -> swipeOpen(position) }
    }

    private val lstItems get() = activity?.findViewById<SwipeMenuListView>(R.id.order_newsaleitems_list_bos)
    private val txtName get() = activity?.findViewById<TextView>(R.id.order_newsaleitems_text_name)

    private fun doFinish() {
        if (itemCount == 0) {
            toast("No Items.")
            return
        }

        orderActivity.pushFragment(ORDER_FRAGMENT_NEWSALEFINISH, null, this)
    }

    private fun doCancel() = requireActivity().finish()
    private fun doCustLookup() = orderActivity.pushFragment(ORDER_FRAGMENT_LOOKUP_CUSTNO, null, this)
    private fun doCustInfo() = orderActivity.pushFragment(ORDER_FRAGMENT_CUSTINFO, null, this)

    private fun loadItems() {
        val lines: ArrayList<BosLineModel> = ArrayList(items.map { Mapper.convert(it, BosLineModel::class.java) })
        val adapter = BosLineAdapter(lines, requireContext())
        lstItems?.adapter = adapter
    }

    private fun doSelect() {
        orderActivity.pushFragment(ORDER_FRAGMENT_ITEMSELECT, null, this)
    }

    fun receivedItemSelect(position: Int, style: String, status: String, loc: Int, quan: Double, price: Double) {
        if (position < 0)
            items.add(NewSaleItem(style, status, loc, quan, price * quan))
        else if (position < items.size)
            items[position] = NewSaleItem(style, status, loc, quan, price)
        loadItems()
    }

    private fun swipeDelete(position: Int): Boolean {
        items.removeAt(position)
        loadItems()
        return false
    }

    private fun swipeOpen(position: Int): Boolean {
        val item = items[position]

        val args = Bundle()
        args.putInt(ARG_POSITION, position)
        args.putString(ARG_STYLE, item.style)
        args.putString(ARG_STATUS, item.status)
        args.putInt(ARG_LOC, item.loc)
        args.putDouble(ARG_QUAN, item.quantity)

        orderActivity.pushFragment(ORDER_FRAGMENT_ITEMSELECT, args, this)
        return false
    }

    fun finishSale(info: NewSaleFinishInfo, fragment: SaleFinishFragment) {
        val request = NewSaleRequest()
        request.mailIndex = mailIndex
        request.items = items
        request.addTax = info.addTax ?: false
        request.delDate = info.delDate
        request.addDel = info.addDel
        request.addLab = info.addLab
        request.addStain = info.addStain
        request.pord = info.pord

        client.postSaleCreate(SettingsManager.storeNo, request, ::finishSaleComplete)
    }

    private fun finishSaleComplete(statusCode: Int, response: NewSaleResponse?) {
        if (statusCode == SC_CREATED && response != null) {
            val args = Bundle()
            args.putString(ARG_SALENO, response.saleNo)
            orderActivity.setFragment(ORDER_FRAGMENT_VIEWSALE, args, this)
        } else {
            toast("Process sale failed")
        }
    }
}

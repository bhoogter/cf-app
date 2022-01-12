package com.wincdspro.app.fragment.inventory

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ortiz.touchview.TouchImageView
import com.wincdspro.app.R
import com.wincdspro.app.activity.InventoryActivity
import com.wincdspro.app.constant.AppValues.Companion.APP_PACKAGE
import com.wincdspro.app.constant.AppValues.Companion.MAX_STORES
import com.wincdspro.app.constant.ArgNames.Companion.ARG_STYLE
import com.wincdspro.app.constant.OperationNames.Companion.INV_FRAGMENT_VIEWITEMDETAIL
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.core.Item
import com.wincdspro.app.util.Format
import java.io.InputStream
import java.net.HttpURLConnection.HTTP_OK
import java.util.Timer

class ItemViewFragment : WincdsFragment() {
    companion object {
        private var lookupTimer: Timer? = null
    }

    private val inventoryActivity get() = (requireActivity() as InventoryActivity)
    private val style: String get() = arguments?.getString(ARG_STYLE) ?: ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inventory_item_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnDetail?.setOnClickListener { doDetail() }
        getItemInfo()
        getItemImage()
    }

    private fun doDetail() {
        val args = Bundle()
        args.putString(ARG_STYLE, style)
        inventoryActivity.pushFragment(INV_FRAGMENT_VIEWITEMDETAIL, args, this)
    }

    private val btnDetail get() = activity?.findViewById<TextView>(R.id.inv_viewitem_button_details)
    private val txtStyle get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_style)
    private val txtVendor get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_vendor)
    private val txtVendorNo get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_vendorno)
    private val txtDesc get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_desc)
    private val txtList get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_list)
    private val txtOnSale get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_sale)
    private val txtOnHand get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_onhand)
    private val txtAvail get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_avail)
    private val txtComments get() = activity?.findViewById<TextView>(R.id.inv_viewitem_txt_comments)
    private val imgItemImage get() = activity?.findViewById<TouchImageView>(R.id.inv_viewitem_image_itemimage)

    private fun fitStores(n: Int): Int = if (n < 1) 1 else (if (n > MAX_STORES) MAX_STORES else n)
    private fun txtStk(n: Int) = activity?.findViewById<TextView>(resources.getIdentifier("inv_viewitem_lbl_stk${fitStores(n)}", "id", APP_PACKAGE))

    private fun txtOrd(n: Int) = activity?.findViewById<TextView>(resources.getIdentifier("inv_viewitem_lbl_ord${fitStores(n)}", "id", APP_PACKAGE))

    private fun getItemInfo() = client.getItem(style, ::itemInfoReceived)
    private fun getItemImage() = client.getItemImage(style, ::itemImageReceived)

    private fun itemInfoReceived(status: Int, item: Item?) {
        if (HTTP_OK != status || item == null) {
            toast("Failed fetching item: $status")
            return
        }
        populateItem(item)
    }

    private fun populateItem(item: Item) {
        txtStyle?.text = item.style
        txtVendor?.text = item.vendor
        txtVendorNo?.text = item.vendorNo
        txtDesc?.text = item.desc
        txtList?.text = Format.currency(item.list)
        txtOnSale?.text = Format.currency(item.onSale)
        txtOnHand?.text = Format.currency(item.onHand)
        txtAvail?.text = Format.quantity((item.available))
        txtComments?.text = item.comments

        populateQuantities(item)
    }

    private fun populateQuantities(item: Item) {
        for (i in 1..MAX_STORES) {
            txtStk(i)?.text = Format.quantity(item.getLocBal(i))
            txtOrd(i)?.text = Format.quantity(item.getOnOrder(i))
        }
    }

    private fun itemImageReceived(status: Int, imageInputStream: InputStream?) {
        if (HTTP_OK != status || imageInputStream == null) {
            toast("Failed fetching item: $status")
            return
        }

        try {
            val bitmap = BitmapFactory.decodeStream(imageInputStream)
            imgItemImage?.setImageBitmap(bitmap)
        } catch (e: Exception) {
            val msg = when {
                e.message?.contains("must not be null") == true -> "No Image Available."
                else -> "Failed to load image: ${e.message}"
            }
            toast(msg)
        }
    }
}

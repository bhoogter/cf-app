package com.wincdspro.app.fragment.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.wincdspro.app.R
import com.wincdspro.app.activity.OrderActivity
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_CREATED
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.core.Mail
import com.wincdspro.app.util.Format
import com.wincdspro.app.util.SettingsManager

class CustomerInfoFragment : WincdsFragment() {
    private val orderActivity get() = requireActivity() as OrderActivity
    private var mailIndex: Int
        get() = orderActivity.mailIndex
        set(v) {
            orderActivity.mailIndex = v
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val result = inflater.inflate(R.layout.fragment_order_custinfo, container, false)

        if (mailIndex > 0) loadMailRecord()
        return result
    }

    override fun onStart() {
        super.onStart()
        btnShipTo?.setOnClickListener { shipToOpen = !shipToOpen }
        btnCancel?.setOnClickListener { doCancel() }
        btnOk?.setOnClickListener { doSave() }
    }

    private val btnShipTo get() = activity?.findViewById<Button>(R.id.order_cust_button_shipto)
    private val btnOk get() = activity?.findViewById<Button>(R.id.order_cust_button_save)
    private val btnCancel get() = activity?.findViewById<Button>(R.id.order_cust_button_cancel)

    private val layCustInfo get() = activity?.findViewById<LinearLayout>(R.id.order_cust_layout_custinfo)
    private val layShipTo get() = activity?.findViewById<LinearLayout>(R.id.order_cust_layout_shipto)

    private val txtLast get() = activity?.findViewById<EditText>(R.id.order_cust_last)
    private val txtFirst get() = activity?.findViewById<EditText>(R.id.order_cust_first)
    private val txtEmail get() = activity?.findViewById<EditText>(R.id.order_cust_email)
    private val txtAddress get() = activity?.findViewById<EditText>(R.id.order_cust_add)
    private val txtAddress2 get() = activity?.findViewById<EditText>(R.id.order_cust_add2)
    private val txtCityST get() = activity?.findViewById<EditText>(R.id.order_cust_city)
    private val txtZip get() = activity?.findViewById<EditText>(R.id.order_cust_zip)
    private val txtTeleLabel get() = activity?.findViewById<EditText>(R.id.order_cust_telelabel)
    private val txtTele get() = activity?.findViewById<EditText>(R.id.order_cust_tele)
    private val txtTele2Label get() = activity?.findViewById<EditText>(R.id.order_cust_tele2label)
    private val txtTele2 get() = activity?.findViewById<EditText>(R.id.order_cust_tele2)

    private val txtShipToLast get() = activity?.findViewById<EditText>(R.id.order_cust_shiptolast)
    private val txtShipToFirst get() = activity?.findViewById<EditText>(R.id.order_cust_shiptofirst)
    private val txtShipToAddress get() = activity?.findViewById<EditText>(R.id.order_cust_label_shiptoaddress)
    private val txtShipToCityST get() = activity?.findViewById<EditText>(R.id.order_cust_label_shiptocity)
    private val txtShipToZip get() = activity?.findViewById<EditText>(R.id.order_cust_label_shiptozip)
    private val txtTele3Label get() = activity?.findViewById<EditText>(R.id.order_cust_telelabel3)
    private val txtTele3 get() = activity?.findViewById<EditText>(R.id.order_cust_tele3)

    private val txtSpecInstr get() = activity?.findViewById<EditText>(R.id.order_cust_specinstr)

    private var shipToOpen: Boolean
        get() = layShipTo?.visibility == View.VISIBLE
        set(v) {
            layShipTo?.visibility = if (v) View.VISIBLE else View.GONE
            layCustInfo?.visibility = if (!v) View.VISIBLE else View.GONE
            btnShipTo?.text = if (v) "<< Cust Info" else "Ship To >>"
        }

    private fun doSave() {
        val record = buildRecord()
        if (mailIndex == 0)
            client.postCustomerCreate(SettingsManager.storeNo, record, ::getSaveResponse)
        else
            client.putCustomerUpdate(SettingsManager.storeNo, mailIndex, record, ::getSaveResponse)
    }

    private fun getSaveResponse(statusCode: Int, mail: Mail?) {
        if ((statusCode == SC_OK || statusCode == SC_CREATED) && mail != null) {
            toast("Customer saved")
            mailIndex = mail.index ?: 0
            orderActivity.popFragment(this)
        } else {
            toast("Customer save failed: ${WincdsClient.lastError}")
        }
    }

    private fun doCancel() {
        orderActivity.finish()
    }

    private fun loadMailRecord() {
        client.getCustomer(SettingsManager.storeNo, mailIndex, ::mailRecordLoaded)
    }

    private fun mailRecordLoaded(statusCode: Int, mail: Mail?) {
        if (statusCode != SC_OK || mail == null) {
            toast("Failed fetching customer: $statusCode")
        } else {
            txtFirst?.setText(mail.first)
            txtLast?.setText(mail.last)
            txtEmail?.setText(mail.email)
            txtAddress?.setText(mail.address)
            txtAddress2?.setText(mail.addAddress)
            txtCityST?.setText(mail.city)
            txtZip?.setText(mail.zip)
            txtTeleLabel?.setText(mail.phoneLabel1 ?: "Telephone")
            txtTele?.setText(Format.ani(mail.tele))
            txtTele2Label?.setText(mail.phoneLabel2 ?: "Telephone")
            txtTele2?.setText(Format.ani(mail.tele2))
            txtTele3Label?.setText(mail.phoneLabel3 ?: "Telephone")
            txtTele3?.setText(Format.ani(mail.tele3))

            txtShipToFirst?.setText(mail.shipToFirst)
            txtShipToLast?.setText(mail.shipToLast)
            txtShipToAddress?.setText(mail.shipToAddress)
            txtShipToCityST?.setText(mail.shipToCity)
            txtShipToZip?.setText(mail.shipToZip)

            txtSpecInstr?.setText(mail.special)
        }
    }

    private fun buildRecord(): Mail {
        val mail = Mail()
        if (mailIndex > 0) mail.index = mailIndex
        mail.first = txtFirst?.text.toString()
        mail.last = txtLast?.text.toString()
        mail.email = txtEmail?.text.toString()
        mail.address = txtAddress?.text.toString()
        mail.addAddress = txtAddress2?.text.toString()
        mail.city = txtCityST?.text.toString()
        mail.zip = txtZip?.text.toString()
        mail.phoneLabel1 = txtTeleLabel?.text.toString()
        mail.tele = txtTele?.text.toString()
        mail.phoneLabel2 = txtTele2Label?.text.toString()
        mail.tele2 = txtTele2?.text.toString()
        mail.phoneLabel3 = txtTele3Label?.text.toString()
        mail.tele3 = txtTele3?.text.toString()

        mail.shipToFirst = txtShipToFirst?.text.toString()
        mail.shipToLast = txtShipToLast?.text.toString()
        mail.shipToAddress = txtShipToAddress?.text.toString()
        mail.shipToCity = txtShipToCityST?.text.toString()
        mail.shipToZip = txtShipToZip?.text.toString()

        mail.special = txtSpecInstr?.text.toString()

        return mail
    }
}

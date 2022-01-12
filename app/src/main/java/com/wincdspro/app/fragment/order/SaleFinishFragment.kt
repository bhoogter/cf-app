package com.wincdspro.app.fragment.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import com.wincdspro.app.R
import com.wincdspro.app.activity.OrderActivity
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.request.newsale.NewSaleFinishInfo
import com.wincdspro.app.util.DateUtil
import com.wincdspro.app.util.Format

class SaleFinishFragment : WincdsFragment() {
    private val orderActivity get() = requireActivity() as OrderActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_newsale_finish, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnProcess?.setOnClickListener { doFinish() }
        txtDelDate?.setText(DateUtil.formatDate())
    }

    private val btnProcess get() = activity?.findViewById<Button>(R.id.order_newsalefinish_button_process)
    private val chkAddTax get() = activity?.findViewById<CheckBox>(R.id.order_newsalefinish_checkbox_addtax)
    private val rdoPickup get() = activity?.findViewById<RadioButton>(R.id.order_newsalefinish_radio_pickup)
    private val rdoDropoff get() = activity?.findViewById<RadioButton>(R.id.order_newsalefinish_radio_dropoff)

    private val txtDelDate get() = activity?.findViewById<EditText>(R.id.order_newsalefinish_text_deldate)
    private val txtAddDel get() = activity?.findViewById<EditText>(R.id.order_newsalefinish_text_adddel)
    private val txtAddLab get() = activity?.findViewById<EditText>(R.id.order_newsalefinish_text_addlab)
    private val txtAddStain get() = activity?.findViewById<EditText>(R.id.order_newsalefinish_text_addstain)

    private fun doFinish() {
        if (DateUtil.parseDate(txtDelDate?.text.toString()) == null) {
            toast("Please enter a valid delivery date")
            return
        }

        val newSaleFragment = orderActivity.popFragment(this) as SaleCreateFragment
        val info = NewSaleFinishInfo(
            addTax = chkAddTax?.isChecked == true,
            addDel = Format.getPrice(txtAddDel?.text.toString()),
            addLab = Format.getPrice(txtAddLab?.text.toString()),
            addStain = Format.getPrice(txtAddStain?.text.toString()),
            delDate = txtDelDate?.text.toString(),
            pord = if (rdoPickup?.isChecked == true) "P" else "D",
        )
        newSaleFragment.finishSale(info, this)
    }
}

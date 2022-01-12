package com.wincdspro.app.fragment.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.wincdspro.app.R
import com.wincdspro.app.client.HttpStatusCode.Companion.SC_OK
import com.wincdspro.app.fragment.autotype.WincdsFragment
import com.wincdspro.app.model.wincds.request.tracking.PullOrderRequest
import com.wincdspro.app.model.wincds.request.tracking.ResultStatusResponse

class TrackingPullOrderFragment : WincdsFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tracking_pullorder, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnReceive?.setOnClickListener { doReceive() }
    }

    private val txtSaleNo get() = activity?.findViewById<EditText>(R.id.track_pullorder_txt_saleno)
    private val txtStyle get() = activity?.findViewById<EditText>(R.id.track_pullorder_txt_style)
    private val txtBin get() = activity?.findViewById<EditText>(R.id.track_pullorder_txt_bin)
    private val txtSerial get() = activity?.findViewById<EditText>(R.id.track_pullorder_txt_serial)

    private val btnReceive get() = activity?.findViewById<Button>(R.id.track_pullorder_btn_pull)

    private fun doReceive() {
        val request = PullOrderRequest(txtSaleNo?.text.toString(), txtStyle?.text.toString(), txtBin?.text.toString(), txtSerial?.text.toString())
        client.postTrackingPullOrder(request, ::receiveComplete)
    }

    private fun receiveComplete(status: Int, response: ResultStatusResponse? = null) {
        if (status == SC_OK) {
            txtStyle?.setText("")
            txtBin?.setText("")
            txtSerial?.setText("")
            txtStyle?.requestFocus()
            toast("Ok")
        } else {
            toast("Failed - $status")
        }
    }
}

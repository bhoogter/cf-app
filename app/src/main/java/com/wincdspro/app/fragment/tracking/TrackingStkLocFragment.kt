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
import com.wincdspro.app.model.wincds.request.tracking.ResultStatusResponse
import com.wincdspro.app.model.wincds.request.tracking.StkLocRequest

class TrackingStkLocFragment : WincdsFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tracking_stkloc, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnReceive?.setOnClickListener { doReceive() }
    }

    private val txtStyle get() = activity?.findViewById<EditText>(R.id.track_stkloc_txt_style)
    private val txtOldBin get() = activity?.findViewById<EditText>(R.id.track_stkloc_txt_oldbin)
    private val txtNewBin get() = activity?.findViewById<EditText>(R.id.track_stkloc_txt_newbin)
    private val txtSerial get() = activity?.findViewById<EditText>(R.id.track_stkloc_txt_serial)

    private val btnReceive get() = activity?.findViewById<Button>(R.id.track_stkloc_btn_receive)

    private fun doReceive() {
        val request = StkLocRequest(txtStyle?.text.toString(), txtOldBin?.text.toString(), txtNewBin?.text.toString(), txtSerial?.text.toString())
        client.postTrackingStkLoc(request, ::receiveComplete)
    }

    private fun receiveComplete(status: Int, response: ResultStatusResponse? = null) {
        if (status == SC_OK) {
            txtStyle?.setText("")
            txtOldBin?.setText("")
            txtNewBin?.setText("")
            txtSerial?.setText("")
            txtStyle?.requestFocus()
            toast("Ok")
        } else {
            toast("Failed - $status")
        }
    }
}

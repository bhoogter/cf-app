package com.wincdspro.app.fragment.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.constant.ArgNames.Companion.ARG_TYPE
import com.wincdspro.app.fragment.autotype.WincdsFragment

class PermissionRequestFragment : WincdsFragment() {
    private val mainActivity get() = (requireActivity() as MainActivity)

    enum class PermissionRequestType {
        CAMERA
    }

    val type: PermissionRequestType get() = PermissionRequestType.valueOf(arguments?.getString(ARG_TYPE) ?: PermissionRequestType.CAMERA.toString())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_permission, container, false)
    }

    override fun onStart() {
        super.onStart()

        when (type) {
            PermissionRequestType.CAMERA -> {
                val image = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_menu_camera, null)
                imgType?.setImageDrawable(image)
                txtType?.text = "Camera Access"
                txtReason?.text = "The camera is needed to scan live barcodes in the viewfinder.  No pictures are taken, transmitted, or otherwise used."
            }
        }

        btnAccept?.setOnClickListener { doClose() }
    }

    private val imgType get() = activity?.findViewById<ImageView>(R.id.main_permission_image_type)
    private val txtType get() = activity?.findViewById<TextView>(R.id.main_permission_text_type)
    private val txtReason get() = activity?.findViewById<TextView>(R.id.main_permission_text_reason)
    private val btnAccept get() = activity?.findViewById<Button>(R.id.main_permission_button_accept)

    private fun doClose() {
        requestPermission()
    }

    private fun requestPermission() {
        if (hasPermission(requireContext(), Manifest.permission.CAMERA))
            requestPermissions(mainActivity, Manifest.permission.CAMERA)
    }

    companion object {
        const val MY_CAMERA_REQUEST_CODE = 103
        fun hasPermission(context: Context, type: String) = ContextCompat.checkSelfPermission(context, type) != PackageManager.PERMISSION_GRANTED
        fun requestPermissions(activity: Activity, type: String) = requestPermissions(activity, arrayOf(type), MY_CAMERA_REQUEST_CODE)
    }
}

package com.wincdspro.app.fragment.autotype

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.constant.ArgNames.Companion.ARG_OPERATION

abstract class WincdsFragment : Fragment() {
    protected open val operation: String get() = activity?.intent?.extras?.getString(ARG_OPERATION) ?: ""
    protected open val client: WincdsClient get() = WincdsClient()
    protected open fun toast(s: String): Boolean {
        try {
            Toast.makeText(requireContext(), s, Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            // ...
        }
        return true
    }
}

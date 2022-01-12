package com.wincdspro.app.activity

import androidx.fragment.app.Fragment
import com.wincdspro.app.constant.OperationNames.Companion.TRK_FRAGMENT_PULLORDER
import com.wincdspro.app.constant.OperationNames.Companion.TRK_FRAGMENT_RECPO
import com.wincdspro.app.constant.OperationNames.Companion.TRK_FRAGMENT_STKLOC
import com.wincdspro.app.constant.OperationNames.Companion.TRK_OPERATION_PULLORDER
import com.wincdspro.app.constant.OperationNames.Companion.TRK_OPERATION_RECPO
import com.wincdspro.app.constant.OperationNames.Companion.TRK_OPERATION_STKLOC
import com.wincdspro.app.exception.TrackingException
import com.wincdspro.app.fragment.tracking.TrackingPullOrderFragment
import com.wincdspro.app.fragment.tracking.TrackingRecPoFragment
import com.wincdspro.app.fragment.tracking.TrackingStkLocFragment

class TrackingActivity : FragmentActivity() {

    override fun initialFragment(operation: String): String = when (operation) {
        TRK_OPERATION_PULLORDER -> TRK_FRAGMENT_PULLORDER
        TRK_OPERATION_RECPO -> TRK_FRAGMENT_RECPO
        TRK_OPERATION_STKLOC -> TRK_OPERATION_STKLOC
        else -> throw TrackingException("Invalid fragment: $operation")
    }

    override fun lookupFragment(fragmentName: String): Fragment = when (fragmentName) {
        TRK_FRAGMENT_PULLORDER -> TrackingPullOrderFragment()
        TRK_FRAGMENT_RECPO -> TrackingRecPoFragment()
        TRK_FRAGMENT_STKLOC -> TrackingStkLocFragment()
        else -> throw TrackingException("Invalid fragment: $fragmentName")
    }
}

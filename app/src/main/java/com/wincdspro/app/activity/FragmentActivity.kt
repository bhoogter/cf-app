package com.wincdspro.app.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.wincdspro.app.R
import com.wincdspro.app.constant.ArgNames.Companion.ARG_OPERATION
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_START
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_ITEMSELECT
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_VIEWSALE
import com.wincdspro.app.exception.FragmentActivityException
import java.util.Stack

abstract class FragmentActivity : AppCompatActivity() {
    private val pushedFragments = Stack<Fragment>()
    private val pushedFragmentName = Stack<String>()
    private var currFragmentName: String = ""
    var currentFragmentName: String
        get() = currFragmentName
        private set(value) {
            currFragmentName = value
        }
    private var currFragment: Fragment? = null
    fun getCurrentFragment() = currFragment

    protected open val operation: String get() = intent.extras?.getString(ARG_OPERATION) ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initialLayoutId())
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setFragment(initialFragment(operation))

        setActionButton()
    }

    override fun onPause() {
        super.onPause()
        clearFabs()
    }

    override fun onResume() {
        super.onResume()
        clearFabs()
    }

    protected open fun initialLayoutId(): Int = R.layout.activity_with_fragments
    protected open fun targetLayoutId(): Int = R.id.fragment_framelayout
    protected open fun initialFragment(operation: String): String = throw FragmentActivityException("Invalid fragment: $operation")
    protected open fun lookupFragment(fragmentName: String): Fragment = throw FragmentActivityException("Invalid fragment: $fragmentName")
    protected open fun fabAction(): ((View) -> Unit)? = null
    protected open fun fabType(): String = "email"

    open fun actionButtonDrawable(type: String) = when (type) {
        "add" -> android.R.drawable.ic_input_add // btn_plus
        "plus" -> android.R.drawable.ic_input_add // btn_plus
        "delete" -> android.R.drawable.ic_input_delete
        "get" -> android.R.drawable.ic_input_get
        "down" -> android.R.drawable.arrow_down_float
        "up" -> android.R.drawable.arrow_up_float
        "edit" -> android.R.drawable.ic_menu_edit
        "dot" -> android.R.drawable.btn_plus
        "minus" -> android.R.drawable.btn_minus
        "star" -> android.R.drawable.btn_star
        "staron" -> android.R.drawable.btn_star_big_on
        "staroff" -> android.R.drawable.btn_star_big_off
        "speak" -> android.R.drawable.ic_btn_speak_now
        "email" -> android.R.drawable.ic_dialog_email
        "info" -> android.R.drawable.ic_dialog_info
        "dialer" -> android.R.drawable.ic_dialog_dialer
        "map" -> android.R.drawable.ic_dialog_map
        else -> android.R.drawable.ic_dialog_email
    }

    open fun removeActionButton() = setActionButton(null, null)
    open fun setActionButton(type: String? = fabType(), action: ((View) -> Unit)? = fabAction()) {
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        if (action == null) {
            fab.visibility = View.GONE
            return
        }
        fab.setOnClickListener(action)
        if (type == null)
            fab.visibility = View.GONE
        else {
            fab.visibility = View.VISIBLE
            fab.setImageResource(actionButtonDrawable(type))
        }
    }

    open fun removeActionButtonLeft() = setActionButtonLeft(null, null)
    open fun setActionButtonLeft(type: String? = fabType(), action: ((View) -> Unit)? = fabAction()) {
        val fabLeft = findViewById<FloatingActionButton>(R.id.fabLeft)
        if (action == null) {
            fabLeft.visibility = View.GONE
            return
        }
        fabLeft.setOnClickListener(action)
        if (type == null)
            fabLeft.visibility = View.GONE
        else {
            fabLeft.visibility = View.VISIBLE
            fabLeft.setImageResource(actionButtonDrawable(type))
        }
    }

    open fun clearFabs() {
        removeActionButton()
        removeActionButtonLeft()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> closeActivity()
            else -> super.onOptionsItemSelected(item)
        }
    }

    open fun closeActivity(): Boolean {
        finish()
        return true
    }

    open fun setFragment(fragmentName: String = ORDER_FRAGMENT_VIEWSALE, args: Bundle? = null, source: Fragment? = null): Boolean {
        if (source != null) supportFragmentManager.beginTransaction().remove(source).commit()

        currFragmentName = fragmentName
        val fragment = lookupFragment(fragmentName) ?: throw FragmentActivityException("Invalid fragment: $fragmentName")
        fragment.arguments = args

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(targetLayoutId(), fragment)
        ft.commit()
        runOnUiThread {
            supportActionBar?.setDisplayHomeAsUpEnabled(fragmentName != MAIN_MENU_START)
        }

        currFragment = fragment
        return true
    }

    open fun pushFragment(fragmentName: String = ORDER_FRAGMENT_ITEMSELECT, args: Bundle?, source: Fragment): Boolean {
        val ft = supportFragmentManager.beginTransaction()
        pushedFragmentName.push(currFragmentName)
        currFragmentName = fragmentName
        val fragment = lookupFragment(fragmentName) ?: throw FragmentActivityException("Invalid fragment: $fragmentName")
        fragment.arguments = args

        pushedFragments.push(source)
        ft.hide(source)
        ft.add(targetLayoutId(), fragment)
        ft.show(fragment)
        ft.commit()
        return true
    }

    open fun popFragment(source: Fragment): Fragment {
        val ft = supportFragmentManager.beginTransaction()
        val old = pushedFragments.pop()
        currFragmentName = pushedFragmentName.pop()
        ft.hide(source)
        ft.show(old)
        ft.remove(source)
        ft.commit()
        return old
    }

    open fun bundleArgs(args: Map<String, Any>) = Bundle().apply {
        args.forEach { (t, u) ->
            when (u) {
                is String -> putString(t, u)
                is Int -> putInt(t, u)
                is Long -> putLong(t, u)
                else -> throw FragmentActivityException("Unknown type on bundleArgs: $u")
            }
        }
    }
}

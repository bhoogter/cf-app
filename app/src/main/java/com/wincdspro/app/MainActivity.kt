package com.wincdspro.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.wincdspro.app.activity.FragmentActivity
import com.wincdspro.app.activity.ServerSettingsActivity
import com.wincdspro.app.activity.UserSettingsActivity
import com.wincdspro.app.constant.AppValues
import com.wincdspro.app.constant.ArgNames.Companion.ARG_OPERATION
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_ABOUTP
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_DEBUG
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_HELP
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_INVENT
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_ORDER
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_PERMCAMERA
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_START
import com.wincdspro.app.constant.OperationNames.Companion.MAIN_MENU_TRACKG
import com.wincdspro.app.constant.PrivZones
import com.wincdspro.app.fragment.main.AppHelpFragment
import com.wincdspro.app.fragment.main.DebugFragment
import com.wincdspro.app.fragment.main.MainMenuAboutFragment
import com.wincdspro.app.fragment.main.MainMenuInventoryFragment
import com.wincdspro.app.fragment.main.MainMenuOrderFragment
import com.wincdspro.app.fragment.main.MainMenuStartFragment
import com.wincdspro.app.fragment.main.MainMenuTrackingFragment
import com.wincdspro.app.fragment.main.PermissionRequestFragment
import com.wincdspro.app.fragment.main.PermissionRequestFragment.Companion.MY_CAMERA_REQUEST_CODE
import com.wincdspro.app.util.PrivsUtil.Companion.checkAccess
import com.wincdspro.app.util.SettingsManager

class MainActivity : FragmentActivity() {
    override fun initialFragment(operation: String): String = MAIN_MENU_START

    override fun fabAction(): ((View) -> Unit) = {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, AppValues.EMAIL_SUPPORT)
        intent.putExtra(Intent.EXTRA_SUBJECT, "WinCDS Pro App Support")
        intent.putExtra(Intent.EXTRA_TEXT, "Hello WinCDS Pro:\n\nI need help with ")

        startActivity(Intent.createChooser(intent, "Send Email"))
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SettingsManager.load(this)
        setSubMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
//        mnuDebug.visibility = View.GONE
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            android.R.id.home -> setSubMenu()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (currentFragmentName == MAIN_MENU_START) super.onBackPressed()
        else {
            setSubMenu()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(baseContext, "Thank you", Toast.LENGTH_LONG).show()
                setSubMenu()
            } else {
                Toast.makeText(baseContext, "No Camera?  No thank-you.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    override fun lookupFragment(fragmentName: String): Fragment = when (fragmentName) {
        MAIN_MENU_START -> MainMenuStartFragment()
        MAIN_MENU_ABOUTP -> MainMenuAboutFragment()
        MAIN_MENU_ORDER -> MainMenuOrderFragment()
        MAIN_MENU_INVENT -> MainMenuInventoryFragment()
        MAIN_MENU_TRACKG -> MainMenuTrackingFragment()
        MAIN_MENU_PERMCAMERA -> PermissionRequestFragment()
        MAIN_MENU_HELP -> AppHelpFragment()
        MAIN_MENU_DEBUG -> DebugFragment()
        else -> MainMenuStartFragment()
    }

    private val mnuDebug get() = findViewById<Button>(R.id.action_debug)

    fun setSubMenu(name: String = ""): Boolean {
        val tName = if (name.isNotEmpty()) name else when {
            PermissionRequestFragment.hasPermission(this, Manifest.permission.CAMERA) -> MAIN_MENU_PERMCAMERA
            else -> MAIN_MENU_START
        }

        setFragment(tName)
        runOnUiThread { supportActionBar?.setDisplayHomeAsUpEnabled(tName != MAIN_MENU_START) }

        if (tName == MAIN_MENU_START && !SettingsManager.serverSettings.serverIsSetup) startActivity(Intent(this, ServerSettingsActivity::class.java))
        return true
    }

    fun launchOperation(perm: PrivZones?, c: Class<*>, operation: String) {
        if (!SettingsManager.loggedIn) {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show()
        } else if (perm == null || checkAccess(perm, this))
            startActivity(Intent(this, c).apply { putExtra(ARG_OPERATION, operation) })
        else
            Toast.makeText(baseContext, "You don't have permission to ${perm.text}", Toast.LENGTH_LONG).show()
    }

    // Nav Menu (top right)
    fun main_menu_settings(x: MenuItem) = startActivity(Intent(this, ServerSettingsActivity::class.java))
    fun main_menu_settings_user(x: MenuItem) = startActivity(Intent(this, UserSettingsActivity::class.java))
    fun main_menu_about(x: MenuItem) = setSubMenu(MAIN_MENU_ABOUTP)
    fun main_menu_debug(item: MenuItem) = setSubMenu(MAIN_MENU_DEBUG)
    fun main_menu_help(item: MenuItem) = setSubMenu(MAIN_MENU_HELP)
    fun main_menu_quit(x: MenuItem) = finish()
}

package com.wincdspro.app.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.wincdspro.app.R
import com.wincdspro.app.constant.AppValues.Companion.APP_PACKAGE
import com.wincdspro.app.model.settings.sections.UserSettings
import com.wincdspro.app.util.SettingsManager

class UserSettingsActivity : AppCompatActivity() {
    private val preferences get() = getSharedPreferences(APP_PACKAGE, Context.MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        SettingsManager.load(this)
        userSettings = SettingsManager.userSettings
    }

    override fun onStart() {
        super.onStart()
        btnUp?.setOnClickListener { addLoc(1) }
        btnDn?.setOnClickListener { addLoc(-1) }
    }

    private fun addLoc(dir: Int) {
        txtStoreNo.setText((txtStoreNo.text.toString().toIntOrNull() ?: 1 + dir).coerceIn(1, SettingsManager.maxStoreAvailable).toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            android.R.id.home -> {
                finish()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()
        SettingsManager.settings.userSettings = userSettings
        SettingsManager.save(preferences)
    }

    override fun onDestroy() {
        super.onDestroy()
        SettingsManager.load(this)
    }

    private val txtStoreNo get() = findViewById<EditText>(R.id.settings_user_storeno)
    private val txtSalesman get() = findViewById<EditText>(R.id.settings_user_salesman)
    private val txtSalesPass get() = findViewById<EditText>(R.id.settings_user_salespass)
    private val btnUp get() = findViewById<Button>(R.id.settings_user_button_plusloc)
    private val btnDn get() = findViewById<Button>(R.id.settings_user_button_minusloc)

    private var userSettings: UserSettings
        get() = UserSettings(
            txtStoreNo.text.toString().toIntOrNull() ?: 1,
            txtSalesman.text.toString(),
            txtSalesPass.text.toString()
        )
        set(u) {
            txtSalesman.setText(u.salesman)
            txtSalesPass.setText(u.salesmanPass)
            txtStoreNo.setText(u.storeNo.toString())
        }
}

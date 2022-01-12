package com.wincdspro.app.fragment.main

import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.constant.OperationNames
import com.wincdspro.app.test.CustomMatchers.Companion.containsStringIgnoringCase
import com.wincdspro.app.test.CustomMatchers.Companion.hasValueEqualTo
import com.wincdspro.app.util.WLogger
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DebugFragmentTest {
    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        WLogger.clear()
        WLogger.info("test 1")
        WLogger.info("test 2")
        WLogger.info("test 3")

        val intent = Intent().apply { putExtra("mocked", true) }
        activityTestRule.launchActivity(intent)
        activityTestRule.activity.setSubMenu(OperationNames.MAIN_MENU_DEBUG)
    }

    @Test
    fun testClearInput() {
        onView(withId(R.id.fragment_main_debug)).check(matches(isDisplayed()))
        onView(withId(R.id.main_debug_text_log))
            .check(matches(containsStringIgnoringCase("test 2")))
            .perform(longClick())
            .check(matches(hasValueEqualTo("")))
    }
}

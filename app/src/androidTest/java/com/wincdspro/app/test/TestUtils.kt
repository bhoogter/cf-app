package com.wincdspro.app.test

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.wincdspro.app.R
import com.wincdspro.app.client.WincdsClient
import com.wincdspro.app.model.settings.general.Group
import com.wincdspro.app.test.WincdsClientTestResponses.Companion.defaultSetup
import com.wincdspro.app.util.SettingsManager

class TestUtils {
    companion object {

        @JvmStatic
        fun mockConnected() {
            SettingsManager.loggedIn = true
            SettingsManager.authToken.admin = true

            SettingsManager.wincdsSettings.groups = listOf(Group("A"), Group("O"), Group("S"), Group("W"))

            SettingsManager.serverSettings.serverHost = "localhost"
            SettingsManager.serverSettings.serverPort = 9999
            SettingsManager.serverSettings.serverPass = ""

            WincdsClient.additionalInterceptors = listOf(FakeNetworkInterceptor())
            FakeNetworkInterceptor.responses = mutableMapOf()
            defaultSetup()
        }

        @JvmStatic
        fun fragmentVisisble(f: Int) = Espresso.onView(ViewMatchers.withId(f)).check(ViewAssertions.matches(ViewMatchers.isDisplayed())).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        @JvmStatic
        fun clickBarBack() = Espresso.onView(ViewMatchers.withContentDescription(R.string.abc_action_bar_up_description)).perform(ViewActions.click())
    }
}

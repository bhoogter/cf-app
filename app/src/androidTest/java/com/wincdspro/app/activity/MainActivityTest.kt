package com.wincdspro.app.activity

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.wincdspro.app.MainActivity
import com.wincdspro.app.R
import com.wincdspro.app.test.TestUtils.Companion.clickBarBack
import com.wincdspro.app.test.TestUtils.Companion.fragmentVisisble
import com.wincdspro.app.test.TestUtils.Companion.mockConnected
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainActivityTest {

    @get:Rule
    var activityRule: IntentsTestRule<MainActivity> = IntentsTestRule(MainActivity::class.java)

    @Before
    fun init() {
        mockConnected()
    }

    @Test
    fun testStartNav() {
        fragmentVisisble(R.id.fragment_main_start)

        onView(withId(R.id.main_button_orderentry)).perform(click())
        fragmentVisisble(R.id.fragment_main_order)
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)

        onView(withId(R.id.main_button_inventory)).perform(click())
        fragmentVisisble(R.id.fragment_main_inventory)
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)

        onView(withId(R.id.main_button_tracking)).perform(click())
        fragmentVisisble(R.id.fragment_main_tracking)
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)

        onView(withId(R.id.main_button_reports)).perform(click())
        intended(hasComponent(ReportActivity::class.java.name))
        fragmentVisisble(R.id.fragment_reports_results)
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    fun testLaunchViewSale() {
        fragmentVisisble(R.id.fragment_main_start)

        onView(withId(R.id.main_button_orderentry)).perform(click())
        onView(withId(R.id.main_button_viewsale)).perform(click())
        intended(hasComponent(OrderActivity::class.java.name))
        fragmentVisisble(R.id.fragment_order_lookup)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    fun testLaunchNewSale() {
        onView(withId(R.id.main_button_orderentry)).perform(click())
        onView(withId(R.id.main_button_newsale)).perform(click())
        intended(hasComponent(OrderActivity::class.java.name))
        fragmentVisisble(R.id.fragment_order_lookup)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    fun testLaunchDailyAudit() {
        onView(withId(R.id.main_button_orderentry)).perform(click())
        onView(withId(R.id.main_button_dailyAudit)).perform(click())
        intended(hasComponent(ReportActivity::class.java.name))
        fragmentVisisble(R.id.fragment_reports_dailyaudit)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    fun testLaunchItemLookupp() {
        onView(withId(R.id.main_button_inventory)).perform(click())
        onView(withId(R.id.main_inventory_button_itemlookup)).perform(click())
        intended(hasComponent(InventoryActivity::class.java.name))
        fragmentVisisble(R.id.fragment_inventory_item_lookup)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    fun testLaunchPoss() {
        onView(withId(R.id.main_button_inventory)).perform(click())
        onView(withId(R.id.main_inventory_button_pos)).perform(click())
        intended(hasComponent(InventoryActivity::class.java.name))
        fragmentVisisble(R.id.fragment_inventory_po_lookup)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    @Ignore("No camera results in an error.")
    fun testLaunchPhysInv() {
        onView(withId(R.id.main_button_inventory)).perform(click())
        onView(withId(R.id.main_inventory_button_physicalinv)).perform(click())
        intended(hasComponent(InventoryActivity::class.java.name))
        fragmentVisisble(R.id.fragment_inventory_physicalinv)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    fun testLaunchPullOrder() {
        onView(withId(R.id.main_button_tracking)).perform(click())
        onView(withId(R.id.main_button_pullorder)).perform(click())
        intended(hasComponent(TrackingActivity::class.java.name))
        fragmentVisisble(R.id.fragment_tracking_pullorder)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    fun testLaunchRecPo() {
        onView(withId(R.id.main_button_tracking)).perform(click())
        onView(withId(R.id.main_button_recpo)).perform(click())
        intended(hasComponent(TrackingActivity::class.java.name))
        fragmentVisisble(R.id.fragment_tracking_recpo)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }

    @Test
    fun testLaunchStkLoc() {
        onView(withId(R.id.main_button_tracking)).perform(click())
        onView(withId(R.id.main_button_stkloc)).perform(click())
        intended(hasComponent(TrackingActivity::class.java.name))
        fragmentVisisble(R.id.fragment_tracking_stkloc)
        clickBarBack()
        clickBarBack()
        fragmentVisisble(R.id.fragment_main_start)
    }
}

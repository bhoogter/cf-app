package com.wincdspro.app.integration

import android.content.Intent
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.wincdspro.app.R
import com.wincdspro.app.activity.OrderActivity
import com.wincdspro.app.constant.ArgNames
import com.wincdspro.app.constant.OperationNames
import com.wincdspro.app.test.CustomMatchers
import com.wincdspro.app.test.TestUtils.Companion.fragmentVisisble
import com.wincdspro.app.test.TestUtils.Companion.mockConnected
import org.hamcrest.CoreMatchers.anything
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class ViewSaleTest {

    @get:Rule
    var activityRule: ActivityTestRule<OrderActivity> = ActivityTestRule(OrderActivity::class.java, false, false)

    @Before
    fun init() {
        mockConnected()

        val intent = Intent().apply { putExtra(ArgNames.ARG_OPERATION, OperationNames.ORDER_OPERATION_VIEWSALE) }
        activityRule.launchActivity(intent)
    }

    @Test
    fun testViewSale() {
        fragmentVisisble(R.id.fragment_order_lookup)
        onView(withId(R.id.order_lookup_input)).perform(typeText("1034"))
        Thread.sleep(1500)
        onData(anything())
            .inAdapterView(withId(R.id.order_look_lst_results))
            .atPosition(0)
            .check(matches(isDisplayed()))
        onData(anything())
            .inAdapterView(withId(R.id.order_look_lst_results))
            .atPosition(3)
            .check(matches(CustomMatchers.containsStringIgnoringCase("10348")))

        onView(withId(R.id.order_lookup_input)).perform(replaceText("10348"))
        Thread.sleep(1500)
        onData(anything())
            .inAdapterView(withId(R.id.order_look_lst_results))
            .atPosition(0)
            .check(matches(CustomMatchers.containsStringIgnoringCase("10348")))
            .perform(click())

        fragmentVisisble(R.id.fragment_order_viewsale)
        onView(withId(R.id.order_viewsale_text_saleno)).check(matches(withText("10348")))
    }
}

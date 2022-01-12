package com.wincdspro.app.fragment.order

import android.content.Intent
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import androidx.test.rule.ActivityTestRule
import com.wincdspro.app.R
import com.wincdspro.app.activity.OrderActivity
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_FRAGMENT_LOOKUP
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_OPERATION_NEWSALE
import com.wincdspro.app.constant.OperationNames.Companion.ORDER_OPERATION_VIEWSALE
import com.wincdspro.app.test.TestUtils.Companion.mockConnected
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class SaleLookupFragmentTest {
    @get:Rule
    var activityTestRule: ActivityTestRule<OrderActivity> = ActivityTestRule(OrderActivity::class.java, false, false)

    @Before
    fun setup() {
        mockConnected()
    }

    fun launch(op: String) {
        val intent = Intent().apply {
            putExtra("mocked", true)
            putExtra("operation", op)
        }
        activityTestRule.launchActivity(intent)
        activityTestRule.activity.setFragment(ORDER_FRAGMENT_LOOKUP)
    }

    @Test
    fun testLookupOnViewSale() {
        launch(ORDER_OPERATION_VIEWSALE)
        onView(withId(R.id.lookup_phone)).check(matches(isDisplayed()))
        onView(withId(R.id.lookup_name)).check(matches(isDisplayed()))
        onView(withId(R.id.lookup_sale)).check(matches(isDisplayed()))
        onView(withId(R.id.lookup_date)).check(matches(isDisplayed()))
    }

    @Test
    fun testLookupOnNewSale() {
        launch(ORDER_OPERATION_NEWSALE)
        onView(withId(R.id.lookup_phone)).check(matches(isDisplayed()))
        onView(withId(R.id.lookup_name)).check(matches(isDisplayed()))
        onView(withId(R.id.lookup_sale)).check(matches(not(isDisplayed())))
        onView(withId(R.id.lookup_date)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testLookupFindListByName() {
        launch(ORDER_OPERATION_VIEWSALE)

        onView(withId(R.id.lookup_name))
            .check(matches(isDisplayed()))
            .perform(click())
        onView(withId(R.id.order_lookup_input))
            .check(matches(isDisplayed()))
            .perform(typeText("CARTER"))
        (activityTestRule.activity.getCurrentFragment() as SaleLookupFragment).inputChangedDispatch(true)
        Thread.sleep(150)

        onView(withId(R.id.order_look_lst_results))
            .check(matches(isDisplayed()))

        onData(anything())
            .inAdapterView(withId(R.id.order_look_lst_results))
            .atPosition(1)
            .check(matches(isDisplayed()))
        onData(anything())
            .inAdapterView(withId(R.id.order_look_lst_results))
            .atPosition(0)
            .check(matches(isDisplayed()))
            .perform(click())
    }
}

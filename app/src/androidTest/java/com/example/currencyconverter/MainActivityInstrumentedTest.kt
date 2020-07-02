package com.example.currencyconverter

import android.view.View
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingPolicies
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.currencyconverter.delegate.CurrencyDelegate
import com.example.currencyconverter.idlingResources.RecyclerViewIdlingResource
import com.example.currencyconverter.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.hamcrest.Matcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityInstrumentedTest {

    @get:Rule
    val activityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Before
    fun setUp()  {
        IdlingPolicies.setMasterPolicyTimeout(30, TimeUnit.SECONDS)
        IdlingPolicies.setIdlingResourceTimeout(30, TimeUnit.SECONDS)
    }

    @Test
    fun testCurrencyValues() {
        val recyclerView = activityTestRule.activity.activity_main_recycler_view
        val position0 = 0
        val position1 = 1
        val position30 = 30
        val eurItemIdlingResource = RecyclerViewIdlingResource(recyclerView, position0)
        val usdItemIdlingResource = RecyclerViewIdlingResource(recyclerView, position30)
        //check initial values
        IdlingRegistry.getInstance().register(eurItemIdlingResource)
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position0, hasDescendant(withText("1.0")))))
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position0, hasDescendant(withText("EUR")))))
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position0, hasDescendant(withText("Euro")))))
        IdlingRegistry.getInstance().unregister(eurItemIdlingResource)

        onView(withId(R.id.activity_main_recycler_view))
            .perform(scrollToPosition<CurrencyDelegate.ViewHolder>(position30))
        IdlingRegistry.getInstance().register(usdItemIdlingResource)
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position30, hasDescendant(withText("1.143")))))
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position30, hasDescendant(withText("USD")))))
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position30, hasDescendant(withText("US Dollar")))))
        IdlingRegistry.getInstance().unregister(usdItemIdlingResource)

        //currency change check
        onView(withId(R.id.activity_main_recycler_view))
            .perform(actionOnItemAtPosition<CurrencyDelegate.ViewHolder>(position30, click()))
        onView(withId(R.id.activity_main_recycler_view))
            .perform(scrollToPosition<CurrencyDelegate.ViewHolder>(position0))
        Thread.sleep(200)//wait for request
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position1, hasDescendant(withText("1.014"))))) //0.887 * 1.143 and round till 3 numbers
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position1, hasDescendant(withText("EUR")))))
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position1, hasDescendant(withText("Euro")))))

        onView(withId(R.id.activity_main_recycler_view))
            .perform(actionOnItemAtPosition<CurrencyDelegate.ViewHolder>(
                position0,
                typeTextInChildViewWithId(R.id.currency_delegate_currency_value, "2")
            ))
        Thread.sleep(200)
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position0, hasDescendant(withText("2")))))
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position0, hasDescendant(withText("USD")))))
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position0, hasDescendant(withText("US Dollar")))))

        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position1, hasDescendant(withText("1.774")))))// 2 * (1.014 / 1.143) and round(3)
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position1, hasDescendant(withText("EUR")))))
        onView(withId(R.id.activity_main_recycler_view))
            .check(matches(atPosition(position1, hasDescendant(withText("Euro")))))

    }

    private fun typeTextInChildViewWithId(id: Int, textToBeTyped: String): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View>? {
                return null
            }

            override fun getDescription(): String {
                return "Click on a child view with specified id."
            }

            override fun perform(uiController: UiController, view: View) {
                val v = view.findViewById<View>(id) as EditText
                v.setText(textToBeTyped)
            }
        }
    }
}
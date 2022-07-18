package com.clearscore.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()


    @Test
    fun testNavigationToCreditScoreFragment() {

        // VERIFY
        onView(withId(R.id.fragmentCreditScore)).check(matches(isDisplayed()))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
        onView(withId(R.id.textCreditScoreLine1)).check(matches(isDisplayed()))
        onView(withId(R.id.textCreditScoreLine2)).check(matches(isDisplayed()))
        onView(withId(R.id.textCreditScoreLine3)).check(matches(isDisplayed()))
        onView(withId(R.id.retryButton)).check(matches(not(isDisplayed())))

        // ACTION
        onView(withId(R.id.creditScoreRing)).perform(ViewActions.click())

        // VERIFY
        onView(withId(R.id.fragmentCreditScoreDetails)).check(matches(isDisplayed()))
        onView(withId(R.id.accountStatus)).check(matches(isDisplayed()))
        onView(withId(R.id.dashboardStatus)).check(matches(isDisplayed()))

        // ACTION
        pressBack()

        // VERIFY
        onView(withId(R.id.fragmentCreditScore)).check(matches(isDisplayed()))
    }

}
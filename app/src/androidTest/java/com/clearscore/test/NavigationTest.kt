package com.clearscore.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setUp() {
    }

    @Test
    fun testNavigationToCreditScoreFragment() {

        // VERIFY
        onView(withId(R.id.fragmentCreditScore)).check(matches(isDisplayed()))

        // ACTION
        onView(withId(R.id.creditScoreRing)).perform(ViewActions.click())

        // VERIFY
        onView(withId(R.id.fragmentCreditScoreDetails)).check(matches(isDisplayed()))

        // ACTION
        pressBack()

        // VERIFY
        onView(withId(R.id.fragmentCreditScore)).check(matches(isDisplayed()))
    }

//    @Test
//    fun test3() {
//        val navController = TestNavHostController(
//            ApplicationProvider.getApplicationContext()
//        )
//
//        val creditScoreFragmentScenario =
//            launchFragmentInContainer<CreditScoreFragment>(themeResId = R.style.MyMaterialTheme)
//
//        creditScoreFragmentScenario.onFragment { fragment ->
//            navController.setGraph(R.navigation.nav_graph)
//
//            Navigation.setViewNavController(fragment.requireView(), navController)
//        }
//
//        assertEquals(
//            navController.currentDestination?.id,
//            R.id.action_CreditScoreFragment_to_CreditScoreDetailsFragment
//        )
//    }

}
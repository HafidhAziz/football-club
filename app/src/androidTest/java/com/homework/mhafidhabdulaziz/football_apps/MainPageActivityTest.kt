package com.homework.mhafidhabdulaziz.football_apps

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.homework.mhafidhabdulaziz.football_apps.R.id.*
import com.homework.mhafidhabdulaziz.football_apps.presentation.MainPageActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by M Hafidh Abdul Aziz on 10/19/2018.
 */
@RunWith(AndroidJUnit4::class)
class MainPageActivityTest {


    @Rule
    @JvmField
    var mainPageActivityTestRule = ActivityTestRule(MainPageActivity::class.java)

    @Test
    fun mainActivityTest() {
        delay()

        Espresso.onView(ViewMatchers.withId(last_match_recycler))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        delay()
        Espresso.onView(ViewMatchers.withId(last_match_recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))

        Espresso.onView(ViewMatchers.withId(last_match_recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, ViewActions.click()))
        delay()

        Espresso.onView(ViewMatchers.withId(home_team_logo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_team_logo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.pressBack()
        delay()

        Espresso.onView(ViewMatchers.withId(view_pager)).perform(ViewActions.swipeLeft())

        delay()

        Espresso.onView(ViewMatchers.withId(next_match_recycler))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        delay()
        Espresso.onView(ViewMatchers.withId(next_match_recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))

        Espresso.onView(ViewMatchers.withId(next_match_recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, ViewActions.click()))
        delay()

        Espresso.onView(ViewMatchers.withId(home_team_logo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_team_logo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(favorite)).perform(ViewActions.click())

        Espresso.pressBack()
        delay()

        Espresso.onView(ViewMatchers.withId(next_swipe_layout)).perform(ViewActions.swipeDown())

        delay()

        Espresso.onView(ViewMatchers.withId(action_favorite)).perform(ViewActions.click())

        delay()
        Espresso.onView(ViewMatchers.withId(fav_match_recycler))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(fav_match_recycler)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        Espresso.onView(ViewMatchers.withId(fav_match_recycler)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(home_team_logo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(away_team_logo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(favorite)).perform(ViewActions.click())

        Espresso.pressBack()

        delay()

    }

    private fun delay(){
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
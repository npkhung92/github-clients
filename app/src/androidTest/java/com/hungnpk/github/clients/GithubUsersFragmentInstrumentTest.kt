package com.hungnpk.github.clients

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.hungnpk.github.clients.di.NetworkModule
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.repository.GithubUsersRepository
import com.hungnpk.github.clients.presentation.users.GithubUserViewHolder
import com.hungnpk.github.clients.presentation.users.GithubUsersFragment
import com.hungnpk.github.clients.util.FakeGithubRepository
import com.hungnpk.github.clients.util.waitFor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class GithubUsersFragmentInstrumentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltAndroidRule.inject()
        launchFragmentInHiltContainer<GithubUsersFragment>()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    class TestNetworkModule {

        @Provides
        @Singleton
        fun providesGithubRepository(): GithubUsersRepository {
            return FakeGithubRepository().apply {
                mockUserList.add(
                    User(
                        id = 1, username = "hung123",
                        name = "Hung Nguyen",
                        location = "HCM",
                        company = "google"
                    )
                )
                mockUserList.add(
                    User(
                        id = 2, username = "zalo32",
                        name = "Zalo Nguyen",
                        location = "Hanoi",
                        company = "facebook"
                    )
                )
                mockUserList.add(
                    User(
                        id = 3, username = "hwai99",
                        name = "Hawaii Hoang",
                        location = "Da Nang",
                        company = "axon"
                    )
                )
            }
        }
    }

    private fun updateSearchText(text: String) {
        onView(allOf(withId(R.id.etSearchUsers))).perform(ViewActions.clearText())
            .perform(ViewActions.click())
            .perform(ViewActions.typeText(text))
    }

    private fun scrollThenCheckTextVisible(position: Int, text: String) {
        onView(withId(R.id.rvGithubUsers))
            .perform(RecyclerViewActions.scrollToPosition<GithubUserViewHolder>(position))
        onView(withText(text)).check(matches(isDisplayed()))
    }

    @Test
    fun whenSearched_displayMatchingUsersFromRepository() {
        updateSearchText("hwai")
        onView(isRoot()).perform(waitFor(3000))
        onView(withText("hwai99")).check(matches(isDisplayed()))
    }

}
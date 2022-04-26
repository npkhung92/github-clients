package com.hungnpk.github.clients

import androidx.core.os.bundleOf
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.hungnpk.github.clients.di.NetworkModule
import com.hungnpk.github.clients.domain.model.User
import com.hungnpk.github.clients.domain.repository.GithubUsersRepository
import com.hungnpk.github.clients.presentation.userdetail.GithubUserDetailFragment
import com.hungnpk.github.clients.util.FakeGithubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(NetworkModule::class)
class GithubUserDetailFragmentInstrumentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltAndroidRule.inject()
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

    @Test(expected = IllegalStateException::class)
    fun whenLaunchUserDetailScreen_noUserName_throwException() {
        launchFragmentInHiltContainer<GithubUserDetailFragment>()
    }

    @Test
    fun whenLaunchUserDetailScreen_showUserFullName() {
        launchFragmentInHiltContainer<GithubUserDetailFragment>(
            fragmentArgs = bundleOf("username" to "hwai99")
        )
        onView(withId(R.id.tvUserFullName)).check(matches(isDisplayed()))
        onView(withId(R.id.tvUserFullName)).check(matches(withText("Hawaii Hoang")))
    }

}
package com.hungnpk.github.clients

import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isGone
import com.hungnpk.github.clients.presentation.users.GithubUsersFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    application = HiltTestApplication::class
)
@LooperMode(LooperMode.Mode.PAUSED)
class GithubUsersFragmentTest {
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun testFragmentShowed() {
        launchFragmentInHiltContainer<GithubUsersFragment> {
            assert(view?.findViewById<EditText>(R.id.etSearchUsers)?.hint == "Input username here …")
            assert(view?.findViewById<ProgressBar>(R.id.ivProgress)?.isGone == true)
            assert(view?.findViewById<TextView>(R.id.tvEmptyState)?.isGone == true)
        }
    }
}
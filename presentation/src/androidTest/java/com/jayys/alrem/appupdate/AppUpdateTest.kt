package com.jayys.alrem.appupdate

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppUpdateTest {

    private lateinit var appUpdateTestHelper: AppUpdateTestHelper
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        appUpdateTestHelper = AppUpdateTestHelper(context)
    }

    @Test
    fun testUpdateAvailable() {
        // Given
        appUpdateTestHelper.setupUpdateAvailable()
        val appUpdateManager = appUpdateTestHelper.getAppUpdateManager()

        // When
        val appUpdateInfo: AppUpdateInfo = appUpdateManager.appUpdateInfo.result

        // Then
        assertEquals(UpdateAvailability.UPDATE_AVAILABLE, appUpdateInfo.updateAvailability())
        assertTrue(appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE))
    }
}
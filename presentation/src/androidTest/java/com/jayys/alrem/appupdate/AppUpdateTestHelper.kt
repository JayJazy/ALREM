package com.jayys.alrem.appupdate

import android.content.Context
import com.google.android.play.core.appupdate.testing.FakeAppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType

class AppUpdateTestHelper(private val context: Context) {
    private val fakeAppUpdateManager = FakeAppUpdateManager(context)

    fun setupUpdateAvailable(updateType: Int = AppUpdateType.IMMEDIATE) {
        fakeAppUpdateManager.setUpdateAvailable(9)
        fakeAppUpdateManager.userAcceptsUpdate()
        fakeAppUpdateManager.downloadStarts()
        fakeAppUpdateManager.downloadCompletes()

        when (updateType) {
            AppUpdateType.IMMEDIATE -> fakeAppUpdateManager.installCompletes()
            AppUpdateType.FLEXIBLE -> {
                // Flexible 업데이트의 경우 추가 단계가 필요할 수 있습니다.
            }
        }
    }

    fun getAppUpdateManager() = fakeAppUpdateManager
}
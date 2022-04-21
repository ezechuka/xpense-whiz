package com.javalon.xpensewhiz.presentation.setting_screen.service

import android.app.job.JobParameters
import android.app.job.JobService
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import com.javalon.xpensewhiz.common.Constants
import com.javalon.xpensewhiz.data.repository.datastore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class LimitResetJobService : JobService() {

    private val expenseLimit = doublePreferencesKey(Constants.EXPENSE_LIMIT_KEY)
    override fun onStartJob(p0: JobParameters?): Boolean {
        GlobalScope.launch(IO) {
            applicationContext.datastore.edit { store ->
                store[expenseLimit] = 0.0
            }
        }
        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return false
    }

}
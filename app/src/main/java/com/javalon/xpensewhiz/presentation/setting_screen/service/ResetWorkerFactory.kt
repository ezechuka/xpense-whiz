package com.javalon.xpensewhiz.presentation.setting_screen.service

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.javalon.xpensewhiz.domain.usecase.write_datastore.EditExpenseLimitUseCase
import javax.inject.Inject

class ResetWorkerFactory @Inject constructor(private val editExpenseLimitUseCase: EditExpenseLimitUseCase) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return LimitResetWorker(appContext, workerParameters, editExpenseLimitUseCase)
    }
}
//package com.example.currencyconverter.workers
//
//import android.app.Application
//import android.content.Context
//import androidx.work.*
//import com.example.currencyconverter.viewmodel.CurrencyViewModel
//import java.util.concurrent.TimeUnit
//
//class ExchangeRateWorker(
//    context: Context,
//    workerParams: WorkerParameters
//) : CoroutineWorker(context, workerParams) {
//
//    override suspend fun doWork(): Result {
//        return try {
//            val application = applicationContext as Application
//            val currencyViewModel = CurrencyViewModel(application)
//
//            currencyViewModel.updateExchangeRates()
//
//            Result.success()
//        } catch (e: Exception) {
//            Result.retry()
//        }
//    }
//}
//
//fun scheduleExchangeRateUpdate(context: Context) {
//    val workRequest = PeriodicWorkRequestBuilder<ExchangeRateWorker>(
//        repeatInterval = 24,
//        repeatIntervalTimeUnit = TimeUnit.HOURS
//    )
//        .setConstraints(
//            Constraints.Builder()
//                .setRequiredNetworkType(NetworkType.CONNECTED)
//                .build()
//        )
//        .build()
//
//    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//        "ExchangeRateUpdate",
//        ExistingPeriodicWorkPolicy.KEEP,
//        workRequest
//    )
//}

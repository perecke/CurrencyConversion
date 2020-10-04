package com.julia.szelesjulia.myapplication.model

import android.util.Log
import com.julia.szelesjulia.myapplication.currency.CurrencyRateContract
import com.julia.szelesjulia.myapplication.network.ApiClient
import kotlinx.coroutines.*
import java.net.UnknownHostException

/**
 * Connecting to the API via model
 */

class CurrencyRateTypeModel : CurrencyRateContract.Model {

    private val API_KEY = "6bbd2cc7c9b36d4ab0fa52ef763860ab"
    private var job: Job = Job()

    override fun getCurrencyList(currencyListListener: CurrencyRateContract.Model.OnFinishedListenerCurrencyList) {
        CoroutineScope(Dispatchers.IO + job).launch {
            try {
                val currencyListResponse =
                    ApiClient.createRetrofitService().getCurrenciesList(API_KEY)
                withContext(Dispatchers.Main) {
                    if (currencyListResponse.isSuccessful) {
                        currencyListResponse.body()?.let { currencyListListener.onSuccess(it) }
                            ?: currencyListListener.onFailedList(throwable = Throwable("Empty response"))
                    } else {
                        currencyListListener.onFailedList(throwable = Throwable(currencyListResponse.message()))
                    }
                }
            } catch (e: UnknownHostException) {
                Log.d("### Internet error ###", "")
                withContext(Dispatchers.Main) {
                    currencyListListener.onFailedList(Throwable(e.message.toString()))
                }
            } catch (error: Exception) {
                Log.d("### Other error ###", "${error.message}")
            }
        }
    }

    override fun getCurrencyRateList(currencyRateListener: CurrencyRateContract.Model.OnFinishedListenerCurrencyRate) {
        CoroutineScope(Dispatchers.IO + job).launch {
            try {
                val currencyRateResponse =
                    ApiClient.createRetrofitService().getCurrencyRate(API_KEY)
                withContext(Dispatchers.Main) {
                    if (currencyRateResponse.isSuccessful) {
                        currencyRateResponse.body()?.let { currencyRateListener.onSuccess(it) }
                            ?: currencyRateListener.onFailedRate(throwable = Throwable("Empty response"))
                    } else {
                        currencyRateListener.onFailedRate(throwable = Throwable(currencyRateResponse.message()))
                    }
                }
            } catch (e: UnknownHostException) {
                Log.d("### Internet error ###", "")
                withContext(Dispatchers.Main) {
                    currencyRateListener.onFailedRate(Throwable(e.message.toString()))
                }
            } catch (exception: Exception) {
                Log.d("### Other error ###", "${exception.message}")
            }
        }
    }
}
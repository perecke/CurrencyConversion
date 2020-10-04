package com.julia.szelesjulia.myapplication.currency

import com.julia.szelesjulia.myapplication.datamodel.CurrencyModel
import com.julia.szelesjulia.myapplication.datamodel.CurrencyRateModel

/**
 * CurrencyRateContract
 */
interface CurrencyRateContract {
    interface Model {
        interface OnFinishedListenerCurrencyRate {
            fun onSuccess(currencyRate: CurrencyRateModel)
            fun onFailedRate(throwable: Throwable)
        }

        interface OnFinishedListenerCurrencyList {
            fun onSuccess(currencyType: CurrencyModel)
            fun onFailedList(throwable: Throwable)
        }

        fun getCurrencyList(currencyListListener: OnFinishedListenerCurrencyList)

        fun getCurrencyRateList(currencyRateListener: OnFinishedListenerCurrencyRate)
    }

    interface View {
        fun showProgress()
        fun hideProgress()
        fun setCurrencyType(currencyTypeList: List<CurrencyType>)
        fun setCurrencyRate(currencyRateList: List<CurrencyRate>)
        fun onError(t: Throwable)
    }

    interface Presenter {
        fun onDestroy()

        fun getCurrencyRate()

        fun getCurrencyList()

        interface PresenterContract {
            fun setView(view: View)
        }
    }

    interface Adapter {
        fun setOnGridSelectedListener(listener: CurrencyRateRecyclerViewAdapter.OnGridItemClickedListener)
        fun updateCurrencyRateData(rateList: List<CurrencyRate>)
    }
}
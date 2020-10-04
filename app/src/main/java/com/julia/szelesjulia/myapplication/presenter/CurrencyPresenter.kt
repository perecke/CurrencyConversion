package com.julia.szelesjulia.myapplication.presenter

import android.util.Log
import com.julia.szelesjulia.myapplication.utils.ConverterHelper
import com.julia.szelesjulia.myapplication.currency.CurrencyRateContract
import com.julia.szelesjulia.myapplication.datamodel.CurrencyModel
import com.julia.szelesjulia.myapplication.datamodel.CurrencyRateModel
import com.julia.szelesjulia.myapplication.model.CurrencyRateTypeModel

/**
 * Presenter
 */
class CurrencyPresenter : CurrencyRateContract.Presenter,
    CurrencyRateContract.Presenter.PresenterContract,
    CurrencyRateContract.Model.OnFinishedListenerCurrencyRate,
    CurrencyRateContract.Model.OnFinishedListenerCurrencyList {

    var model : CurrencyRateTypeModel? = null
    var viewContract : CurrencyRateContract.View? = null
    var converterHelper : ConverterHelper =
        ConverterHelper()

    override fun setView(view: CurrencyRateContract.View) {
        model = CurrencyRateTypeModel()
        viewContract = view
    }

    override fun getCurrencyRate() {
        Log.d("###", "getCurrencyRate" + "###")
        viewContract?.showProgress()
        model?.getCurrencyRateList(this)
    }

    override fun getCurrencyList() {
        Log.d("###", "getCurrencyList" + "###")
        viewContract?.showProgress()
        model?.getCurrencyList(this)
    }

    override fun onSuccess(currencyRate: CurrencyRateModel) {
        Log.d("###", "onSuccess rate" + "###")
        viewContract?.hideProgress()
        viewContract?.setCurrencyRate(converterHelper.convertCurrencyRateToList(currencyRate))
    }

    override fun onSuccess(currencyType: CurrencyModel) {
        Log.d("###", "onSuccess type" + "###")
        viewContract?.hideProgress()
        viewContract?.setCurrencyType(converterHelper.convertCurrencyTypeToList(currencyType))
    }

    override fun onFailedList(throwable: Throwable) {
        Log.d("###", "onFailedList" + "###")
        viewContract?.hideProgress()
        viewContract?.onError(throwable)
    }

    override fun onFailedRate(throwable: Throwable) {
        Log.d("###", "onFailedRate" + "###")
        viewContract?.hideProgress()
        viewContract?.onError(throwable)
    }

    override fun onDestroy() {
        viewContract = null
    }
}
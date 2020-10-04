package com.julia.szelesjulia.myapplication.utils

import com.julia.szelesjulia.myapplication.datamodel.CurrencyModel
import com.julia.szelesjulia.myapplication.currency.CurrencyRate
import com.julia.szelesjulia.myapplication.datamodel.CurrencyRateModel
import com.julia.szelesjulia.myapplication.currency.CurrencyType

interface CurrencyConverter {
    fun convertCurrencyTypeToList(currencyModel: CurrencyModel) : List<CurrencyType>
    fun convertCurrencyRateToList(currencyRate: CurrencyRateModel) : List<CurrencyRate>
}
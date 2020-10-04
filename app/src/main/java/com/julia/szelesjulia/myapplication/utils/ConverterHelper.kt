package com.julia.szelesjulia.myapplication.utils

import com.julia.szelesjulia.myapplication.datamodel.CurrencyModel
import com.julia.szelesjulia.myapplication.currency.CurrencyRate
import com.julia.szelesjulia.myapplication.datamodel.CurrencyRateModel
import com.julia.szelesjulia.myapplication.currency.CurrencyType

/**
 * Convert the JSON model to lists of items
 */
class ConverterHelper : CurrencyConverter {

    override fun convertCurrencyTypeToList(currencyModel: CurrencyModel): List<CurrencyType> {
        val currencyList : MutableList<CurrencyType> = mutableListOf()

        val rates = currencyModel.currencies
        currencyList.add(
            CurrencyType(
                "AUD",
                rates.aED
            )
        )
        currencyList.add(
            CurrencyType(
                "BGN",
                rates.bGN
            )
        )
        currencyList.add(
            CurrencyType(
                "BRL",
                rates.bRL
            )
        )
        currencyList.add(
            CurrencyType(
                "CAD",
                rates.cAD
            )
        )
        currencyList.add(
            CurrencyType(
                "CHF",
                rates.cHF
            )
        )

        currencyList.add(
            CurrencyType(
                "CNY",
                rates.cNY
            )
        )
        currencyList.add(
            CurrencyType(
                "CZK",
                rates.cZK
            )
        )
        currencyList.add(
            CurrencyType(
                "DKK",
                rates.dKK
            )
        )
        currencyList.add(
            CurrencyType(
                "GBP",
                rates.gBP
            )
        )
        currencyList.add(
            CurrencyType(
                "HKD",
                rates.hKD
            )
        )

        currencyList.add(
            CurrencyType(
                "HRK",
                rates.hRK
            )
        )
        currencyList.add(
            CurrencyType(
                "HUF",
                rates.hUF
            )
        )
        currencyList.add(
            CurrencyType(
                "IDR",
                rates.iDR
            )
        )
        currencyList.add(
            CurrencyType(
                "ILS",
                rates.iLS
            )
        )
        currencyList.add(
            CurrencyType(
                "INR",
                rates.iNR
            )
        )

        currencyList.add(
            CurrencyType(
                "JPY",
                rates.jPY
            )
        )
        currencyList.add(
            CurrencyType(
                "KRW",
                rates.kRW
            )
        )
        currencyList.add(
            CurrencyType(
                "MXN",
                rates.mXN
            )
        )
        currencyList.add(
            CurrencyType(
                "MYR",
                rates.mYR
            )
        )
        currencyList.add(
            CurrencyType(
                "NOK",
                rates.nOK
            )
        )

        currencyList.add(
            CurrencyType(
                "NZD",
                rates.nZD
            )
        )

        return currencyList
    }

    override fun convertCurrencyRateToList(currencyRate: CurrencyRateModel): List<CurrencyRate> {
        val currencyList : MutableList<CurrencyRate> = mutableListOf()

        val rates = currencyRate.quotes

        currencyList.add(
            CurrencyRate(
                "AUD",
                rates.uSDAED
            )
        )
        currencyList.add(
            CurrencyRate(
                "BGN",
                rates.uSDBGN
            )
        )
        currencyList.add(
            CurrencyRate(
                "BRL",
                rates.uSDBRL
            )
        )
        currencyList.add(
            CurrencyRate(
                "CAD",
                rates.uSDCAD
            )
        )
        currencyList.add(
            CurrencyRate(
                "CHF",
                rates.uSDCHF
            )
        )

        currencyList.add(
            CurrencyRate(
                "CNY",
                rates.uSDCNY
            )
        )
        currencyList.add(
            CurrencyRate(
                "CZK",
                rates.uSDCZK
            )
        )
        currencyList.add(
            CurrencyRate(
                "DKK",
                rates.uSDDKK
            )
        )
        currencyList.add(
            CurrencyRate(
                "GBP",
                rates.uSDGBP
            )
        )
        currencyList.add(
            CurrencyRate(
                "HKD",
                rates.uSDHKD
            )
        )

        currencyList.add(
            CurrencyRate(
                "HRK",
                rates.uSDHRK
            )
        )
        currencyList.add(
            CurrencyRate(
                "HUF",
                rates.uSDHUF
            )
        )
        currencyList.add(
            CurrencyRate(
                "IDR",
                rates.uSDIDR
            )
        )
        currencyList.add(
            CurrencyRate(
                "ILS",
                rates.uSDILS
            )
        )
        currencyList.add(
            CurrencyRate(
                "INR",
                rates.uSDINR
            )
        )

        currencyList.add(
            CurrencyRate(
                "JPY",
                rates.uSDJPY
            )
        )
        currencyList.add(
            CurrencyRate(
                "KRW",
                rates.uSDKRW
            )
        )
        currencyList.add(
            CurrencyRate(
                "MXN",
                rates.uSDMXN
            )
        )
        currencyList.add(
            CurrencyRate(
                "MYR",
                rates.uSDMYR
            )
        )
        currencyList.add(
            CurrencyRate(
                "NOK",
                rates.uSDNOK
            )
        )

        currencyList.add(
            CurrencyRate(
                "NZD",
                rates.uSDNZD
            )
        )

        return currencyList
    }
}
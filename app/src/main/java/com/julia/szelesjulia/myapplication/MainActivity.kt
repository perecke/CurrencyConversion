package com.julia.szelesjulia.myapplication

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.julia.szelesjulia.myapplication.currency.CurrencyRate
import com.julia.szelesjulia.myapplication.currency.CurrencyRateContract
import com.julia.szelesjulia.myapplication.currency.CurrencyRateRecyclerViewAdapter
import com.julia.szelesjulia.myapplication.currency.CurrencyType
import com.julia.szelesjulia.myapplication.customview.CustomDialog
import com.julia.szelesjulia.myapplication.presenter.CurrencyPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

/**
 * MainActivity
 */
class MainActivity : AppCompatActivity(),
    CurrencyRateContract.View,
    CustomDialog.OnItemSelectedListener,
    CurrencyRateRecyclerViewAdapter.OnGridItemClickedListener {

    private var mainPresenter: CurrencyPresenter? = null

    private var currencyTypeArrayMain: ArrayList<CurrencyType> = ArrayList()
    private var currencyRateListMain: ArrayList<CurrencyRate> = ArrayList()

    private var adapter: CurrencyRateRecyclerViewAdapter? = null

    private var selectedCurrency: String = ""

    private var isFirst = false
    private var isRefreshed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storeLastRefreshDate()
        mainPresenter = CurrencyPresenter()
        mainPresenter?.setView(this)
        callService()
        isFirst = true

        select_currency_btn.setOnClickListener {
            if (currencyTypeArrayMain.isNotEmpty()) {
                CustomDialog.show(supportFragmentManager, ArrayList(currencyTypeArrayMain), this)
            }
        }

        initRecyclerView()

    }

    private fun getPreferences(): SharedPreferences =
        this.getSharedPreferences(
            resources.getString(R.string.preference_name),
            Context.MODE_PRIVATE
        )

    override fun onStart() {
        super.onStart()
        if (!isFirst) {
            shouldUpdateData()
        }
        isFirst = false
    }

    override fun showProgress() {
        Log.d("###", "showProgress ###")
        loading_back.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        Log.d("###", "hideProgress ###")
        loading_back.visibility = View.GONE
    }

    override fun setCurrencyType(currencyTypeList: List<CurrencyType>) {
        Log.d("###", "$currencyTypeList ###")
        currencyTypeArrayMain = ArrayList(currencyTypeList)
    }

    override fun setCurrencyRate(currencyRateList: List<CurrencyRate>) {
        Log.d("###", "$currencyRateList ###")
        currencyRateListMain = ArrayList(currencyRateList)

        if (isRefreshed) {
            val amount = amount_edit_text.text.toString()
            if (amount.isNotEmpty() && selectedCurrency.isNotEmpty()) {
                Log.d("### Adapter refresh ###", "")
                val convertedRate =
                    ArrayList(convertCurrencyInto(currencyRateListMain, amount.toDouble()))
                adapter?.updateCurrencyRateData(convertedRate)
                adapter?.notifyDataSetChanged()
            }
        }
        isRefreshed = false
    }

    override fun onError(t: Throwable) {
        Log.d("###", "onError $t ###")
        displayAlertDialogWith(R.string.unexpected_error, R.string.unexpected_error_message, true)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let { focus ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(focus.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    //If 30 minutes passed since the last update, get the API data first
    override fun onItemSelected(currencyType: CurrencyType) {
        Log.d("### OnItemSelected ###", "")
        val amount = amount_edit_text.text.toString()

        if (amount.isEmpty()) {
            select_currency_btn.text = ""
            selectedCurrency = ""
            displayAlertDialogWith(
                R.string.missing_data_error,
                R.string.missing_data_error_message,
                false
            )
            return
        }

        select_currency_btn.text = currencyType.currencyName
        selectedCurrency = currencyType.currencyName

        if (!shouldUpdateData()) {
            Log.d("### Data no update ###", "")
            val convertedRate =
                ArrayList(convertCurrencyInto(currencyRateListMain, amount.toDouble()))
            adapter?.updateCurrencyRateData(convertedRate)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onGridClicked(rate: CurrencyRate) {
        val currencyName = currencyTypeArrayMain.find { it.currencyName == rate.currencyType }
        Toast.makeText(this, "${currencyName?.currencyAbb} ${rate.currencyRate}", Toast.LENGTH_LONG)
            .show()
    }

    private fun callService() {
        mainPresenter?.getCurrencyList()
        mainPresenter?.getCurrencyRate()
    }

    private fun initRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        adapter = CurrencyRateRecyclerViewAdapter(this).also {
            it.setOnGridSelectedListener(this)
        }
        main_recycler_view.adapter = adapter
        main_recycler_view.layoutManager = layoutManager
    }

    private fun displayAlertDialogWith(titleId: Int, messageId: Int, isApiError: Boolean) {
        val alertDialog = AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle(resources.getString(titleId))
        alertDialog.setMessage(resources.getString(messageId))
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, resources.getText(R.string.alert_ok_btn)
        ) { dialog, _ ->
            if (isApiError) {
                callService()
            }
            dialog.dismiss()
        }
        alertDialog.show()
    }

    private fun convertCurrencyInto(
        origList: List<CurrencyRate>,
        amount: Double
    ): List<CurrencyRate> {
        val currentCurrency = origList.find { it.currencyType == selectedCurrency }
        Log.d("### Current currency:$selectedCurrency", "value in USD:$currentCurrency")

        val convertedList = ArrayList<CurrencyRate>()

        currentCurrency?.let { currencyRateObj ->
            val amountInDollar = amount / currencyRateObj.currencyRate
            Log.d(
                "### Inserted amount:$amount",
                "value in USD:${amount * currencyRateObj.currencyRate}to"
            )

            for (rateItem in origList) {
                val rate =
                    CurrencyRate(
                        rateItem.currencyType,
                        String.format("%.3f", rateItem.currencyRate * amountInDollar).toDouble()
                    )
                convertedList.add(rate)
            }
        }

        return convertedList
    }

    private fun storeLastRefreshDate() {
        val date = Date(System.currentTimeMillis())
        val millis = date.time

        getPreferences().edit().putLong(resources.getString(R.string.date_preference), millis)
            .apply()
    }

    private fun shouldUpdateData(): Boolean {
        val previousPref =
            getPreferences().getLong(resources.getString(R.string.date_preference), 0)

        Log.d("### Prev time: $previousPref", "")
        val previous = Calendar.getInstance()
        previous.time = Date(previousPref)
        val now = Calendar.getInstance()
        val diff = now.timeInMillis - previous.timeInMillis
        if (diff >= 30 * 60 * 1000) {
            Log.d("### Update time ###", "after 30 minutes ###")
            storeLastRefreshDate()
            mainPresenter?.getCurrencyRate()
            isRefreshed = true

            return true
        }

        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter?.onDestroy()
    }
}

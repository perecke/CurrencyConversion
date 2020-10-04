package com.julia.szelesjulia.myapplication.currency

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.julia.szelesjulia.myapplication.R
import kotlinx.android.synthetic.main.view_grid_currency_rate.view.*

/**
 * Currency rate recycler view adapter
 */
class CurrencyRateRecyclerViewAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    CurrencyRateContract.Adapter{

    var listenerOnGrid : OnGridItemClickedListener? = null
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var currencyRateList: List<CurrencyRate> = mutableListOf()

    interface OnGridItemClickedListener {
        fun onGridClicked(rate: CurrencyRate)
    }

    override fun setOnGridSelectedListener(listener: OnGridItemClickedListener) {
        listenerOnGrid = listener
    }

    override fun updateCurrencyRateData(rateList: List<CurrencyRate>) {
        currencyRateList = rateList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CurrencyRateViewHolder(inflater.inflate(R.layout.view_grid_currency_rate, parent, false))
    }

    override fun getItemCount(): Int = currencyRateList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CurrencyRateViewHolder).bindView(position)
    }

    private inner class CurrencyRateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindView(position: Int) {
            val rateItem = currencyRateList[position]
            itemView.currency_rate_name.text = rateItem.currencyType
            itemView.currency_rate_value.text = rateItem.currencyRate.toString()

            itemView.setOnClickListener {
                listenerOnGrid?.onGridClicked(rateItem)
            }
        }
    }
}
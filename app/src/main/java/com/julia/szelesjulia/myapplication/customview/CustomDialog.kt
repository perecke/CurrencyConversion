package com.julia.szelesjulia.myapplication.customview

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.julia.szelesjulia.myapplication.R
import com.julia.szelesjulia.myapplication.currency.CurrencyType
import java.util.*


/**
 * Dialog fragment for scrollable view
 */
class CustomDialog : DialogFragment() {

    var currencyTypeList: ArrayList<CurrencyType> = ArrayList()
    var listener: OnItemSelectedListener? = null

    interface OnItemSelectedListener {
        fun onItemSelected(currencyType: CurrencyType)
    }

    companion object {
        fun show(
            fm: FragmentManager,
            list: ArrayList<CurrencyType>,
            listener: OnItemSelectedListener
        ) {
            dismiss(fm)
            val fragment =
                createInstance(
                    list,
                    listener
                )
            fragment.show(fm, CustomDialog::class.java.name)
            fm.executePendingTransactions()
        }

        private fun dismiss(fm: FragmentManager?) {
            if (fm == null) {
                return
            }
            val fragment = fm
                .findFragmentByTag(DialogFragment::class.java.name)

            if (fragment is DialogFragment) {
                fragment.dismissAllowingStateLoss()
                fm.executePendingTransactions()
            }
        }

        private fun createInstance(
            couponList: ArrayList<CurrencyType>,
            listener: OnItemSelectedListener
        ): CustomDialog {
            val fragment = CustomDialog()
            fragment.currencyTypeList = couponList
            fragment.listener = listener
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(
            activity,
            R.layout.layout_dialog_list, null
        )
        val adapter = ListAdapter(requireContext(), 0, currencyTypeList)
        val listView = view.findViewById<View>(R.id.dialog_list) as ListView
        listView.adapter = adapter
        return AlertDialog.Builder(activity)
            .setTitle(requireContext().resources.getString(R.string.select_currency))
            .setView(view)
            .create()
    }

    inner class ListAdapter(context: Context, resource: Int, list: ArrayList<CurrencyType>) :
        ArrayAdapter<CurrencyType>(context, resource, list) {

        private var inflater: LayoutInflater? = null

        init {
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }

        override fun getView(position: Int, v: View?, parent: ViewGroup): View {
            val item = getItem(position)
            var convertView = v
            if (v == null) {
                convertView = inflater?.inflate(R.layout.view_text_dialog_text_view, null)
            }
            val intTextView = convertView?.findViewById(R.id.currency_type_dialog) as TextView
            intTextView.text = String.format(
                context.getString(R.string.format_for_dialog),
                item?.currencyName,
                item?.currencyAbb
            )
            intTextView.setOnClickListener {
                item?.let { listener?.onItemSelected(it) }
                dismiss()
            }
            return convertView
        }
    }
}
package com.wincdspro.app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.wincdspro.app.R
import com.wincdspro.app.util.Format

class BosLineAdapter(
    data: ArrayList<BosLineModel>,
    context: Context
) : ArrayAdapter<BosLineModel?>(context, R.layout.row_viewsale_item, data as List<BosLineModel?>), View.OnClickListener {

    private val dataSet: ArrayList<BosLineModel> = data
    var mContext: Context = context

    // View lookup cache
    private class ViewHolder {
        var txtStyle: TextView? = null
        var txtStatus: TextView? = null
        var txtQty: TextView? = null
        var txtPrice: TextView? = null
        var txtDesc: TextView? = null
    }

    override fun onClick(v: View) {
        val position = v.tag as Int
        val `object`: Any? = getItem(position)
        val dataModel: BosLineModel? = `object` as BosLineModel?
    }

    private var lastPosition = -1
    override fun getView(position: Int, pConvertView: View?, parent: ViewGroup): View {
        // Get the data item for this position
        var convertView: View? = pConvertView
        val dataModel: BosLineModel? = getItem(position)

        // Check if an existing view is being reused, otherwise inflate the view
        val viewHolder: ViewHolder // view lookup cache stored in tag

        if (convertView == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(R.layout.row_viewsale_item, parent, false)
            viewHolder.txtStyle = convertView.findViewById(R.id.row_viewsale_item_style)
            viewHolder.txtStatus = convertView.findViewById(R.id.row_viewsale_item_status)
            viewHolder.txtQty = convertView.findViewById(R.id.row_viewsale_item_qty)
            viewHolder.txtPrice = convertView.findViewById(R.id.row_viewsale_item_price)
            viewHolder.txtDesc = convertView.findViewById(R.id.row_viewsale_item_desc)
            convertView?.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        lastPosition = position
        viewHolder.txtStyle?.text = dataModel?.style
        viewHolder.txtStatus?.text = dataModel?.status
        viewHolder.txtQty?.text = Format.quantity(dataModel?.quantity ?: 0.0)
        viewHolder.txtPrice?.text = Format.currency(dataModel?.price ?: 0.0)
        viewHolder.txtDesc?.text = dataModel?.description ?: ""
        // Return the completed view to render on screen
        return convertView!!
    }
}

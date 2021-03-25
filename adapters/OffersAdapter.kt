package com.example.calatour.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.calatour.R
import com.example.calatour.model.Offer

class OffersAdapter (private val context: Context,
                     private var dataSource: ArrayList<Offer>) : BaseAdapter() {

    // get a reference to the LayoutInflater service
    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, containerView: View?, viewGroupParent: ViewGroup?): View {
        val rowView = containerView ?: inflater.inflate(R.layout.offers_list_element, viewGroupParent, false)

        // get the title of the offer
        val titleTextView = rowView.findViewById<TextView> ( R.id.offerTitleTextView )
        titleTextView.text = getItem(position).title

        // get the image of the offer
        val imageImageView = rowView.findViewById<ImageView>( R.id.offerImageImageView )
        imageImageView.setImageResource(getItem(position).image)

        // get price of the offer
        val priceTextView = rowView.findViewById<TextView>( R.id.offerPriceTextView )
        priceTextView.text = getItem(position).price.toString() + "Euro"

        // get the description of the offer
        val descriptionTextView = rowView.findViewById<TextView>( R.id.offerDescriptionTextView )
        descriptionTextView.text = getItem(position).description
        return rowView
    }

    override fun getItem(position: Int): Offer {
        return dataSource.elementAt(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

    fun addOffer(position: Int, offer: Offer) {
        dataSource.add(position, offer)
    }

    fun removeOffer(position: Int) {
        dataSource.removeAt(position)
    }

    fun resendDataSource(offer: ArrayList<Offer>) {
        dataSource = offer
    }
}
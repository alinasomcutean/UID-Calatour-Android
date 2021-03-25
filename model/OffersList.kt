package com.example.calatour.model

import com.example.calatour.R

class OffersList {
    fun getOffers() : ArrayList<Offer> {
        val offers = ArrayList<Offer>()
        offers.add((Offer("Barcelona, 3 nights", "Description for Barcelona offer!", 300, R.drawable.offer_1)))
        offers.add((Offer("Maldive, 7 nights", "Description for Maldive offer!", 1050, R.drawable.offer_2)))
        offers.add((Offer("Thailand, 10 nights", "Description fo Thailand offer!", 1250, R.drawable.offer_3)))

        return offers
    }
}
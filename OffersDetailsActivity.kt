package com.example.calatour

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.calatour.model.Offer
import org.w3c.dom.Text

class OffersDetailsActivity : AppCompatActivity() {

    private lateinit var offer: Offer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers_details)

        offer = intent.getSerializableExtra("offer") as Offer

        val titleTextView = findViewById<TextView>(R.id.offerDetailTitleTextView)
        titleTextView.text = offer.title

        val imageImageView = findViewById<ImageView>(R.id.offerDetailImageImageView)
        imageImageView.setImageResource(offer.image)

        val priceTextView = findViewById<TextView>(R.id.offerDetailPriceTextView)
        priceTextView.text = offer.price.toString() + "Euro"

        val descriptionTextView = findViewById<TextView>(R.id.offerDetailDescriptionTextView)
        descriptionTextView.text = offer.description
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.offer_details_options_menu, menu)

        if(offer.isFavourite) {
            menu?.getItem(0)?.title = "Remove from favourites"
        } else {
            menu?.getItem(0)?.title = "Add to favourites"
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        offer.isFavourite = !(offer.isFavourite)

        if(offer.isFavourite) {
            item.title = "Remove from favourites"
            Toast.makeText(this, "Removed from favourites!", Toast.LENGTH_LONG).show()

        } else {
            item.title = "Add to favourites"
            Toast.makeText(this, "Added to favourites!", Toast.LENGTH_LONG).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("isFavourite", offer.isFavourite)
        intent.putExtra("position", this.intent.getIntExtra("position", -1))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}

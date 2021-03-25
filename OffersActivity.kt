package com.example.calatour

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.calatour.adapters.OffersAdapter
import com.example.calatour.model.Offer
import com.example.calatour.model.OffersList
import com.example.calatour.model.chat_api.AuthenticationRequest
import com.example.calatour.rest_api.ChatAPI
import kotlinx.android.synthetic.main.offers_list_element.*
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.concurrent.schedule

class OffersActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private val detailsId = 1001
    private val chatAPI = ChatAPI.create()

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val intent = Intent(this, OffersDetailsActivity::class.java)

        //Get the clicked offer
        val offer = offersAdapter.getItem(position)
        intent.putExtra("offer", offer)
        intent.putExtra("position", position)
        startActivityForResult(intent, detailsId)
        //startActivity ( intent )
    }

    //init variable with null
    private lateinit var offersAdapter: OffersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)

        offersAdapter = OffersAdapter(this, OffersList().getOffers())

        val offersListView = findViewById<ListView>(R.id.offersListView)
        offersListView.adapter = offersAdapter

        registerForContextMenu(offersListView)

        // make visible the loading animation
        val progressBar = findViewById<ProgressBar>(R.id.offersProgressBar)
        progressBar.visibility = View.VISIBLE
        offersListView.visibility = View.GONE

        // wait 3 seconds until display the offers list
        Timer("OffersLoadingAnimation").schedule(3000) {
            // this is the main thread of the application
            Handler(mainLooper).post{
                progressBar.visibility = View.GONE
                offersListView.visibility = View.VISIBLE
            }
        }

        offersListView.setOnItemClickListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?)
    {
        super.onCreateContextMenu ( menu, v, menuInfo )
        // check if the menu is created for the targeted list
        if (v!!.id == R.id.offersListView)
        {
            // identify the item selected from the list
            val info = menuInfo as AdapterView.AdapterContextMenuInfo
            menu!!.setHeaderTitle ( offersAdapter.getItem(info.position).title )

            // load the visual structure of the menu
            getMenuInflater().inflate ( R.menu.offers_contextual_menu, menu )
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder ( this )
        builder.setTitle ( "Please confirm" )
            .setMessage ( "Are you sure you want to sign out?" )
                //finish tells that you finished everything, you can go back in memory
            .setPositiveButton ( "Yes",  DialogInterface.OnClickListener {
                dialog, which ->  finish()
                chatAPI.globalLogout(AuthenticationRequest("alina", "alina1")).enqueue(object: retrofit2.Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(applicationContext, "There is no internet connection? Server unreachable", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if(response.isSuccessful) {
                            Toast.makeText(applicationContext, "Global logout successful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(applicationContext, "Global logout error", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            })
            .setNegativeButton ( "No", null )
        builder.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate ( R.menu.offers_options_menu, menu )

        // change programmatically some menu items
        return super.onCreateOptionsMenu(menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        if(item.itemId == R.id.offerMenuAddOfer) {
            val offer = Offer("new offer", "new description", 500, R.drawable.offer_2)
            offersAdapter.addOffer(info.position, offer)
        }

        if(item.itemId == R.id.offerMenuRemoveOffer) {
            offersAdapter.removeOffer(info.position)
        }

        //Generates an event, you need to redisplay data
        offersAdapter.notifyDataSetChanged()

        return super.onContextItemSelected(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.signOut) {
            onBackPressed()
        } else if(item.itemId == R.id.resetList) {
                offersAdapter.resendDataSource(OffersList().getOffers())
                offersAdapter.notifyDataSetChanged()
        } else if(item.itemId == R.id.clearFavourites) {

        } else if(item.itemId == R.id.chatOption) {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity ( intent )
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == detailsId) {
            if(resultCode == Activity.RESULT_OK) {
                val isFavourite = data!!.getBooleanExtra("isFavourite", false)
                val position = data!!.getIntExtra("position", -1)

                if(position > -1) {
                    offersAdapter.getItem(position).isFavourite = isFavourite
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}

package com.example.calatour
import android.content.Intent

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.calatour.model.UserInfoSingleton
import com.example.calatour.model.chat_api.AuthenticationRequest
import com.example.calatour.model.chat_api.AuthenticationResponse
import com.example.calatour.model.chat_api.ErrorDetails
import com.example.calatour.rest_api.ChatAPI
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class SigninActivity : AppCompatActivity(), View.OnClickListener {

    private val chatAPI = ChatAPI.create()

    override fun onClick(v: View?) {
        //Get values from input
        val username = findViewById<EditText>(R.id.usernameEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        var usernameValue = username.text.toString()
        var passwordValue = password.text.toString()

        //Get the error messages
        val usernameError = findViewById(R.id.errorUsernameTextView) as TextView
        val passwordError = findViewById<TextView>(R.id.errorPasswordTextView)

        var ok = 0;

        //Check username
        if(usernameValue != ""){
            if(usernameValue.length < 4) {
                usernameError.setText(R.string.usernameShort)
            } else {
                usernameError.setText("")
                ok++;
            }
        }

        //Check password
        if(passwordValue != "") {
            if(passwordValue.length < 7) {
                passwordError.setText(R.string.passwordShort)
            } else {
                passwordError.setText("")
                ok++;
            }
        }

        //Check credentials
        /*val loginMsg = findViewById<TextView>(R.id.loginMsgTextView)
        if(ok == 2) {
            if(usernameValue.equals("admin") && passwordValue.equals("password")) {
                loginMsg.setText(R.string.successLogin)
                loginMsg.setTextColor(resources.getColor(R.color.myGreen))
                val intent = Intent(this, OffersActivity::class.java)
                startActivity ( intent )
            } else {
                loginMsg.setText(R.string.invalidLogin)
                loginMsg.setTextColor(resources.getColor(R.color.myRed))
            }
        }*/

        chatAPI.authenticate(AuthenticationRequest(usernameValue, passwordValue)).enqueue(object : retrofit2.Callback<AuthenticationResponse> {
            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                val loginMsg = findViewById<TextView>(R.id.loginMsgTextView)
                loginMsg.text = "Please check your internet connection. Server unreachable"
                loginMsg.setTextColor(resources.getColor(R.color.myRed))
            }

            override fun onResponse(
                call: Call<AuthenticationResponse>,
                response: Response<AuthenticationResponse>
            ) {
                if(response.isSuccessful) {
                    val loginMsg = findViewById<TextView>(R.id.loginMsgTextView)
                    loginMsg.text = ""

                    UserInfoSingleton.userId = response.body()!!.id
                    UserInfoSingleton.token = response.body()!!.token
                    UserInfoSingleton.displayName = response.body()!!.display

                    val intent = Intent(applicationContext, OffersActivity::class.java)
                    startActivity ( intent )

                } else {
                    var errorDetails = ErrorDetails("No error details available")
                    try {
                        if(response.errorBody() != null) {
                            val rawErrorDetails = response.errorBody()!!.string()
                            val parser = Gson()
                            errorDetails = parser.fromJson(rawErrorDetails, ErrorDetails::class.java)
                        }
                    } catch (e: Exception) {
                        errorDetails = ErrorDetails("Cannot retrieve error details")
                    }

                    val loginMsg = findViewById<TextView>(R.id.loginMsgTextView)
                    loginMsg.text = errorDetails.message
                    loginMsg.setTextColor(resources.getColor(R.color.myRed))
                }
            }

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin_main)

        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener(this)

        val globalLogoutButton = findViewById<Button>(R.id.globalLogoutButton)
        globalLogoutButton.setOnClickListener(this)
    }
}

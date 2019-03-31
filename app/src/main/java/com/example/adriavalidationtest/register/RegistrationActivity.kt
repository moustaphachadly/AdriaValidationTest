package com.example.adriavalidationtest.register

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adriavalidationtest.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity(), RegistrationContract.View {

    var registrationPresenter: RegistrationPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Registration button click
        button_register.setOnClickListener {
            val userName = edittext_username_register.text.toString()
            val email = edittext_email_register.text.toString()
            val password = edittext_password_register.text.toString()

            registrationPresenter?.registerUser(userName, email, password)
        }

        // Login activity button click
        text_already_have_account.setOnClickListener {
            // Popup the registration activity
            finish()
        }

        // Setup the presenter
        if (registrationPresenter == null) {
            registrationPresenter = RegistrationPresenter(this)
        }
    }

    override fun emptyField() {
        Toast.makeText(this, "Please enter text in UserName, Email and Password fields", Toast.LENGTH_SHORT).show()
    }

    override fun registrationSuccess() {
        // Go to chat list
    }

    override fun registrationFailed(task: Task<AuthResult>) {
        // If sign in fails, display a message to the user.
        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
    }

}

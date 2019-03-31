package com.example.adriavalidationtest.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.adriavalidationtest.R
import com.example.adriavalidationtest.register.RegistrationActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    var loginPresenter: LoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        // Login button click
        button_login.setOnClickListener {
            val email = edittext_email_login.text.toString()
            val password = edittext_password_login.text.toString()

            loginPresenter?.loginUser(email, password)
        }

        // Registration activity button click
        text_create_new_account.setOnClickListener{
            // Launch the registration activity
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

        // Setup the presenter
        if (loginPresenter == null) {
            loginPresenter = LoginPresenter(this)
        }
    }

    override fun emptyField() {
        Toast.makeText(this, "Please enter text in Email and Password fields", Toast.LENGTH_SHORT).show()
    }

    override fun loginSuccess() {
        // Go to chat list
    }

    override fun loginFailed(task: Task<AuthResult>) {
        // If sign in fails, display a message to the user.
        Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
    }

}
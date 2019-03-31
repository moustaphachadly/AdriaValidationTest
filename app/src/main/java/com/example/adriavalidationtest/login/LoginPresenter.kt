package com.example.adriavalidationtest.login

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter : LoginContract.Presenter {

    var loginView: LoginContract.View?
    private lateinit var auth: FirebaseAuth

    constructor(loginView: LoginContract.View) {
        this.loginView = loginView

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    override fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            loginView?.emptyField()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener() { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")

                        // Update UI
                        loginView?.loginSuccess()
                    } else {
                        loginView?.loginFailed(task)
                    }
                }
        }
    }
}
package com.example.adriavalidationtest

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

class RegistrationPresenter : RegistrationContract.Presenter {

    var registartionView: RegistrationContract.View?
    private lateinit var auth: FirebaseAuth

    constructor(registartionView: RegistrationContract.View) {
        this.registartionView = registartionView

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
    }

    override fun registerUser(userName: String, email: String, password: String) {
        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            registartionView?.emptyField()
        } else {
            // Firebase Authentication to create a user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")

                        // Update user infos
                        auth.currentUser?.let {
                            updateUserProfile(it, userName)
                        }
                    } else {
                        registartionView?.registrationFailed(task)
                    }
                }
        }
    }

    private fun updateUserProfile(user: FirebaseUser, displayName: String) {
        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(displayName)
            .build()

        user?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                    registartionView?.registrationSuccess()
                }
            }
    }
}
package com.example.adriavalidationtest.register

import android.content.ContentValues.TAG
import android.util.Log
import com.example.adriavalidationtest.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationPresenter : RegistrationContract.Presenter {

    var registartionView: RegistrationContract.View?
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    constructor(registartionView: RegistrationContract.View) {
        this.registartionView = registartionView

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()
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
                            saveUserToFirestore(it, userName)
                        }
                    } else {
                        registartionView?.registrationFailed(task)
                    }
                }
        }
    }

    private fun saveUserToFirestore(firebaseUser: FirebaseUser, userName: String) {
        val uid = firebaseUser.uid
        val user = User(uid, userName)

        db.collection("users").document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "DocumentSnapshot successfully written!")
                registartionView?.registrationSuccess()
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
}
package com.example.adriavalidationtest.register

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class RegistrationContract {

    interface View {
        fun emptyField()
        fun registrationSuccess()
        fun registrationFailed(task: Task<AuthResult>)
    }

    interface Presenter {
        fun registerUser(name: String, email: String, password: String)
    }

}
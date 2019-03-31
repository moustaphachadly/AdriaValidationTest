package com.example.adriavalidationtest.login

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

class LoginContract {

    interface View {
        fun emptyField()
        fun loginSuccess()
        fun loginFailed(task: Task<AuthResult>)
    }

    interface Presenter {
        fun loginUser(email: String, password: String)
    }

}
package com.example.adriavalidationtest.models

data class User(val uid: String, val username: String) {
    constructor() : this("", "")
}
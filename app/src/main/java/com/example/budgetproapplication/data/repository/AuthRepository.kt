package com.example.budgetproapplication.data.repository

import com.example.budgetproapplication.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")
    private var currentUser: User? = null

    suspend fun signUp(name: String, email: String, password: String): User = withContext(Dispatchers.IO) {
        // TODO: Implement actual registration
        // For now, just create a dummy user
        User(
            id = "1",
            name = name,
            email = email
        ).also { currentUser = it }
    }

    suspend fun signIn(email: String, password: String): User = withContext(Dispatchers.IO) {
        // TODO: Implement actual authentication
        // For now, just create a dummy user
        User(
            id = "1",
            name = "Test User",
            email = email
        ).also { currentUser = it }
    }

    suspend fun signOut() = withContext(Dispatchers.IO) {
        currentUser = null
    }

    suspend fun getCurrentUser(): User? = withContext(Dispatchers.IO) {
        currentUser
    }
} 
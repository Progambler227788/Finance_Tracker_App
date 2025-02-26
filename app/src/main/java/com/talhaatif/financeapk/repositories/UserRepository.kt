package com.talhaatif.financeapk.repositories

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.talhaatif.financeapk.firebase.Util
import com.talhaatif.financeapk.firebase.Variables
import com.talhaatif.financeapk.models.BudgetModel

class UserRepository {
    private val auth: FirebaseAuth = Variables.auth
    private val db: FirebaseFirestore = Variables.db
    private val storageRef = FirebaseStorage.getInstance().reference

    private val _authState = MutableLiveData<Boolean>()
    val authState: LiveData<Boolean> get() = _authState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun signUp(
        context: Context,
        email: String,
        password: String,
        name: String,
        currency: String,
        imageUri: Uri?,
        bitmap: Bitmap?
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    uid?.let { saveUserToFirestore(context, uid, email, name, currency, imageUri, bitmap) }
                } else {
                    _errorMessage.postValue(task.exception?.message ?: "Signup failed")
                }
            }
    }
    private fun saveUserToFirestore(
        context: Context,
        uid: String,
        email: String,
        name: String,
        currency: String,
        imageUri: Uri?,
        bitmap: Bitmap?
    ) {
        val finalImageUri = imageUri ?: Variables.getRandomAvatar(context)

        val storageRef = storageRef.child("users/$uid")
        val uploadTask = storageRef.putFile(finalImageUri)

        uploadTask.continueWithTask { task ->
            task.exception?.let { throw it } // Properly check and throw exception
            storageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result.toString()
                val user = hashMapOf(
                    "uid" to uid,
                    "name" to name,
                    "email" to email,
                    "currency" to currency,
                    "image" to downloadUri
                )

                val budget = hashMapOf(
                    "uid" to uid,
                    "income" to 0.0,
                    "expense" to 0.0,
                    "balance" to 0.0
                )

                val batch = db.batch()
                val userRef = db.collection("users").document(uid)
                val budgetRef = db.collection("budget").document(uid)

                batch.set(userRef, user)
                batch.set(budgetRef, budget)

                batch.commit().addOnSuccessListener {
                    Log.d("Firestore", "Batch write successful")
                    Util().saveLocalData(context, "currency", currency)
                    _authState.postValue(true)
                }.addOnFailureListener {
                    _errorMessage.postValue("Error saving user: ${it.message}")
                }
            } else {
                _errorMessage.postValue("Error retrieving image URL")
            }
        }.addOnFailureListener {
            _errorMessage.postValue("Error uploading image: ${it.message}")
        }
    }

//    private fun saveUserToFirestore(
//        context: Context,
//        uid: String,
//        email: String,
//        name: String,
//        currency: String,
//        imageUri: Uri?,
//        bitmap: Bitmap?
//    ) {
//        // If no image is selected, use a random avatar
//        val finalImageUri = imageUri ?: Variables.getRandomAvatar(context)
//
//        val storageRef = storageRef.child("users/$uid")
//        val uploadTask = storageRef.putFile(finalImageUri)
//
//        uploadTask.continueWithTask { task ->
//            if (!task.isSuccessful) {
//                throw task.exception ?: Exception("Image upload failed")
//            }
//            storageRef.downloadUrl
//        }.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val downloadUri = task.result.toString()
//                val user = hashMapOf(
//                    "uid" to uid,
//                    "name" to name,
//                    "email" to email,
//                    "currency" to currency,
//                    "image" to downloadUri
//                )
//                val budget = BudgetModel(0.0, 0.0, 0.0)
//
//                db.collection("users").document(uid).set(user)
//                    .addOnSuccessListener {
//                        db.collection("budget").document(uid).set(budget)
//
//                        Util().saveLocalData(context, "currency", currency)
//                        _authState.postValue(true)
//                    }
//                    .addOnFailureListener {
//                        _errorMessage.postValue("Error saving user: ${it.message}")
//                    }
//            } else {
//                _errorMessage.postValue("Error retrieving image URL")
//            }
//        }.addOnFailureListener {
//            _errorMessage.postValue("Error uploading image: ${it.message}")
//        }
//    }

    fun login(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    user?.let {
                        Util().saveLocalData(context, "uid", it.uid)
                        Util().saveLocalData(context, "auth", "true")
                    }
                    _authState.postValue(true)
                } else {
                    _errorMessage.postValue("Login failed: ${task.exception?.message}")
                }
            }
    }
}

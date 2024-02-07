package com.jetpackcomposethe.thread.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.jetpackcomposethe.thread.model.UserModel
import com.jetpackcomposethe.thread.utils.SharedPref
import java.util.UUID


class AuthViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()
    val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: MutableLiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    private val storageRef = Firebase.storage.getReference()
    private val imageRef = storageRef.child("users/${UUID.randomUUID()}.jpg")

    init {
        _firebaseUser.value = auth.currentUser
    }


    // Login

    fun login(email: String, password: String, context: Context) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)

                    getData(auth.currentUser!!.uid,context)
                } else {
                    _error.postValue("Something went wrong")

                }
            }
    }

    private fun getData(uid: String,context: Context) {




        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val userData = p0.getValue(UserModel::class.java)
                SharedPref.storeData(
                    userData!!.name,
                    userData!!.email,
                    userData!!.bio,
                    userData!!.username,
                    userData!!.imgUrl,
                    context
                )
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        }
        )

    }

    // Register

    fun Register(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        imageuri: Uri,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _firebaseUser.postValue(auth.currentUser)
                    saveImage(
                        email,
                        password,
                        name,
                        bio,
                        username,
                        imageuri,
                        auth.currentUser?.uid,
                        context
                    )
                } else {
                    _error.postValue("Something went wrong")
                }
            }
    }

    private fun saveImage(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        imageuri: Uri,
        uid: String?,
        context: Context
    ) {

        val uploadTask = imageRef.putFile(imageuri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(email, password, name, bio, username, it.toString(), uid, context)
            }
        }
    }

    private fun saveData(
        email: String,
        password: String,
        name: String,
        bio: String,
        username: String,
        toString: String,
        uid: String?,
        context: Context
    ) {

        val firestoreDb=Firebase.firestore
        val followersRef=firestoreDb.collection("followers").document(uid!!)
        val followingRef=firestoreDb.collection("following").document(uid!!)

        followersRef.set(
            mapOf("followingIds" to listOf<String>()))
        followingRef.set(
            mapOf("followersIds" to listOf<String>()))

        val userData = UserModel(email, password, name, bio, username, toString, uid!!)

        userRef.child(uid!!).setValue(userData)
            .addOnSuccessListener {
                SharedPref.storeData(name, email, bio, username, toString, context)
            }.addOnFailureListener {

            }

    }

    fun logout() {
        auth.currentUser?.let { user ->
            auth.signOut()
            _firebaseUser.postValue(null)
        }
    }


}
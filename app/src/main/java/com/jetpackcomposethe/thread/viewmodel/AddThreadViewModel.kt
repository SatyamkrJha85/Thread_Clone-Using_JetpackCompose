package com.jetpackcomposethe.thread.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage
import com.jetpackcomposethe.thread.model.ThreadModel
import java.util.UUID


class AddThreadViewModel : ViewModel() {

    val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("threads")



    private val _isposted = MutableLiveData<Boolean>()
    val isposted: LiveData<Boolean> = _isposted

    private val storageRef = Firebase.storage.getReference()
    private val imageRef = storageRef.child("threads/${UUID.randomUUID()}.jpg")




     fun saveImage(

         thread: String,
         imageuri: Uri,
         userId: String,
    ) {

        val uploadTask = imageRef.putFile(imageuri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener {
                saveData(thread, userId, it.toString())
            }
        }
    }

     fun saveData(
         thread: String,
         userId: String,
         imageuri: String

    ) {

        val threadData = ThreadModel(thread,userId,imageuri,System.currentTimeMillis().toString())

        userRef.child(userRef.push().key!!).setValue(threadData)
            .addOnSuccessListener {
                _isposted.postValue(true)
            }.addOnFailureListener {
                _isposted.postValue(false)

            }

    }


}
package com.jetpackcomposethe.thread.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.storage.storage
import com.jetpackcomposethe.thread.model.ThreadModel
import com.jetpackcomposethe.thread.model.UserModel
import java.util.UUID


class SearchViewModel : ViewModel() {

    val db = FirebaseDatabase.getInstance()
    val users = db.getReference("users")



    private val _users = MutableLiveData<List<UserModel>>()
    val userList: LiveData<List<UserModel>> = _users

    init {
        fetchUsers {
            _users.value=it
        }
    }

    private fun fetchUsers(onResult: (List<UserModel>)->Unit){
        users.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val result= mutableListOf<UserModel>()
                for (threadSnapshot in p0.children){
                    val thread=threadSnapshot.getValue(UserModel::class.java)

                    result.add(thread!!)
                }

                onResult(result)
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    fun fetchUserFromThread(thread: ThreadModel,onResult:(UserModel)->Unit){
        db.getReference("users").child(thread.userId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    val user=p0.getValue(UserModel::class.java)
                    user?.let(onResult)
                }

                override fun onCancelled(p0: DatabaseError) {

                }

            })
    }

}
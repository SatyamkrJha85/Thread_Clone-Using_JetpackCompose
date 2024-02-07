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


class HomeViewModel : ViewModel() {

    val db = FirebaseDatabase.getInstance()
    val thread = db.getReference("threads")



    private val _threadsAndUsers = MutableLiveData<List<Pair<ThreadModel,UserModel>>>()
    val threadsAndUsers: LiveData<List<Pair<ThreadModel,UserModel>>> = _threadsAndUsers

    init {
        fetchThreadAndUsers { _threadsAndUsers.value=it }
    }

    private fun fetchThreadAndUsers(onResult: (List<Pair<ThreadModel,UserModel>>)->Unit){
        thread.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val result= mutableListOf<Pair<ThreadModel,UserModel>>()
                for (threadSnapshot in p0.children){
                    val thread=threadSnapshot.getValue(ThreadModel::class.java)
                    thread.let {
                        fetchUserFromThread(it!!){
                            user ->
                            result.add(0,it to user)

                            if (result.size==p0.childrenCount.toInt()){
                                onResult(result)
                            }
                        }
                    }
                }
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
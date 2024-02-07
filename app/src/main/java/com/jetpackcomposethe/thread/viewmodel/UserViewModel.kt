package com.jetpackcomposethe.thread.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.jetpackcomposethe.thread.model.ThreadModel
import com.jetpackcomposethe.thread.model.UserModel


class UserViewModel : ViewModel() {

    val db = FirebaseDatabase.getInstance()
    val threadRef = db.getReference("threads")
    val userRef = db.getReference("users")


    private var _threads=MutableLiveData(listOf<ThreadModel>())
    val threads:LiveData<List<ThreadModel>> get()=_threads

    private var _followerList=MutableLiveData(listOf<String>())
    val followerList:LiveData<List<String>> get()=_followerList

    private var _followingList=MutableLiveData(listOf<String>())
    val followingList:LiveData<List<String>> get()=_followingList

    private var _users=MutableLiveData(UserModel())
    val users:LiveData<UserModel> get()=_users

    fun fetchUser(uid:String){
        userRef.child(uid).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val user =p0.getValue(UserModel::class.java)
                _users.postValue(user)
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }

    fun fetchThreads(uid:String){
        threadRef.orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val threadList =p0.children.mapNotNull { it.getValue(ThreadModel::class.java) }
                _threads.postValue(threadList)
            }

            override fun onCancelled(p0: DatabaseError) {

            }

        })
    }


    val firestoreDb=Firebase.firestore
    fun followUsers(userId:String,currentUserId:String){
        val ref=firestoreDb.collection("following").document(currentUserId)
        val followerref=firestoreDb.collection("followers").document(userId)

        ref.update("followingIds",FieldValue.arrayUnion(userId))
        followerref.update("followerIds",FieldValue.arrayUnion(currentUserId))


    }

    fun getFollowers(userId:String){
        firestoreDb.collection("followers").document(userId)
            .addSnapshotListener{value,error->
                val followerIds=value?.get("followerIds")as?List<String>?: listOf()
                _followerList.postValue(followerIds)
            }
    }

    fun getFollowing(userId:String){
        firestoreDb.collection("following").document(userId)
            .addSnapshotListener{value,error->
                val followingIds=value?.get("followingIds")as?List<String>?: listOf()
                _followingList.postValue(followingIds)
            }
    }


}
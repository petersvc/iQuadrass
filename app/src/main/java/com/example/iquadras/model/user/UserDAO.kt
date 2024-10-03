package com.example.iquadras.model.user

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class UserDAO {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("user")


    fun getAll(callback: (List<User>) -> Unit) {
        collection.get()
            .addOnSuccessListener { document ->
                val users = document.toObjects<User>()
                callback(users)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }


    fun findByEmail(email: String, callback: (User?) -> Unit) {
        collection.whereEqualTo("email", email).get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val user = document.documents[0].toObject<User>()
                    callback(user)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }


    fun findByName(name: String, callback: (User?) -> Unit) {
        collection.whereEqualTo("name", name).get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val user = document.documents[0].toObject<User>()
                    callback(user)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }


    fun findById(id: String, callback: (User) -> Unit) {
        collection.document(id).get()
            .addOnSuccessListener { document ->
                val user = document.toObject<User>()
                callback(user!!)
            }
            .addOnFailureListener {
                callback(User())
            }
    }


    fun save(user: User, callback: (User) -> Unit) {
        collection.add(user)
            .addOnSuccessListener { documentReference ->
                documentReference.get().addOnSuccessListener { documentSnapshot ->
                    val addedUser = documentSnapshot.toObject<User>()
                    callback(addedUser ?: user)
                }
            }
            .addOnFailureListener {
                callback(User())
            }
    }


    fun update(user: User, callback: (Boolean) -> Unit) {
        collection.document(user.id).set(user, SetOptions.merge())
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

    fun delete(user: User, callback: (Boolean) -> Unit){
        collection.document(user.id).delete()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }

}

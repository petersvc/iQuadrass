package com.example.iquadras.model.court

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class CourtDAO {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("court")

    fun getAll(callback: (List<Court>) -> Unit) {
        collection.get()
            .addOnSuccessListener { document ->
                val courts = document.toObjects<Court>()
                callback(courts)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun findByName(name: String, callback: (Court?) -> Unit) {
        collection.whereEqualTo("name", name).get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val court = document.documents[0].toObject<Court>()
                    callback(court)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun findById(id: String, callback: (Court) -> Unit) {
        collection.document(id).get()
            .addOnSuccessListener { document ->
                val court = document.toObject<Court>()
                callback(court!!)
            }
            .addOnFailureListener {
                callback(Court())
            }
    }


    fun save(court: Court, callback: (Court) -> Unit) {
        collection.add(court)
            .addOnSuccessListener { documentReference ->
                documentReference.get().addOnSuccessListener { documentSnapshot ->
                    val addedCourt = documentSnapshot.toObject<Court>()
                    callback(addedCourt ?: court)
                }
            }
            .addOnFailureListener {
                callback(Court())
            }
    }

    fun delete(id: String, callback: (Boolean) -> Unit) {
        collection.document(id).delete()
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }


}
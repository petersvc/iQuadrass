package com.example.iquadras.model.booking

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects

class BookingDao {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("booking")

    fun getAll(callback: (List<Booking>) -> Unit) {
        collection.get()
            .addOnSuccessListener { document ->
                val bookings = document.toObjects<Booking>()
                callback(bookings)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun findById(id: String, callback: (Booking) -> Unit) {
        collection.document(id).get()
            .addOnSuccessListener { document ->
                val booking = document.toObject<Booking>()
                callback(booking!!)
            }
            .addOnFailureListener {
                callback(Booking())
            }
    }


    fun save(booking: Booking, callback: (Booking) -> Unit) {
        collection.add(booking)
            .addOnSuccessListener { documentReference ->
                documentReference.get().addOnSuccessListener { documentSnapshot ->
                    val addedBooking = documentSnapshot.toObject<Booking>()
                    callback(addedBooking ?: booking)
                }
            }
            .addOnFailureListener {
                callback(Booking())
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
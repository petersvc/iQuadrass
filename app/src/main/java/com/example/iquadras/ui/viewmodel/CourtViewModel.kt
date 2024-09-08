package com.example.iquadras.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iquadras.model.court.Court
import com.example.iquadras.model.court.CourtDAO
import kotlinx.coroutines.launch

class CourtViewModel : ViewModel() {
    private val courtDAO = CourtDAO()

    fun addCourt(court: Court, callback: (Court) -> Unit) {
        viewModelScope.launch {
            courtDAO.save(court) {
                callback(it)
            }
        }
    }

    fun getAllCourts(callback: (List<Court>) -> Unit) {
        viewModelScope.launch {
            courtDAO.getAll {
                callback(it)
            }
        }
    }

    fun findCourtById(id: String, callback: (Court?) -> Unit) {
        viewModelScope.launch {
            courtDAO.findById(id) {
                callback(it)
            }
        }
    }

    fun findCourtByName(name: String, callback: (Court?) -> Unit) {
        viewModelScope.launch {
            courtDAO.findByName(name) {
                callback(it)
            }
        }
    }

    fun saveCourt(newCourt: Court, callback: (Court) -> Unit) {
        viewModelScope.launch {
            courtDAO.save(newCourt) {
                callback(it)
            }
        }
    }

    fun deleteCourt(id: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            courtDAO.delete(id) { success ->
                callback(success)
            }
        }
    }
}

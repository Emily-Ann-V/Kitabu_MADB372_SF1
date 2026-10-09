package com.example.kitabu_madb372_sf1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitabu_madb372_sf1.data.AppDatabase
import com.example.kitabu_madb372_sf1.data.BookEntity
import com.example.kitabu_madb372_sf1.data.BookingEntity
import com.example.kitabu_madb372_sf1.data.BookingStatus
import com.example.kitabu_madb372_sf1.data.JoinedBookingData
import com.example.kitabu_madb372_sf1.repository.BookingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookingViewModel(application: Application) : AndroidViewModel(application) {

    // Connecting to the database
    private val database = AppDatabase.getDatabase(application)

    // Connecting the repository to the Booking DAO
    private val repository = BookingRepository(database.bookingDao(), database.bookDao())

    // Getting filtered bookings from the repository
    val bookings: StateFlow<List<JoinedBookingData>> =
        repository.getFilteredBookings(BookingStatus.RETURNED)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // Adding booking from the repository
    fun makeBooking(book: BookEntity, duration: Int, onSuccess: () -> Unit, onFailure: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.makeBooking(book, duration)
                onSuccess()
            } catch (_: Exception) {
                onFailure()
            }
        }
    }

    // Updating booking information when being actively rented from the repository
    fun updateActiveStatus(
        bookingId: Int,
        duration: Int,
        bookId: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.updateActiveStatus(bookingId, BookingStatus.ACTIVE, duration, bookId)
                onSuccess()
            } catch (_: Exception) {
                onFailure()
            }
        }
    }

    // Updating booking information when booking is renewed from the repository
    fun renewBooking(
        bookingId: Int,
        duration: Int,
        returnDeadline: Long,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.renewBooking(bookingId, duration, returnDeadline)
                onSuccess()
            } catch (_: Exception) {
                onFailure()
            }
        }
    }

    // Updating booking information when book is returned from the repository
    fun updateReturnStatus(
        bookingId: Int,
        bookId: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.updateReturnStatus(bookingId, BookingStatus.RETURNED, bookId)
                onSuccess()
            } catch (_: Exception) {
                onFailure()
            }
        }
    }

    // Deleting booking and updating book information when booking is cancelled from the repository
    fun cancelBooking(
        booking: BookingEntity,
        bookId: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.cancelBooking(booking, bookId)
                onSuccess()
            } catch (_: Exception) {
                onFailure()
            }
        }
    }
}
package com.example.kitabu_madb372_sf1.data

import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

interface BookingDao {
    // Creating a booking
    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    // Reading booking list

    // Showing pending and active bookings
    @Query(
        "SELECT * FROM booking" +
                " WHERE status <> RETURNED" +
                " ORDER BY bookOwnerId ASC"
    )
    fun getFilteredBookings(): Flow<List<BookingEntity>>

    // Extending rental return deadline
    @Query("UPDATE booking SET returnDeadline = :returnDeadline WHERE bookingId = :bookingId")
    suspend fun renewReturnDeadline(
        bookingId: Int,
        returnDeadline: Long
    )
    // ToDo: Update duration period

  // ToDo: Update return status

    @Delete
    suspend fun cancelBooking(booking: BookingEntity)
}
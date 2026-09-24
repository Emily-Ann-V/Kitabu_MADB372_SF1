package com.example.kitabu_madb372_sf1.data

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    // Creating a booking
    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    // Reading booking list

    // Showing filtered bookings
    @Query(
        "SELECT * FROM booking" +
                " WHERE status <> :status" +
                " ORDER BY bookOwnerId ASC"
    )
    fun getFilteredBookings(status: BookingStatus): Flow<List<BookingEntity>>

    // Extending rental return deadline
    @Query(
        "UPDATE booking SET returnDeadline = :returnDeadline" +
                " WHERE bookingId = :bookingId"
    )
    suspend fun renewReturnDeadline(
        bookingId: Int,
        returnDeadline: Long
    )

    // Updating rental status
    @Query(
        "UPDATE booking SET status = :status" +
                " WHERE bookingId = :bookingId"
    )
    suspend fun updateStatus(
        bookingId: Int,
        status: BookingStatus
    )

    // Extending rental duration period
    @Query(
        "UPDATE booking SET duration = :duration" +
                " WHERE bookingId = :bookingId"
    )
    suspend fun updateDuration(
        bookingId: Int,
        duration: Int
    )

    // Deleting booking
    @Delete
    suspend fun cancelBooking(booking: BookingEntity)
}
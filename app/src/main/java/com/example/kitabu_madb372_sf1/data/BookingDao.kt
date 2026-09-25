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

    // Showing filtered bookings with relevant book information
    @Query(
        "SELECT booking.*, book.*" +
                " FROM booking" +
                " INNER JOIN book ON booking.bookOwnerId = book.bookID" +
                " WHERE booking.status <> :status"
    )
    fun getFilteredBookings(status: BookingStatus): Flow<List<JoinedBookingData>>


    // Setting the booking date
    @Query(
        "UPDATE booking SET bookingDate = :bookingDate" +
                " WHERE bookingId = :bookingId"
    )
    suspend fun updateBookingDate(bookingId: Int, bookingDate: Long)

    // Setting the return deadline
    @Query(
        "UPDATE booking SET returnDeadline = :returnDeadline" +
                " WHERE bookingId = :bookingId"
    )
    suspend fun updateReturnDeadline(bookingId: Int, returnDeadline: Long)

    // Setting the duration period
    @Query(
        "UPDATE booking SET duration = :duration" +
                " WHERE bookingId = :bookingId"
    )
    suspend fun updateDuration(bookingId: Int, duration: Int)

    // Updating booking status
    @Query(
        "UPDATE booking SET status = :status" +
                " WHERE bookingId = :bookingId"
    )
    suspend fun updateStatus(bookingId: Int, status: BookingStatus)

    // Deleting booking
    @Delete
    suspend fun cancelBooking(booking: BookingEntity)
}
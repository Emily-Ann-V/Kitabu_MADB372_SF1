package com.example.kitabu_madb372_sf1.repository

import com.example.kitabu_madb372_sf1.data.BookDao
import com.example.kitabu_madb372_sf1.data.BookEntity
import com.example.kitabu_madb372_sf1.data.BookingDao
import com.example.kitabu_madb372_sf1.data.BookingEntity
import com.example.kitabu_madb372_sf1.data.BookingStatus

class BookingRepository(
    private val bookingDao: BookingDao,
    private val bookDao: BookDao
) {

    // Getting filtered bookings from the database
    fun getFilteredBookings(status: BookingStatus) =
        bookingDao.getFilteredBookings(status)

    // Creating a booking
    suspend fun makeBooking(book: BookEntity, duration: Int) {
        val booking = BookingEntity(
            bookOwnerId = book.bookId,
            userName = "User",
            bookingDate = 0L,
            returnDeadline = 0L,
            duration = duration,
            status = BookingStatus.PENDING
        )

        bookingDao.insertBooking(booking)
        bookDao.updateAvailability(book.bookId, false)
    }

    // Updating booking information when being actively rented
    suspend fun updateActiveStatus(
        bookingId: Int,
        status: BookingStatus,
        duration: Int,
        bookId: Int
    ) {
        bookingDao.updateStatus(bookingId, status)
        bookDao.updateAvailability(bookId, true)
        setBookingDates(bookingId, duration)
    }

    // Setting booking dates
    suspend fun setBookingDates(bookingId: Int, duration: Int) {
        val bookingDate = System.currentTimeMillis()

        val returnDeadline = bookingDate + (duration * 24 * 60 * 60 * 1000L)

        bookingDao.updateBookingDate(bookingId, bookingDate)
        bookingDao.updateReturnDeadline(bookingId, returnDeadline)
    }

    // Updating booking information when booking is renewed
    suspend fun renewBooking(bookingId: Int, duration: Int, returnDeadline: Long) {
        bookingDao.updateDuration(bookingId, duration * 2)

        val updatedReturnDeadline = returnDeadline + (duration * 24 * 60 * 60 * 1000L)

        bookingDao.updateReturnDeadline(bookingId, updatedReturnDeadline)
    }

    // Updating booking information when book is returned
    suspend fun updateReturnStatus(bookingId: Int, status: BookingStatus, bookId: Int) {
        bookingDao.updateStatus(bookingId, status)
        bookDao.updateAvailability(bookId, true)
    }

    // Deleting booking and updating book information when booking is cancelled
    suspend fun cancelBooking(booking: BookingEntity, bookId: Int) {
        bookingDao.cancelBooking(booking)
        bookDao.updateAvailability(bookId, true)
    }
}

package com.example.kitabu_madb372_sf1.data

import androidx.room3.ColumnTypeConverter // @TypeConverter renamed in Room 3.0

// Converting data SQLite cannot natively store (e.g. Dates, Enum, UUID, Uri)
class StatusConverter {

    // Getting the booking status to save in the database
    @ColumnTypeConverter
    fun bookingStatusToString(status: BookingStatus): String {
        return status.name
    }

    // Getting the booking status from the database
    @ColumnTypeConverter
    fun stringToBookingStatus(status: String): BookingStatus {
        return BookingStatus.valueOf(status)
    }
}
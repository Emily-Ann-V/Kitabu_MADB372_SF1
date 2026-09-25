package com.example.kitabu_madb372_sf1.data

import androidx.room3.Embedded

data class JoinedBookingData(

    // Getting book information
    @Embedded
    val book: BookEntity,

    // Getting booking information
    @Embedded
    val booking: BookingEntity
)
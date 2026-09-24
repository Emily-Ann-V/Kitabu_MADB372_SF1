package com.example.kitabu_madb372_sf1.data

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

// Table that stores booking data
@Entity(
    tableName = "booking",

    // Setting foreign key to link to reference BookEntity.bookId and CASCADE on delete
    foreignKeys = [ForeignKey(
        entity = BookEntity::class,
        parentColumns = ["bookId"],
        childColumns = ["bookOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val bookingId: Int = 0, // Setting primary key to auto generate
    val bookOwnerId: Int,
    val userName: String,
    val bookingDate: Long,
    val returnDeadline: Long,
    val duration: Int, // ToDo
    val status: BookingStatus // Getting status options from the BookingStatus enum class
)
package com.example.kitabu_madb372_sf1.data

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

// Table that stores book data
@Entity(tableName = "book")
data class BookEntity(
    // Setting primary key to auto generate
    @PrimaryKey(autoGenerate = true) val bookId: Int = 0,
    val title: String,
    val author: String,
    val category: String,
    // Setting default value to true
    @ColumnInfo(defaultValue = "1") val isAvailable: Boolean = true
)
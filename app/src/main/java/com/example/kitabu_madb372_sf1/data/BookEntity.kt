package com.example.kitabu_madb372_sf1.data

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

// Table that stores book data
@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) val bookId: Int = 0, // Setting primary key to auto generate
    val imageResId: Int, // ToDo
    val title: String,
    val author: String,
    val category: String,
    @ColumnInfo(defaultValue = "1") val isAvailable: Boolean = true // Setting default value to true
)
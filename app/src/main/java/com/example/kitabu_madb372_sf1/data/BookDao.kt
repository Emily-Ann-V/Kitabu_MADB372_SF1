package com.example.kitabu_madb372_sf1.data

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import kotlinx.coroutines.flow.Flow

// @Insert, @Update, @Delete = suspend | Queries returning Flow <> suspend

@Dao
interface BookDao {
    // Creating a book
    @Insert
    suspend fun insertBook(book: BookEntity)

    // Reading book list

    // Showing all books
    @Query("SELECT * FROM book ORDER BY title ASC")
    fun getAllBooks(): Flow<List<BookEntity>>

    // Showing books containing search input
    @Query(
        "SELECT * FROM book" +
                " WHERE title LIKE '%' || :query || '%'" +
                " OR author LIKE '%' || :query || '%'" +
                " ORDER BY title ASC"
    )
    fun getSearchedBooks(query: String): Flow<List<BookEntity>>

    // Showing books matching availability
    @Query(
        "SELECT * FROM book" +
                " WHERE isAvailable = :isAvailable" +
                " ORDER BY title ASC"
    )
    fun getFilteredBooks(isAvailable: Boolean): Flow<List<BookEntity>>

    // ToDo: Update Availability
}
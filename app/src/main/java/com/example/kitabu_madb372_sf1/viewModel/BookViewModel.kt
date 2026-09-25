package com.example.kitabu_madb372_sf1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitabu_madb372_sf1.data.AppDatabase
import com.example.kitabu_madb372_sf1.data.BookEntity
import com.example.kitabu_madb372_sf1.repository.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    // Connecting to the database
    private val database = AppDatabase.getDatabase(application)

    // Connecting the repository to the Book DAO
    private val repository = BookRepository(database.bookDao())

    // Storing books
    private val _books = MutableStateFlow<List<BookEntity>>(emptyList())
    val books: StateFlow<List<BookEntity>> = _books

    init {
        getAllBooks()
    }

    // Showing all books
    fun getAllBooks() {
        viewModelScope.launch {
            repository.getAllBooks().collect {
                _books.value = it
            }
        }
    }

    // Showing books containing search input
    fun getSearchedBooks(query: String) {
        viewModelScope.launch {
            repository.getSearchedBooks(query).collect {
                _books.value = it
            }
        }
    }

    // Showing books matching availability
    fun getFilteredBooks(isAvailable: Boolean) {
        viewModelScope.launch {
            repository.getFilteredBooks(isAvailable).collect {
                _books.value = it
            }
        }
    }
}
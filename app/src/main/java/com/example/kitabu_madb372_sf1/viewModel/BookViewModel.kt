package com.example.kitabu_madb372_sf1.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitabu_madb372_sf1.data.AppDatabase
import com.example.kitabu_madb372_sf1.data.BookEntity
import com.example.kitabu_madb372_sf1.repository.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

// ToDo
class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val repository = BookRepository(database.bookDao())

    // Getting all books from the repository
    val books: StateFlow<List<BookEntity>> = repository.getAllBooks()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )
}
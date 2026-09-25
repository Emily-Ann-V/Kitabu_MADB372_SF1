package com.example.kitabu_madb372_sf1.repository

import com.example.kitabu_madb372_sf1.data.BookDao

class BookRepository(private val bookDao: BookDao) {

    // Getting all books from the database
    fun getAllBooks() = bookDao.getAllBooks()

    // Getting books containing search input
    fun getSearchedBooks(query: String) = bookDao.getSearchedBooks(query)

    // Getting books matching availability
    fun getFilteredBooks(isAvailable: Boolean) = bookDao.getFilteredBooks(isAvailable)
}

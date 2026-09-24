package com.example.kitabu_madb372_sf1.repository

import com.example.kitabu_madb372_sf1.data.BookDao

class BookRepository(private val bookDao: BookDao) {

    // Getting all books from the database
    fun getAllBooks() = bookDao.getAllBooks()
}

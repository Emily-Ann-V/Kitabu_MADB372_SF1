package com.example.kitabu_madb372_sf1.data

import android.content.Context
import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.AndroidSQLiteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Defining database with tables
@Database(entities = [BookEntity::class, BookingEntity::class], version = 1)

// Connecting database to converter
@ColumnTypeConverters(StatusConverter::class)

// Connecting app to database
abstract class AppDatabase : RoomDatabase() {

    // Connecting book functions
    abstract fun bookDao(): BookDao

    // Connecting booking functions
    abstract fun bookingDao(): BookingDao

    // Using companion object to provide a thread-safe Singleton database instance
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            // Creating the database instance if one does not already exist
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "kitabu_database"
                )
                    .setDriver(AndroidSQLiteDriver())

                    // Configuring to pre-populate sample book records
                    .addCallback(object : Callback() {
                        override suspend fun onCreate(connection: androidx.sqlite.SQLiteConnection) {
                            super.onCreate(connection)

                            CoroutineScope(Dispatchers.IO).launch {
                                INSTANCE?.bookDao()?.insertBooks(sampleBooks)
                            }
                        }
                    })

                    // Building the database
                    .build()

                    // Saving the database instance for future use
                    .also { database ->
                        INSTANCE = database
                    }
            }
        }
    }
}
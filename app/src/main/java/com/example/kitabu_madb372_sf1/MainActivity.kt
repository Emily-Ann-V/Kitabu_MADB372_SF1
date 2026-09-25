package com.example.kitabu_madb372_sf1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kitabu_madb372_sf1.ui.screens.BookCatalogScreen
import com.example.kitabu_madb372_sf1.ui.screens.ReservationsScreen
import com.example.kitabu_madb372_sf1.ui.theme.Kitabu_MADB372_SF1Theme
import com.example.kitabu_madb372_sf1.viewModel.BookViewModel
import com.example.kitabu_madb372_sf1.viewModel.BookingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Kitabu_MADB372_SF1Theme {

                val navController = rememberNavController() // Creating the navigation controller
                val bookViewModel: BookViewModel = viewModel() // Creating the BookViewModel
                val bookingViewModel: BookingViewModel =
                    viewModel() // Creating the BookingViewModel

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->

                    NavHost( // Defining the app's navigation routes
                        navController = navController,
                        startDestination = "catalog",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("catalog") {
                            BookCatalogScreen(
                                navController = navController, // Providing navigation control for this screen
                                bookViewModel = bookViewModel, // Setting the BookViewModel
                                bookingViewModel = bookingViewModel // Setting the BookingViewModel
                            )
                        }

                        composable("reservations") {
                            ReservationsScreen(
                                navController = navController, // Providing navigation control for this screen
                                bookingViewModel = bookingViewModel // Setting the BookingViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookCatalogScreenPreview() {
    Kitabu_MADB372_SF1Theme {
        BookCatalogScreen(
            navController = rememberNavController(),
            bookViewModel = viewModel(),
            bookingViewModel = viewModel()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReservationsScreenPreview() {
    Kitabu_MADB372_SF1Theme {
        ReservationsScreen(navController = rememberNavController(), bookingViewModel = viewModel())
    }
}
package com.example.kitabu_madb372_sf1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.kitabu_madb372_sf1.data.BookingStatus
import com.example.kitabu_madb372_sf1.data.JoinedBookingData
import com.example.kitabu_madb372_sf1.viewModel.BookingViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@Composable
fun ReservationsScreen(
    modifier: Modifier = Modifier,
    navController: NavController, // Displaying the reservations screen
    bookingViewModel: BookingViewModel // Providing the ViewModel for managing booking data
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        Header("Your Reservations")
        Spacer(modifier = Modifier.height(20.dp))

        ActiveBookingsList(
            bookingViewModel, // Providing the ViewModel
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))
        Footer("Our Catalog", "catalog", navController)
    }
}

// Creating book grid
@Composable
fun ActiveBookingsList(bookingViewModel: BookingViewModel, modifier: Modifier = Modifier) {

    // Getting the current list of books from the ViewModel
    val bookings by bookingViewModel.bookings.collectAsStateWithLifecycle()

    LazyColumn(
        // Scrolls vertically
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp) // Adding vertical spacing to in-between items
    ) {
        // Displaying each booking in the list
        items(bookings) { booking ->
            ActiveBookingsItem(booking, bookingViewModel)
        }
    }
}

// Creating book
@Composable
fun ActiveBookingsItem(joinedBooking: JoinedBookingData, bookingViewModel: BookingViewModel) {

    // Getting book information
    val book = joinedBooking.book

    // Getting booking information
    val booking = joinedBooking.booking

    // Setting date format
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Calculating days remaining
    val daysRemaining = TimeUnit.MILLISECONDS.toDays(
        booking.returnDeadline - System.currentTimeMillis()
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Badge(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(30.dp)
        ) {
            Text(text = booking.status.name)
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Column(
                // Setting book details
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(book.imageResId),
                    contentDescription = "Book cover"
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    Text(
                        text = book.title
                    )
                    Text(
                        text = book.author
                    )
                    Text(
                        text = book.category
                    )

                    if (booking.status == BookingStatus.PENDING) {
                        Text(text = "No Dates Set")
                    } else {
                        Text( // Displaying dates in correct format
                            text = "${dateFormat.format(Date(booking.bookingDate))} to ${
                                dateFormat.format(Date(booking.returnDeadline))
                            }"
                        )
                        Text(
                            text = "Days remaining: $daysRemaining"
                        )
                    }
                }

                // Showing relevant buttons depending on booking state
                if (booking.status == BookingStatus.PENDING) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Updating booking information after book is picked-up
                        Button(
                            onClick = {
                                bookingViewModel.updateActiveStatus(
                                    booking.bookingId,
                                    booking.duration,
                                    book.bookId
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text(
                                text = "Pick-Up"
                            )
                        }

                        // Cancelling booking
                        Button(
                            onClick = { bookingViewModel.cancelBooking(booking, book.bookId) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text(
                                text = "Cancel"
                            )
                        }
                    }
                } else {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        // Renewing booking
                        Button(
                            onClick = {
                                bookingViewModel.renewBooking(
                                    booking.bookingId,
                                    booking.duration,
                                    booking.returnDeadline
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text(
                                text = "Renew"
                            )
                        }

                        // Returning book
                        Button(
                            onClick = {
                                bookingViewModel.updateReturnStatus(
                                    booking.bookingId,
                                    book.bookId
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text(
                                text = "Return"
                            )
                        }
                    }
                }
            }
        }
    }
}

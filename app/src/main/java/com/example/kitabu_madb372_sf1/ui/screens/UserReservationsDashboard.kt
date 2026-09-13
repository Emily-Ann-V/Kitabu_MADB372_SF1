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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kitabu_madb372_sf1.R

@Composable
fun ReservationsScreen(
    modifier: Modifier = Modifier,
    navController: NavController // Displaying the reservations screen
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        Header("Your Reservations")
        Spacer(modifier = Modifier.height(20.dp))

        ActiveBookingsList(
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
fun ActiveBookingsList(modifier: Modifier = Modifier) {
    LazyColumn(
        // Scrolls vertically
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp) // Adding vertical spacing to in-between items
    ) {
        item {
            ActiveBookingsItem() // Showing book
        }

        item {
            ActiveBookingsItem()
        }
    }
}

// Creating book
@Composable
fun ActiveBookingsItem() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Badge(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(30.dp)
        ) {
            Text("Availability")
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
                    painter = painterResource(R.drawable.img_default),
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
                        text = "Title"
                    )
                    Text(
                        text = "Author"
                    )
                    Text(
                        text = "Category"
                    )
                    Text(
                        text = "Reservation Date - Return Deadline"
                    )
                    Text(
                        text = "Calculated Days Remaining"
                    )
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text(
                                text = "Renew"
                            )
                        }
                        Button(
                            onClick = {},
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
                    Button(
                        onClick = {},
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

            }
        }
    }
}
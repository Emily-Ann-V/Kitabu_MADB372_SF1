package com.example.kitabu_madb372_sf1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

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

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        ) {
            Search()
            Spacer(modifier = Modifier.height(20.dp))
            BookCatalogGrid()
        }

        Spacer(modifier = Modifier.height(20.dp))
        Footer("Our Catalog", "catalog", navController)
    }
}
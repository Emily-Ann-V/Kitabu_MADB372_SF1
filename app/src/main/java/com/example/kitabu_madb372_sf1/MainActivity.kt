package com.example.kitabu_madb372_sf1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kitabu_madb372_sf1.ui.theme.Kitabu_MADB372_SF1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Kitabu_MADB372_SF1Theme {

                val navController = rememberNavController()

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
                            BookCatalogScreen(navController = navController)  // Providing navigation control for this screen
                        }

                        composable("reservations") {
                            ReservationsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookCatalogScreen(
    modifier: Modifier = Modifier,
    navController: NavController // Displaying the catalog screen
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Calling composable functions
        Header("Our Catalog")
        Search()
        BookCatalogGrid(modifier.weight(1f)) // Filling remaining middle space
        Footer("Your Reservations", "reservations", navController)
    }
}

// Creating header section and setting text
@Composable
fun Header(heading: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(0.dp, 10.dp)
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = heading,
            style = MaterialTheme.typography.titleLarge
        )
    }

}

// Creating search bar and filter button
@Composable
fun Search() {
    var showFilterDialog by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            shape = RoundedCornerShape(50.dp),
            placeholder = {
                Text(
                    text = "Search",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            // leadingIcon - Puts icon on the left
            trailingIcon = { // Putting icon on the right
                Icon(
                    imageVector = Icons.Default.Search,// Needed to add Icons dependency
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        Button(
            onClick = {
                showFilterDialog = true // Showing filter dialog when button is pressed
            }
        ) {
            Text("Filter")
        }
    }
    FilterDialog(
        showFilterDialog = showFilterDialog,
        onDismiss = {
            showFilterDialog = false // Hiding filter dialog
        }
    )
}

// Creating book grid
@Composable
fun BookCatalogGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        // Scrolls vertically
        columns = GridCells.Adaptive(minSize = 130.dp), // Adaptive cell width based on screen size
        modifier = modifier
            .padding(20.dp, 50.dp), // Adding padding to the whole grid
        horizontalArrangement = Arrangement.spacedBy(20.dp), // Adding horizontal spacing to in-between items
        verticalArrangement = Arrangement.spacedBy(20.dp) // Adding vertical spacing to in-between items
        // columns = GridCells.Fixed(2) - Always have 2 columns
    ) {
        item {
            BookCatalogItem() // Showing book
        }

        // ToDo: Delete
        item {
            BookCatalogItem()
        }
    }
}

// Creating book
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCatalogItem() {
    var showReservationModal by remember { mutableStateOf(false) } // Setting up state

    val modalState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false // Allowing modal to open partially
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Badge(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(25.dp)
        ) {
            Text("Availability")
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showReservationModal = true // Showing reservation modal when card is pressed
                },
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
                        .padding(0.dp, 10.dp),
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
                }

            }
        }
    }
    ReservationModal(
        showReservationSheet = showReservationModal,
        sheetState = modalState,
        onDismiss = {
            showReservationModal = false // Hiding reservation modal
        }
    )
}

// Creating footer section and setting text
@Composable
fun Footer(
    pageLink: String,
    screen: String,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp)
            .height(40.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                navController.navigate(screen) // Navigating to the selected screen
            },
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = pageLink,
            style = MaterialTheme.typography.labelSmall
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = "Next Page",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

// Creating filter dialog
@Composable
fun FilterDialog(
    showFilterDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (showFilterDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.secondary,
            onDismissRequest = onDismiss,
            title = {
                Text("Filter Catalog")
            },
            text = {
                Column {
                    RadioButtonItem(option = "Author")
                }
            },
            confirmButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("Filter")
                }
            },
            dismissButton = {
                Button(
                    onClick = onDismiss
                ) {
                    Text("Cancel")
                }
            }
        )
    }

}

// Creating reservation modal
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationModal(
    showReservationSheet: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    if (showReservationSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.secondary,
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    "Reserve A Book",
                )
                Text(
                    "Rental Duration",
                )
                Column {
                    RadioButtonItem("1 Day")
                }

                Button(onClick = {}) {
                    Text(
                        text = "Reserve"
                    )
                }
            }
        }

    }
}

// Creating radio buttons
@Composable
fun RadioButtonItem(option: String) {
    var selectedOption by remember { mutableStateOf(false) } // Storing the selected radio button option

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedOption,
            onClick = { selectedOption = true }
        )
        Text(
            text = option,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun ReservationsScreen(
    modifier: Modifier = Modifier,
    navController: NavController // Displaying the reservations screen
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Calling composable functions
        Header("Your Reservations")
        Search()
        BookCatalogGrid(modifier.weight(1f)) // Filling remaining middle space
        Footer("Our Catalog", "catalog", navController)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookCatalogScreenPreview() {
    Kitabu_MADB372_SF1Theme {
        BookCatalogScreen(navController = rememberNavController())
    }
}
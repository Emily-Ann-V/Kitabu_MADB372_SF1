package com.example.kitabu_madb372_sf1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.kitabu_madb372_sf1.R
import com.example.kitabu_madb372_sf1.data.BookEntity
import com.example.kitabu_madb372_sf1.viewModel.BookViewModel
import com.example.kitabu_madb372_sf1.viewModel.BookingViewModel

@Composable
fun BookCatalogScreen(
    modifier: Modifier = Modifier,
    navController: NavController, // Displaying the catalog screen
    bookViewModel: BookViewModel, // Providing the ViewModel for managing book data
    bookingViewModel: BookingViewModel // Providing the ViewModel for managing booking data
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 10.dp)
    ) {
        Header("Our Catalog")
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        ) {
            Search(bookViewModel)
            Spacer(modifier = Modifier.height(20.dp))
            BookCatalogGrid(bookViewModel, bookingViewModel) // Providing the ViewModel
        }

        Spacer(modifier = Modifier.height(20.dp))
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
fun Search(bookViewModel: BookViewModel) {

    // Setting up filter dialog state
    var showFilterDialog by remember { mutableStateOf(false) }

    // Storing the search query
    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                query = it
                bookViewModel.getSearchedBooks(it) // Searching books using the query input
            },
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
        bookViewModel,
        showFilterDialog = showFilterDialog,
        onDismiss = {
            showFilterDialog = false // Hiding filter dialog
        }
    )
}

// Creating book grid
@Composable
fun BookCatalogGrid(
    bookViewModel: BookViewModel,
    bookingViewModel: BookingViewModel,
    modifier: Modifier = Modifier
) {


    // Getting the current list of books from the ViewModel
    val books by bookViewModel.books.collectAsStateWithLifecycle()

    LazyVerticalGrid(
        // Scrolls vertically
        modifier = modifier,
        columns = GridCells.Adaptive(minSize = 130.dp), // Adaptive cell width based on screen size
        horizontalArrangement = Arrangement.spacedBy(20.dp), // Adding horizontal spacing to in-between items
        verticalArrangement = Arrangement.spacedBy(20.dp) // Adding vertical spacing to in-between items
        // columns = GridCells.Fixed(2) - Always have 2 columns
    ) {

        // Displaying each book in the list
        items(books) { book ->
            BookCatalogItem(book, bookingViewModel)
        }
    }
}

// Creating book
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCatalogItem(book: BookEntity, bookingViewModel: BookingViewModel) {

    // Setting up reservation modal state
    var showReservationModal by remember { mutableStateOf(false) }

    // Setting up reservation modal sheet state
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
            Text( // Setting text based on book availability
                text = if (book.isAvailable) {
                    "Available"
                } else {
                    "Borrowed"
                }
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (book.isAvailable) {
                        showReservationModal = true
                    } // Showing reservation modal when card is pressed if book is available
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
                }

            }
        }
    }
    ReservationModal(
        book,
        bookingViewModel,
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
            modifier = Modifier.padding(horizontal = 5.dp),
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = "Next Page",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

// Creating filter dialog
@Composable
fun FilterDialog(
    bookViewModel: BookViewModel,
    showFilterDialog: Boolean,
    onDismiss: () -> Unit
) {

    // Storing the selected radio button option
    var selectedOption by remember { mutableStateOf("Any") }

    if (showFilterDialog) {
        AlertDialog(
            containerColor = MaterialTheme.colorScheme.secondary,
            onDismissRequest = onDismiss,
            title = {
                Text("Filter Catalog By Availability")
            },
            text = {
                Column {
                    RadioButtonItem(
                        "Any", // Setting filter option
                        selectedOption == "Any" // Checking if option is selected
                    ) {
                        selectedOption = "Any" // Setting selected filter
                        bookViewModel.getAllBooks() // Getting all books
                    }

                    RadioButtonItem(
                        "Available",
                        selectedOption == "Available"
                    ) {
                        selectedOption = "Available"
                        bookViewModel.getFilteredBooks(true) // Getting available books
                    }

                    RadioButtonItem(
                        "Borrowed",
                        selectedOption == "Borrowed"
                    ) {
                        selectedOption = "Borrowed"
                        bookViewModel.getFilteredBooks(false) // Getting borrowed books
                    }
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
    book: BookEntity,
    bookingViewModel: BookingViewModel,
    showReservationSheet: Boolean,
    sheetState: SheetState,
    onDismiss: () -> Unit
) {
    // Storing the selected radio button duration
    var selectedDuration by remember { mutableIntStateOf(3) }

    if (showReservationSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(),
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.secondary,
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    RadioButtonItem(
                        "3 Day", // Setting rental duration
                        selectedDuration == 3 // Checking if duration is selected
                    ) {
                        selectedDuration = 3 // Setting selected rental duration to 3 days
                    }

                    RadioButtonItem(
                        "7 Day",
                        selectedDuration == 7
                    ) {
                        selectedDuration = 7 // Setting selected rental duration to 7 days
                    }

                    RadioButtonItem(
                        "14 Day",
                        selectedDuration == 14
                    ) {
                        selectedDuration = 14 // Setting selected rental duration to 14 days
                    }
                }

                // Creating a booking when user presses reserve
                Button(onClick = { bookingViewModel.makeBooking(book, selectedDuration) }) {
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
fun RadioButtonItem(option: String, selected: Boolean, onClick: () -> Unit) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text = option,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}


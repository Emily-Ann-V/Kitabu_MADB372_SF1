package com.example.kitabu_madb372_sf1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kitabu_madb372_sf1.ui.theme.Kitabu_MADB372_SF1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Kitabu_MADB372_SF1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BookCatalogScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun BookCatalogScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        // Calling composable functions
        Header()
        Search()
        BookCatalogGrid(modifier.weight(1f)) // Filling remaining middle space
        Footer()
    }
}

// Creating header section and setting text
@Composable
fun Header() {
    Text(
        text = "Our Catalog",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Center
    )
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
            placeholder = {
                Text("Search books")
            },
            // leadingIcon - Puts icon on the left
            trailingIcon = { // Putting icon on the right
                Icon(
                    imageVector = Icons.Default.Search,// Needed to add Icons dependency
                    contentDescription = "Search"
                )
            }
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
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            Text("Availability")
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showReservationModal = true // Showing reservation modal when card is pressed
                }
        ) {
            Column( // Setting book details
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.img_default),
                    contentDescription = "Book cover"
                )

                Text("Title")
                Text("Author")
                Text("Category")
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
fun Footer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Your Reservations"
        )

        Icon(
            painter = painterResource(R.drawable.ic_arrow_forward),
            contentDescription = "Next Page"
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
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
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
        Text(option)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookCatalogScreenPreview() {
    Kitabu_MADB372_SF1Theme {
        BookCatalogScreen()
    }
}
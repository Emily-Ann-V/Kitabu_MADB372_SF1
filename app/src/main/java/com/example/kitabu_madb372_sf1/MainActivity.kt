package com.example.kitabu_madb372_sf1

import android.os.Bundle
import android.widget.RadioButton
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
    Column(modifier = Modifier.fillMaxSize()) {
        Header()
        Search()
        BookCatalogGrid(modifier.weight(1f)) // Filling remaining middle space
        Footer()
    }
}

@Composable
fun Header() {
//Header
    Text(
        text = "Our Catalog",
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Center
    )
}

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
            // leadingIcon - Puts icon on the right
            trailingIcon = { // Putting icon on the left
                Icon(
                    imageVector = Icons.Default.Search,// Need to add dependency
                    contentDescription = "Search"
                )
            }
        )

        Button(
            onClick = {
                showFilterDialog = true
            }
        ) {
            Text("Filter")
        }
    }
    FilterDialog(
        showFilterDialog = showFilterDialog,
        onDismiss = {
            showFilterDialog = false
        }
    )
}

@Composable
fun BookCatalogGrid(modifier: Modifier = Modifier) {

    //Books
    LazyVerticalGrid(
        // Scrolls vertically
        columns = GridCells.Adaptive(minSize = 130.dp), // Adaptive cell width based on screen size
        modifier = modifier
            .padding(20.dp, 50.dp), // Adding padding to the whole grid
        horizontalArrangement = Arrangement.spacedBy(20.dp), // Adding horizontal padding to in-between items
        verticalArrangement = Arrangement.spacedBy(20.dp) // Adding vertical padding to in-between items
        // columns = GridCells.Fixed(2) - Always have 2 columns
    ) {
        item {
            BookCatalogItem()
        }

        // ToDo: Delete
        item {
            BookCatalogItem()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookCatalogItem() {

    var showReservationSheet by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
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
                    showReservationSheet = true
                }
        ) {
            Column(
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
        showReservationSheet = showReservationSheet,
        sheetState = sheetState,
        onDismiss = {
            showReservationSheet = false
        }
    )
}

@Composable
fun Footer() {
    // Footer
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
                    RadioButtonItem("Author")
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
                Column() {
                    RadioButtonItem("Day 1")
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

@Composable
fun RadioButtonItem(option: String) {
    var selectedOption by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selectedOption == option,
            onClick = { selectedOption = option }
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
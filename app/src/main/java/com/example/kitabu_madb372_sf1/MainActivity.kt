package com.example.kitabu_madb372_sf1

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {


    Column(modifier = Modifier.fillMaxSize()) {
        //Header
        Text(
            text = "Our Catalog",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center
        )

        // Search
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
                onClick = { }
            ) {
                Text("Filter")
            }
        }

        //Books
        LazyVerticalGrid(
            // Scrolls vertically
            columns = GridCells.Adaptive(minSize = 130.dp), // Adaptive cell width based on screen size
            modifier = Modifier
                .weight(1f) // Filling remaining middle space
                .padding(20.dp, 50.dp), // Adding padding to the whole grid
            horizontalArrangement = Arrangement.spacedBy(20.dp), // Adding horizontal padding to in-between items
            verticalArrangement = Arrangement.spacedBy(20.dp) // Adding vertical padding to in-between items
            // columns = GridCells.Fixed(2) - Always have 2 columns
        ) {
// title, author, category, and availability status
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) { // Horizontally centering elements in column
                    Badge(
                        modifier = Modifier
                            .fillMaxWidth(0.5f) // Making the badge half the column's width
                    ) {
                        Text(
                            text = "Availability"
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth() // Making the card the full column's width
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(), // Making the column the full card's width
                            horizontalAlignment = Alignment.CenterHorizontally // Horizontally centering the text
                        ) {
                            // Cover
                            Image(
                                painter = painterResource(R.drawable.img_default),
                                contentDescription = "Book cover"
                            )

                            // Details
                            Text(text = "Title")
                            Text(text = "Author")
                            Text(text = "Category")
                        }
                    }
                }
            }

            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Badge(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                    ) {
                        Text(
                            text = "Availability"
                        )
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Cover
                            Image(
                                painter = painterResource(R.drawable.img_default),
                                contentDescription = "Book cover"
                            )

                            // Details
                            Text(text = "Title")
                            Text(text = "Author")
                            Text(text = "Category")
                        }
                    }
                }
            }
        }

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
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    Kitabu_MADB372_SF1Theme {
        Greeting("Android")
    }
}
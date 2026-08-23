package com.example.kitabu_madb372_sf1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                }
            )

            Button(
                onClick = { }
            ) {
                Text("Filter")
            }
        }


        LazyVerticalGrid(
            // Scrolls vertically
            columns = GridCells.Adaptive(minSize = 130.dp), // Adaptive cell width based on screen size
            modifier = Modifier
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
                            //TODO: Image
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
                            Text(text = "Title")
                            Text(text = "Author")
                            Text(text = "Category")
                        }
                    }
                }
            }
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
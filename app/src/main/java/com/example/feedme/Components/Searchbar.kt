package com.example.feedme.Components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.ui.theme.InputShape

@Preview
@Composable
fun SearchBar() {
    val query = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp).height(50.dp),
                value = query.value,
                onValueChange = { query.value = it },
                placeholder = { Text(text = "Recettes ...") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Recherche",
                        modifier = Modifier.size(33.dp),
                        tint = Color.Black
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { query.value = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear", tint = Color.Black, modifier = Modifier.size(20.dp))
                    }
                },
                shape = InputShape.large,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,

                    ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                textStyle = TextStyle.Default.copy(fontSize = 15.sp, fontWeight = FontWeight.Bold)

            )
        }
    }

//        LazyColumn {
//            itemsIndexed(
//                items = recipes
//            ) { index, recipe ->
//                RecipeCard(recipe = recipe, onClick = {})
//            }
//        }

}
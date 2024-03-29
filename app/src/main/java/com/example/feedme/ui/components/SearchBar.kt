package com.example.feedme.ui.components


import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.ui.theme.InputShape

@ExperimentalComposeUiApi
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .height(50.dp),
                value = query,
                onValueChange = { onQueryChange(it) },
                placeholder = { Text(text = "Recettes ...", color = Color.Black) },
                singleLine = true,
                leadingIcon = {
                    IconButton(onClick = {
                        keyboardController?.hide()
                        onSearch()
                    }) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Recherche",
                            modifier = Modifier.size(33.dp),
                            tint = Color.Black
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        onQueryChange("")
                        keyboardController?.hide()
                        onSearch()
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                shape = InputShape.large,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    textColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSearch()
                        keyboardController?.hideSoftwareKeyboard() // another way to close keyboard
                    },
                ),
                textStyle = TextStyle.Default.copy(fontSize = 15.sp, fontWeight = FontWeight.Bold)

            )
        }
    }
}

fun hideKeyboard(context: Context, windowToken: IBinder) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}
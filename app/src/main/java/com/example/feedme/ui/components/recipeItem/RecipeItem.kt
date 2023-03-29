package com.example.feedme.ui.components.recipeItem

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.feedme.domain.RecipeWithFavorite
import com.example.feedme.ui.components.LoadImageFromUrl
import com.example.feedme.ui.components.viewModel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

fun convertIngredientStringToList(ingredientString: String): List<String> {
    return ingredientString.split(",")
}



@SuppressLint("SuspiciousIndentation")
@Composable
fun RecipeDetails(
    recipeId: Int?,
    onBack: () -> Unit
) {
    val myViewModel: HomeViewModel = hiltViewModel()
    val visibleState = remember {
        MutableTransitionState(false).apply {

            targetState = false // start the animation immediately
        }
    }


    LaunchedEffect(Unit) {

        visibleState.targetState = true // Modifier la visibilité pour déclencher l'animation

        //when{visibleState.isIdle && !visibleState.currentState -> onBack()}

    }

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = visibleState.targetState)
    {
        if (!visibleState.targetState)
        coroutineScope.launch(Dispatchers.Main) {
            delay(200)
            onBack()
        }
    }


    println("recipeID  : $recipeId")
    val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val ingredientString = "pomme,myonnaise,boeuf,poisson"
    val itemsListString = convertIngredientStringToList(ingredientString)
    val recipe = RecipeWithFavorite(
        id = 0,
        title = "recette test",
        publisher = "anonyme",
        featuredImage = "https://nyc3.digitaloceanspaces.com/food2fork/food2fork-static/featured_images/573/featured_image.png",
        rating = 50,
        sourceUrl = "",
        ingredients = "pomme, myonnaise, boeuf, poisson",
        dateAdded = Date(1606348867),
        dateUpdated = Date(1606348867),
        favorite = false
    )



//Animation  !
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        ) + slideInHorizontally(
            initialOffsetX = { it },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        ) + slideOutHorizontally(
            targetOffsetX = { -it },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing
            )
        )
    )  {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.TopStart
        ) {
            FloatingActionButton(
                onClick = { onBack() },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
            }
        }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            LoadImageFromUrl(LocalContext.current, recipe.featuredImage)
                            IconButton(
                                onClick = {
                                    visibleState.targetState = !visibleState.targetState },
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(16.dp)
                                    .background(Color.White, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Retour",
                                    tint = Color.Black
                                )
                            }
                        }
                        Text(
                            text = recipe.title + "-" + recipe.publisher,
                            modifier = Modifier.padding(top = 5.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = sdf.format(recipe.dateUpdated),
                            color = Color(0xFFAAAAAA),
                            modifier = Modifier.padding(top = 5.dp),
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                    itemsIndexed(items = itemsListString) { index, recipe ->
                        Row() {
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = recipe,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
        }
    }






//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            item {
//                Box(modifier = Modifier.fillMaxWidth()) {
//                    LoadImageFromUrl(LocalContext.current, recipe.featuredImage)
//                }
//                Text(
//                    text = recipe.title + "-" + recipe.publisher,
//                    modifier = Modifier.padding(top = 5.dp),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp
//                )
//                Text(
//                    text = sdf.format(recipe.dateUpdated),
//                    color = Color(0xFFAAAAAA),
//                    modifier = Modifier.padding(top = 5.dp),
//                    fontWeight = FontWeight.Normal,
//                    fontSize = 14.sp
//                )
//            }
//            itemsIndexed(items = itemsListString) { index, recipe ->
//                Row() {
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(
//                        text = recipe,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.ExtraBold
//                    )
//                    Spacer(modifier = Modifier.width(4.dp))
//                }
//            }


            /* Surface(
                 modifier = Modifier.size(26.dp, 23.dp),
                 shape = CircleShape,
                 color = Color(0xFFEEEEEE)
             ){
                 Text(
                     color = Color.Black,
                     text = ConvertRating(recipe.rating).toString(),
                     fontWeight = FontWeight.Bold,
                     fontSize = 12.sp,
                     modifier = Modifier
                         .padding(4.dp)
                         .weight(1f)
                 )
             }
             Row() {
                 Text(
                     text = "Liste d'ingrédient :",
                     fontSize = 18.sp,
                     fontWeight = FontWeight.ExtraBold
                 )
             }
             Row() {
                 Text(
                     text = ingredientString,
                     color = Color.DarkGray,
                     fontWeight = FontWeight.Normal,
                     fontSize = 16.sp
                 )
             }*/
       // }
    //}
}

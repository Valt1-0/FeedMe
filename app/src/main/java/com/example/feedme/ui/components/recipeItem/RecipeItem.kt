package com.example.feedme.ui.components.recipeItem

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.ConvertRating
import com.example.feedme.ui.components.LoadImageFromUrl
import com.example.feedme.ui.components.favorite.EventTrigger
import java.text.SimpleDateFormat
import java.util.*

fun convertIngredientStringToList(ingredientString: String): List<String> {
   var resultIngredient = ingredientString
    if (resultIngredient.endsWith(",")) {
        resultIngredient.substring(0, resultIngredient.length - 1)
    }

    return resultIngredient.split(",")
}



@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun RecipeDetails(
    recipeId: Int?,
    onBack: () -> Unit,
    viewModel: RecipeDetailsViewModel
) {
    if (recipeId == null){
        TODO("Show Invalid Recipe")
    }else {
        val onLoad = viewModel.onLoad.value
        println("On Load : "+onLoad)
        LaunchedEffect(Unit)
        {
            if (!onLoad) {
                viewModel.isLoading.value = true
                println("recipeID  : $recipeId")
                viewModel.onTriggerEvent(EventTrigger.GetRecipeEvent(recipeId))
            }
        }


        val visibleStateAnimation = remember {
            MutableTransitionState(false).apply {

                targetState = false // start the animation immediately
            }
        }

        val recipe = viewModel.recipe.value
        val loading = viewModel.isLoading.value
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val itemsListString = recipe?.let {convertIngredientStringToList(it.ingredients) }

        if (!loading && recipe?.id != 0 ) {

            //  LaunchedEffect(Unit) {

            visibleStateAnimation.targetState =
                true // Modifier la visibilité pour déclencher l'animation
            // }


////Animation  !
//    AnimatedVisibility(
//        visibleState = visibleStateAnimation,
//        enter = fadeIn(
//            animationSpec = tween(
//                durationMillis = 500,
//                easing = LinearEasing
//            )
//        ) + slideInHorizontally(
//            initialOffsetX = { -it },
//            animationSpec = tween(
//                durationMillis = 500,
//                easing = LinearEasing
//            )
//        ),
//        exit = fadeOut(
//            animationSpec = tween(
//                durationMillis = 500,
//                easing = LinearOutSlowInEasing
//            )
//        ) + slideOutHorizontally(
//            targetOffsetX = { -it },
//            animationSpec = tween(
//                durationMillis = 500,
//                easing = LinearOutSlowInEasing
//            )
//        )
//    ) {

//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.TopStart
//            ) {
//                FloatingActionButton(
//                    onClick = { onBack() },
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Icon(Icons.Default.ArrowBack, contentDescription = "Retour")
//                }
//            }
            Column(Modifier.fillMaxSize().background(Color.White)) {


                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .background(Color.White)
                        .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    content = {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            Column(Modifier.fillMaxSize()) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    if (recipe != null) {
                                        LoadImageFromUrl(LocalContext.current, recipe.featuredImage)
                                    }
                                    IconButton(
                                        onClick = {
                                            onBack()
                                        },
                                        modifier = Modifier
                                            .align(Alignment.TopStart)
                                            .padding(16.dp)
                                            .background(Color.White, CircleShape)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Retour",
                                            tint = Color.Black
                                        )
                                    }
                                }
                                Row( modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically) {
                                    if (recipe != null) {
                                        Text(
                                            text = recipe.title + "-" + recipe.publisher,
                                            modifier = Modifier.padding(top = 5.dp),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Surface(
                                            modifier = Modifier.size(26.dp, 23.dp),
                                            shape = CircleShape,
                                            color = Color(0xFFEEEEEE)
                                        ) {
                                            Text(
                                                color = Color.Black,
                                                text = ConvertRating(recipe.rating).toString(),
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 12.sp,
                                                modifier = Modifier
                                                    .padding(4.dp)
                                                    .weight(1f)
                                            )
                                        }
                                    }
                                }
                                Row(Modifier.fillMaxWidth()) {
                                    if (recipe != null) {
                                        Text(
                                            text = "Mis à jour : "+sdf.format(recipe.dateUpdated),
                                            color = Color(0xFFAAAAAA),
                                            modifier = Modifier.padding(top = 5.dp),
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 14.sp
                                        )
                                    }
                                }
                            }
                        }

                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            Text(
                                text = "Liste des ingredients : ",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier
                                    .padding(vertical = 16.dp)
                                    .fillMaxWidth()
                            )
                        }
                        for (ingredient in itemsListString!!) {
                            var instructions = ""
                            val title = ingredient.startsWith("For ")
                            val quantity =
                                ingredient.takeWhile { it.isDigit() || it == '-' || it == '/' }
                            val name = ingredient.substringAfter(quantity).trim()
                            if (quantity.isNotEmpty() || name.isNotEmpty()) {
                                if (title) {
                                    instructions = name
                                    item(span = {
                                        GridItemSpan(maxLineSpan)
                                    }) {
                                        Text(
                                            text = instructions,
                                            style = MaterialTheme.typography.h6,
                                            modifier = Modifier
                                                .padding(vertical = 16.dp)
                                                .fillMaxWidth()
                                        )
                                    }

                                } else {
                                    item(span = {
                                        GridItemSpan(1)
                                    }) {
                                        Card(
                                            modifier = Modifier
                                                .padding(end = 16.dp)
                                                .size(130.dp),
                                            elevation = 8.dp
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier.padding(8.dp)
                                            ) {
                                                Text(
                                                    text = name,
                                                    style = MaterialTheme.typography.caption,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                if (quantity.isNotEmpty()) {
                                                    Text(
                                                        text = quantity,
                                                        style = MaterialTheme.typography.subtitle1,
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier.weight(0.3f)
                                                    )
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                )
            }
        }


//        LazyVerticalGrid(
//                modifier = Modifier.fillMaxWidth(),
//                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//                        columns = GridCells.Fixed(2),
//            ) {
//                var instructions = ""
//                itemsIndexed(itemsListString!!) { index, ingredient ->
//                    val title = ingredient.startsWith("For ")
//                    val quantity = ingredient.takeWhile { it.isDigit() || it == '-' || it == '/' }
//                    val name = ingredient.substringAfter(quantity).trim()
//                    if (title) {
//                        instructions = name
//                        Text(
//                            text = instructions,
//                            style = MaterialTheme.typography.h6,
//                            modifier = Modifier
//                                .padding(vertical = 16.dp)
//                                .fillMaxWidth()
//                        )
//                    } else {
//                        Card(
//                            modifier = Modifier
//                                .padding(end = 16.dp)
//                                .size(120.dp),
//                            elevation = 8.dp
//                        ) {
//                            Column(
//                                horizontalAlignment = Alignment.CenterHorizontally,
//                                modifier = Modifier.padding(8.dp)
//                            ) {
//                                Text(
//                                    text = name,
//                                    style = MaterialTheme.typography.subtitle1,
//                                    textAlign = TextAlign.Center,
//                                    modifier = Modifier.weight(1f)
//                                )
//                                if (quantity.isNotEmpty()) {
//                                    Text(
//                                        text = quantity,
//                                        style = MaterialTheme.typography.caption,
//                                        textAlign = TextAlign.Center,
//                                        modifier = Modifier.weight(1f)
//                                    )
//                                }
//                            }
//                        }
//                        if (index < itemsListString!!.size - 1) {
//                            Spacer(modifier = Modifier.width(8.dp))
//                        }
//                    }
//                }
//            }







//            Column(modifier = Modifier.padding(16.dp)) {
//                for (item in itemsListString!!) {
//                    val isInstruction = item.startsWith("For ")
//                    Card(
//                        modifier = Modifier.fillMaxWidth().padding(8.dp),
//                        elevation = 8.dp
//                    ) {
//                        Column(modifier = Modifier.padding(8.dp)) {
//                            Text(
//                                text = item,
//                                style = if (isInstruction) MaterialTheme.typography.h6 else MaterialTheme.typography.body1,
//                                fontWeight = if (isInstruction) FontWeight.Bold else FontWeight.Normal,
//                                textAlign = TextAlign.Center
//                            )
//                        }
//                    }
//                }
//            }







//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    item {
//                        Box(modifier = Modifier.fillMaxWidth()) {
//                            if (recipe != null) {
//                                LoadImageFromUrl(LocalContext.current, recipe.featuredImage)
//                            }
//                            IconButton(
//                                onClick = {
//                                    onBack()
//                                },
//                                modifier = Modifier
//                                    .align(Alignment.TopStart)
//                                    .padding(16.dp)
//                                    .background(Color.White, CircleShape)
//                            ) {
//                                Icon(
//                                    imageVector = Icons.Default.Close,
//                                    contentDescription = "Retour",
//                                    tint = Color.Black
//                                )
//                            }
//                        }
//                        Text(
//                            text = (recipe?.title ?: "") + "-" + (recipe?.publisher ?: ""),
//                            modifier = Modifier.padding(top = 5.dp),
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 20.sp
//                        )
//                        Text(
//                            text = "",
//                            color = Color(0xFFAAAAAA),
//                            modifier = Modifier.padding(top = 5.dp),
//                            fontWeight = FontWeight.Normal,
//                            fontSize = 14.sp
//                        )
//                    }
//                    itemsIndexed(items = itemsListString!!) { index, recipe ->
//                        Row() {
//                            Spacer(modifier = Modifier.width(4.dp))
//                            Text(
//                                text = recipe,
//                                fontSize = 18.sp,
//                                fontWeight = FontWeight.ExtraBold
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                        }
//                    }
//                }
//            }






//        if (this.transition.currentState == this.transition.targetState){
//            println("End of anim ")
//        }

//        val coroutineScope = rememberCoroutineScope()
//        LaunchedEffect(key1 = visibleStateAnimation.targetState)
//        {
//
//                coroutineScope.launch(Dispatchers.Main) {
//                    withFrameNanos {
//                        onBack()
//                    }
//                    //delay(250)
//
//                }
//        }

       // }

    }

   // }
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


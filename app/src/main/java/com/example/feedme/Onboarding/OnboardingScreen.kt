package com.example.feedme.Onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feedme.R
import com.example.feedme.ui.theme.BottomCardShape
import com.example.feedme.ui.theme.Poppins
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@Composable
fun OnboardingScreen(
    onboardingItems: List<OnboardingItem>,
    onboardingCompleteAction: () -> Unit,
) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(pageCount = onboardingItems.size, state = pagerState) { page ->
                Column(
                    modifier = Modifier
                        .background(onboardingItems[page].backgroundColor)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Image(
                        painter = painterResource(id = onboardingItems[page].image),
                        contentDescription = onboardingItems[page].title,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                backgroundColor = colors.secondary,
                elevation = 0.dp,
                shape = BottomCardShape.large
            ) {
                Box() {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PagerIndicator(
                            items = onboardingItems,
                            currentPage = pagerState.currentPage,
                        )
                        Text(
                            text = onboardingItems[pagerState.currentPage].title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, end = 30.dp),
                            color = onboardingItems[pagerState.currentPage].mainColor,
                            fontFamily = Poppins,
                            textAlign = TextAlign.Right,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = onboardingItems[pagerState.currentPage].desc,
                            modifier = Modifier.padding(top = 20.dp, start = 40.dp, end = 20.dp),
                            color = colors.onPrimary,
                            fontFamily = Poppins,
                            textAlign = TextAlign.Center,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.ExtraLight
                        )
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(30.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {


                            if (pagerState.currentPage != 2) {
                                // Skip Button
                                TextButton(onClick = {
                                    coroutineScope.launch { pagerState.scrollToPage(pagerState.currentPage + 2) }
                                }) {
                                    Text(
                                        text = "Passer",
                                        color = Color.Gray,
                                        fontFamily = Poppins,
                                        textAlign = TextAlign.Right,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                // Bouton "Suivant" => Flèche
                                OutlinedButton(
                                    onClick = {
                                        coroutineScope.launch { pagerState.scrollToPage(pagerState.currentPage + 1) }
                                    },

                                    border = BorderStroke(
                                        14.dp,
                                        onboardingItems[pagerState.currentPage].mainColor
                                    ),
                                    shape = RoundedCornerShape(50), // = 50% percent
                                    //or shape = CircleShape
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        contentColor = colors.secondary,
                                        backgroundColor = colors.secondary
                                    ),
                                    modifier = Modifier.size(65.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right_arrow),
                                        contentDescription = "",
                                        tint = onboardingItems[pagerState.currentPage].mainColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                // Bouton Continue => MainActivity
                                Button(
                                    onClick = onboardingCompleteAction,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = onboardingItems[pagerState.currentPage].mainColor
                                    ),
                                    contentPadding = PaddingValues(vertical = 12.dp),
                                    elevation = ButtonDefaults.elevation(
                                        defaultElevation = 0.dp
                                    )
                                ) {
                                    Text(
                                        text = "Commencer",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PagerIndicator(currentPage: Int, items: List<OnboardingItem>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        repeat(items.size) {
            Indicator(isSelected = it == currentPage, color = items[it].mainColor)
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean, color: Color) {
    val width = animateDpAsState(targetValue = if (isSelected) 40.dp else 10.dp)

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) color else Color.Gray.copy(alpha = 0.5f)
            )
    )
}
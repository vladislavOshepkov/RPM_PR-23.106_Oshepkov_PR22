package com.example.prac22

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.prac22.ui.theme.Prac22Theme

class MainActivity : ComponentActivity() {
    private val viewModel: EmojiViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadEmojis()

        setContent {
            Prac22Theme() {
                MainContent(viewModel)
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainContent(viewModel: EmojiViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(onClick = { viewModel.loadEmojis() }) {
                        Icon(
                            Icons.Filled.Refresh,
                            contentDescription = "Reload Game"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val cards: List<AndroidModel> by viewModel.getEmojis().observeAsState(listOf())
        CardsGrid(cards = cards, viewModel = viewModel, paddingValues = paddingValues)
    }
}

@Composable
private fun CardsGrid(cards: List<AndroidModel>, viewModel: EmojiViewModel, paddingValues: PaddingValues) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.padding(top = paddingValues.calculateTopPadding() + 16.dp) // Добавляем отступ сверху
    ) {
        items(cards.count()) { cardIndex ->
            CardItem(cards[cardIndex], viewModel)
        }
    }
}

@Composable
private fun CardItem(emoji: AndroidModel, viewModel: EmojiViewModel) {
    Box(
        modifier = Modifier
            .padding(all = 10.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .size(150.dp)
                .background(
                    color = Color.Black.copy(alpha = if (emoji.isVisible) 0.4F else 0.0F),
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    if (emoji.isVisible) {
                        viewModel.updateShowVisibleCard(emoji.id)
                    }
                }
        ) {
            if (emoji.isSelect) {
                Text(
                    text = emoji.char,
                    fontSize = 32.sp
                )
            }
        }
    }
}

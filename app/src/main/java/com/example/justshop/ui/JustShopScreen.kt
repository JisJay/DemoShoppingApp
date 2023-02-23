package com.example.justshop.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.justshop.R
import com.example.justshop.data.justShopItemList
import com.example.justshop.model.JustShopItem
import com.example.justshop.ui.theme.JustShopTheme

/*
 * The main screen for the App.
 */
@Composable
fun JustShopScreen(
    modifier: Modifier = Modifier, justShopViewModel: JustShopViewModel = viewModel()
) {
    val justShopUiState = justShopViewModel.uiState.collectAsState()
    Scaffold(topBar = { JustShopTopBar() }) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(contentPadding)
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(justShopItemList) {
                JustShopItemView(
                    justShopItem = it,
                    justShopViewModel = justShopViewModel,
                    isFavourite = justShopUiState.value.favourites?.contains(it.id) ?: false
                )
            }
        }
    }
}


/*
 * The Top Bar View.
 */
@Composable
fun JustShopTopBar(modifier: Modifier = Modifier) {
    Text(
        stringResource(id = R.string.title),
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .padding(top = 8.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h2
    )
}

/*
 * Defining each item in the list.
 */
@Composable
fun JustShopItemView(
    modifier: Modifier = Modifier,
    justShopViewModel: JustShopViewModel,
    justShopItem: JustShopItem,
    isFavourite: Boolean = false
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        ) {
            Row(
                modifier = Modifier.padding(8.dp), horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painterResource(id = justShopItem.imageId),
                    contentDescription = justShopItem.title,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .fillMaxSize()
                )
                DetailsSnapShot(justShopViewModel = justShopViewModel, justShopItem, isFavourite)
            }
            if (expanded) {
                Details(justShopItem)
            }
        }
    }
}

/*
 * The basic details which are to be displayed in the list view item
 */
@Composable
fun DetailsSnapShot(
    justShopViewModel: JustShopViewModel, justShopItem: JustShopItem, isFavourite: Boolean = false
) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp)
            .height(100.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = justShopItem.title,
            style = MaterialTheme.typography.h1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Text(
                text = stringResource(id = R.string.price, justShopItem.price),
                modifier = Modifier,
                style = MaterialTheme.typography.caption
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Favorite,
                modifier = Modifier.clickable {
                    if (isFavourite) {
                        justShopViewModel.removeFromFavorites(justShopItem.id)
                    } else {
                        justShopViewModel.addToFavorites(justShopItem.id)

                    }
                },
                contentDescription = stringResource(R.string.favourite),
                tint = if (isFavourite) MaterialTheme.colors.secondary else Color.Gray
            )
        }
    }

}


/*
 * The Details which needs to be shown when touched on the item
 */
@Composable
fun Details(justShopItem: JustShopItem) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = justShopItem.description,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .wrapContentHeight(),
            style = MaterialTheme.typography.body1
        )
        Text(
            text = stringResource(id = R.string.rating, justShopItem.rating.rate),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            text = stringResource(id = R.string.rating_count, justShopItem.rating.count),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            text = stringResource(id = R.string.category, justShopItem.category),
            modifier = Modifier.padding(bottom = 8.dp),
            style = MaterialTheme.typography.body2
        )
    }

}


/*
 * Preview function
 */

@Preview(showBackground = true)
@Composable
fun Preview() {
    JustShopTheme {
        JustShopScreen()
    }
}
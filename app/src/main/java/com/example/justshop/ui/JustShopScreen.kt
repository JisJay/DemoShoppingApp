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
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.justshop.R
import com.example.justshop.model.JustShopItem


/**
 * The main parent surface which holds all the UI elements
 */

@Composable
fun JustShopScreen(modifier: Modifier = Modifier) {
    val justShopViewModel: JustShopViewModel = viewModel(factory = JustShopViewModel.Factory)
    val uiState by justShopViewModel.uiState.collectAsState()
    Scaffold(topBar = {
        JustShopTopBar(isFavouriteScreen = uiState.isFavouriteScreen, onFavouriteClicked = {
            justShopViewModel.loadFavourites()
        }, onFavouriteUnClicked = {
            justShopViewModel.showShopItems()
        })
    }) { contentPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(justShopViewModel)
        }
    }
}

@Composable
fun HomeScreen(
    justShopViewModel: JustShopViewModel, modifier: Modifier = Modifier
) {
    val uiState by justShopViewModel.uiState.collectAsState()
    if (uiState.isLoading) {
        LoadingScreen(modifier)
    } else if (uiState.isError) {
        ErrorScreen(modifier) { justShopViewModel.loadItems() }
    } else if (uiState.isFavouriteScreen) {
        JustShopItemListScreen(
            uiState.favourites, justShopViewModel, modifier
        )
    } else {
        JustShopItemListScreen(
            uiState.justShopItemsList, justShopViewModel, modifier
        )
    }
}


/**
 * The home screen displaying the list of items
 */
@Composable
fun JustShopItemListScreen(
    justShopItemList: List<JustShopItem>,
    justShopViewModel: JustShopViewModel,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(justShopItemList) { item ->
            JustShopItemView(justShopItem = item,
                isFavourite = justShopViewModel.isFavourite(item.id),
                onFavouritePressed = { item: JustShopItem ->
                    justShopViewModel.updateFavorites(item)
                })

        }
    }
}

/**
 * The home screen displaying error message
 */
@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier, retryAction: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

/**
 * Displaying the Topbar
 */
@Composable
fun JustShopTopBar(
    modifier: Modifier = Modifier,
    isFavouriteScreen: Boolean = false,
    onFavouriteClicked: () -> Unit,
    onFavouriteUnClicked: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            stringResource(id = if (isFavouriteScreen) R.string.favourite else R.string.title),
            modifier = Modifier
                .wrapContentWidth()
                .height(72.dp)
                .padding(top = 8.dp, start = 8.dp),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.h2
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            modifier = Modifier
                .padding(8.dp)
                .height(35.dp)
                .width(35.dp)
                .clickable {
                    if (isFavouriteScreen) {
                        //show favourite items
                        onFavouriteUnClicked()
                    } else {
                        //show just shop items
                        onFavouriteClicked()
                    }
                },
            contentDescription = stringResource(R.string.my_favourites),
            tint = if (isFavouriteScreen) Color.Gray else Color.Blue
        )
    }
}

/**
 * UI for each Item
 */
@Composable
fun JustShopItemView(
    modifier: Modifier = Modifier,
    onFavouritePressed: (JustShopItem) -> Unit,
    justShopItem: JustShopItem,
    isFavourite: Boolean = false,
) {
    var expanded by remember { mutableStateOf(false) }
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
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(justShopItem.image).build(),
                    contentDescription = justShopItem.title,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds,
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img)
                )
                DetailsSnapShot(justShopItem, isFavourite, onFavouritePressed = onFavouritePressed)
            }
            if (expanded) {
                Details(justShopItem)
            }
        }
    }
}

/**
 * UI to show the title, price & fav icon
 */
@Composable
fun DetailsSnapShot(
    justShopItem: JustShopItem,
    isFavourite: Boolean = false,
    onFavouritePressed: (JustShopItem) -> Unit
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
                modifier = Modifier.clickable(onClick = {
                    onFavouritePressed(justShopItem)
                }),

                contentDescription = stringResource(R.string.favourite),
                tint = if (isFavourite) MaterialTheme.colors.secondary else Color.Gray
            )
        }
    }

}

/**
 * UI to show the description and ratings
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
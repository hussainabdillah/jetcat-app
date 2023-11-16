package com.dicoding.jetcatsapp.ui

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.dicoding.jetcatsapp.R
import com.dicoding.jetcatsapp.data.CatsRepository
import com.dicoding.jetcatsapp.model.CatsData
import com.dicoding.jetcatsapp.ui.theme.JetCatsAppTheme


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("NotConstructor")
@Composable
fun JetCatsApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: JetCatsViewModel = viewModel(factory = ViewModelFactory(CatsRepository()))
) {
    val groupedCats by viewModel.groupedCats.collectAsState()
    val query by viewModel.query

    Box(modifier = modifier) {

        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
//            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                )
            }
            val filteredCats = CatsData.cats.filter {
                it.name.contains(query, ignoreCase = true) || it.breeds.contains(
                    query,
                    ignoreCase = true
                )
            }
            when {
                filteredCats.isNotEmpty() -> {
                    items(filteredCats, key = { it.id }) { cat ->
                        CatListItem(
                            name = cat.name,
                            breeds = cat.breeds,
                            photoUrl = cat.photoUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(tween(durationMillis = 100))
                        )
                    }
                }
                else -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.not_found_icon),
                                contentDescription = "Not Found",
                                modifier = Modifier
                                    .size(300.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CatListItem(
    name: String,
    breeds: String,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {}
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(CircleShape)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = breeds,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_cat))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}

//@Preview(showBackground = true)
//@Composable
//fun JetHeroesAppPreview() {
//    JetCatsAppTheme {
//        JetCatsApp()
//    }
//}
//
@Preview(showBackground = true)
@Composable
fun CatsListItemPreview() {
    JetCatsAppTheme {
        CatListItem(
            name = "Persian",
            breeds = "Western Breeds",
            photoUrl = ""
        )
    }
}
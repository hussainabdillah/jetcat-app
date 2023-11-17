package com.dicoding.jetcatsapp.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dicoding.jetcatsapp.R
import com.dicoding.jetcatsapp.data.CatsRepository
import com.dicoding.jetcatsapp.model.CatsData
import com.dicoding.jetcatsapp.ui.item.CatListItem
import com.dicoding.jetcatsapp.ui.item.SearchBar
import com.dicoding.jetcatsapp.ui.viewmodel.JetCatsViewModel
import com.dicoding.jetcatsapp.ui.viewmodel.ViewModelFactory
import androidx.compose.foundation.Image
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val viewModel: JetCatsViewModel = viewModel(factory = ViewModelFactory(CatsRepository()))
    val groupedCats by viewModel.groupedCats.collectAsState()
    val query by viewModel.query

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
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

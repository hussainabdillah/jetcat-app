package com.dicoding.jetcatsapp.ui

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.dicoding.jetcatsapp.R
import com.dicoding.jetcatsapp.data.CatsRepository
import com.dicoding.jetcatsapp.model.CatsData
import com.dicoding.jetcatsapp.navigation.Screen
import com.dicoding.jetcatsapp.ui.item.BottomBar
import com.dicoding.jetcatsapp.ui.item.SearchBar
import com.dicoding.jetcatsapp.ui.item.CatListItem
import com.dicoding.jetcatsapp.ui.viewmodel.JetCatsViewModel
import com.dicoding.jetcatsapp.ui.viewmodel.ViewModelFactory

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JetCatsApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val viewModel: JetCatsViewModel = viewModel(factory = ViewModelFactory(CatsRepository()))
    val query by viewModel.query
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchBar(
                query = query,
                onQueryChange = viewModel::search,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .zIndex(1f)
            )
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(top = 90.dp)
            ) {
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
                                    .animateItemPlacement(tween(durationMillis = 100)),
                                onClick = {
                                    navController.navigate(Screen.Detail.route + "${cat.id}")
                                }
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
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
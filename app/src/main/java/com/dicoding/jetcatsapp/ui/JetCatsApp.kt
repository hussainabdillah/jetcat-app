package com.dicoding.jetcatsapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.jetcatsapp.R
import com.dicoding.jetcatsapp.data.CatsRepository
import com.dicoding.jetcatsapp.model.CatsData
import com.dicoding.jetcatsapp.ui.theme.JetCatsAppTheme


@SuppressLint("NotConstructor")
@Composable
fun JetCatsApp(
    modifier: Modifier = Modifier,
    viewModel: JetCatsViewModel = viewModel(factory = ViewModelFactory(CatsRepository()))
) {
    Box(modifier = modifier) {
        LazyColumn {
            items(CatsData.cats, key = { it.id }) { cat ->
                CatListItem(
                    name = cat.name,
                    photoUrl = cat.photoUrl,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun CatListItem(
    name: String,
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
        Text(
            text = name,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetCatsAppTheme {
        JetCatsApp()
    }
}

@Preview(showBackground = true)
@Composable
fun CatsListItemPreview() {
    JetCatsAppTheme {
        CatListItem(
            name = "H.O.S. Cokroaminoto",
            photoUrl = "https://basepaws.com/_next/image?url=https%3A%2F%2Fimages.ctfassets.net%2F7hqiona4456t%2F26gvR2TSfqEKHVldbY2aMZ%2Fbbf98ad647ece39d8f7600c37a0db32f%2Fpersian_cat__1_.jpg&w=640&q=75"
        )
    }
}
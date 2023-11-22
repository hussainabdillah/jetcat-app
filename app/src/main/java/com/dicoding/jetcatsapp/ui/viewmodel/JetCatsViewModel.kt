package com.dicoding.jetcatsapp.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dicoding.jetcatsapp.data.CatsRepository
import kotlinx.coroutines.flow.MutableStateFlow

class JetCatsViewModel(private val repository: CatsRepository) : ViewModel() {

    private val _groupedCats = MutableStateFlow(
        repository.getCats()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedCats.value = repository.searchCats(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}

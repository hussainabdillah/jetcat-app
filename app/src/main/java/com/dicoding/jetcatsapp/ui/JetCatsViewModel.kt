package com.dicoding.jetcatsapp.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.jetcatsapp.data.CatsRepository
import com.dicoding.jetcatsapp.model.Cat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetCatsViewModel(private val repository: CatsRepository) : ViewModel() {

    private val _groupedCats = MutableStateFlow(
        repository.getCats()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupedCats: StateFlow<Map<Char, List<Cat>>> get() = _groupedCats

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedCats.value = repository.searchCats(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}

class ViewModelFactory(private val repository: CatsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetCatsViewModel::class.java)) {
            return JetCatsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
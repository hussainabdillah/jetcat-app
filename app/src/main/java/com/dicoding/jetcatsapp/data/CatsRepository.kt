package com.dicoding.jetcatsapp.data

import com.dicoding.jetcatsapp.model.Cat
import com.dicoding.jetcatsapp.model.CatsData

class CatsRepository {
    fun getCats(): List<Cat> {
        return CatsData.cats
    }

    fun searchCats(query: String): List<Cat> {
        return CatsData.cats.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}
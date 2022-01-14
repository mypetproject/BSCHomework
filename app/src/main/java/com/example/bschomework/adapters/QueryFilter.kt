package com.example.bschomework.adapters

interface QueryFilter<T> {
    fun filter(query: String, list: List<T>?)
}
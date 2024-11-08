package com.example.urdeli.model

data class MenuItem(
    val title: String,
    val icon: Int,
    val submenu: List<String> = emptyList(),
    var isExpanded: Boolean = false,
)

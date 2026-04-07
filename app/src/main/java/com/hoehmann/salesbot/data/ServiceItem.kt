package com.hoehmann.salesbot.data

data class ServiceItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: String,
    val highlights: List<String>,
    val location: String? = null
)

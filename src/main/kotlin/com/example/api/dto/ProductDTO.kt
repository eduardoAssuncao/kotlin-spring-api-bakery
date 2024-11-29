package com.example.api.dto

import java.math.BigDecimal

data class ProductRequest(
    val name: String,
    val description: String,
    val price: BigDecimal,
    val quantity: Int,
    val minimumStock: Int
)

data class ProductResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val quantity: Int,
    val minimumStock: Int,
    val lowStock: Boolean
)

data class StockUpdateRequest(
    val quantity: Int
)

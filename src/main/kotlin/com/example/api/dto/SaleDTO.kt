package com.example.api.dto

import com.example.api.model.PaymentMethod
import java.math.BigDecimal
import java.time.LocalDateTime

data class SaleRequest(
    val items: List<SaleItemRequest>,
    val paymentMethod: PaymentMethod
)

data class SaleItemRequest(
    val productId: Long,
    val quantity: Int
)

data class SaleResponse(
    val id: Long,
    val items: List<SaleItemResponse>,
    val totalAmount: BigDecimal,
    val paymentMethod: PaymentMethod,
    val saleDate: LocalDateTime
)

data class SaleItemResponse(
    val productName: String,
    val quantity: Int,
    val unitPrice: BigDecimal,
    val subtotal: BigDecimal
)

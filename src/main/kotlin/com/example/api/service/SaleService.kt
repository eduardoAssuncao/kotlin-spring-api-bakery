package com.example.api.service

import com.example.api.dto.*
import com.example.api.model.Sale
import com.example.api.model.SaleItem
import com.example.api.repository.SaleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class SaleService(
    private val saleRepository: SaleRepository,
    private val productService: ProductService
) {

    fun getAllSales(): List<SaleResponse> =
        saleRepository.findAll().map { it.toResponse() }

    fun getSale(id: Long): SaleResponse =
        saleRepository.findById(id)
            .orElseThrow { NoSuchElementException("Sale not found with id: $id") }
            .toResponse()

    fun getSalesByDateRange(startDate: LocalDateTime, endDate: LocalDateTime): List<SaleResponse> =
        saleRepository.findBySaleDateBetween(startDate, endDate).map { it.toResponse() }

    @Transactional
    fun createSale(request: SaleRequest): SaleResponse {
        val saleItems = request.items.map { item ->
            val product = productService.getProductEntity(item.productId)
            
            // Check if there's enough stock
            if (product.quantity < item.quantity) {
                throw IllegalStateException("Insufficient stock for product: ${product.name}")
            }
            
            // Update product stock
            productService.updateStock(product.id!!, product.quantity - item.quantity, product.minimumStock)
            
            SaleItem(
                product = product,
                quantity = item.quantity,
                unitPrice = product.price,
                subtotal = product.price.multiply(BigDecimal(item.quantity)),
                sale = Sale(
                    paymentMethod = request.paymentMethod,
                    totalAmount = BigDecimal.ZERO // temporary, will be updated
                )
            )
        }

        val totalAmount = saleItems.sumOf { it.subtotal }
        
        val sale = Sale(
            items = saleItems.toMutableList(),
            totalAmount = totalAmount,
            paymentMethod = request.paymentMethod
        )

        saleItems.forEach { it.sale = sale }
        
        return saleRepository.save(sale).toResponse()
    }

    private fun Sale.toResponse() = SaleResponse(
        id = id!!,
        items = items.map { it.toResponse() },
        totalAmount = totalAmount,
        paymentMethod = paymentMethod,
        saleDate = saleDate
    )

    private fun SaleItem.toResponse() = SaleItemResponse(
        productName = product.name,
        quantity = quantity,
        unitPrice = unitPrice,
        subtotal = subtotal
    )
}

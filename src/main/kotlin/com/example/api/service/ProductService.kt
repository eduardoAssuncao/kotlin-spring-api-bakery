package com.example.api.service

import com.example.api.dto.ProductRequest
import com.example.api.dto.ProductResponse
import com.example.api.model.Product
import com.example.api.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<ProductResponse> = 
        productRepository.findAll().map { it.toResponse() }

    fun getProduct(id: Long): ProductResponse = productRepository.findById(id)
        .orElseThrow { NoSuchElementException("Product not found with id: $id") }
        .toResponse()

    fun searchProducts(name: String): List<ProductResponse> = 
        productRepository.findByNameContainingIgnoreCase(name).map { it.toResponse() }

    fun getLowStockProducts(): List<ProductResponse> = 
        productRepository.findAll()
            .filter { it.quantity <= it.minimumStock }
            .map { it.toResponse() }

    @Transactional
    fun createProduct(request: ProductRequest): ProductResponse =
        productRepository.save(request.toEntity()).toResponse()

    @Transactional
    fun updateProduct(id: Long, request: ProductRequest): ProductResponse {
        val existingProduct = getProductEntity(id)
        return existingProduct.copy(
            name = request.name,
            description = request.description,
            price = request.price,
            quantity = request.quantity,
            minimumStock = request.minimumStock,
            updatedAt = LocalDateTime.now()
        ).let { productRepository.save(it) }.toResponse()
    }

    @Transactional
    fun updateStock(id: Long, quantity: Int): ProductResponse {
        val product = getProductEntity(id)
        return product.copy(
            quantity = quantity,
            updatedAt = LocalDateTime.now()
        ).let { productRepository.save(it) }.toResponse()
    }

    @Transactional
    fun deleteProduct(id: Long) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
        } else {
            throw NoSuchElementException("Product not found with id: $id")
        }
    }

    fun getProductEntity(id: Long): Product = productRepository.findById(id)
        .orElseThrow { NoSuchElementException("Product not found with id: $id") }

    private fun Product.toResponse() = ProductResponse(
        id = id!!,
        name = name,
        description = description,
        price = price,
        quantity = quantity,
        minimumStock = minimumStock,
        lowStock = quantity <= minimumStock
    )

    private fun ProductRequest.toEntity() = Product(
        name = name,
        description = description,
        price = price,
        quantity = quantity,
        minimumStock = minimumStock
    )
}

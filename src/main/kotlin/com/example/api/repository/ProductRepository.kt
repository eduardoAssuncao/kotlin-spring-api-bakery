package com.example.api.repository

import com.example.api.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByNameContainingIgnoreCase(name: String): List<Product>
    fun findByQuantityLessThanEqual(quantity: Int): List<Product>
}

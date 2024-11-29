package com.example.api.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(nullable = false)
    var name: String,
    
    @Column(nullable = false)
    var description: String,
    
    @Column(nullable = false)
    var price: BigDecimal,
    
    @Column(nullable = false)
    var quantity: Int,
    
    @Column(name = "minimum_stock")
    var minimumStock: Int,
    
    @OneToMany(mappedBy = "product")
    var saleItems: List<SaleItem> = mutableListOf(),
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
)

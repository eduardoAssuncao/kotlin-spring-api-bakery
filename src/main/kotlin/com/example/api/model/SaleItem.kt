package com.example.api.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "sale_items")
data class SaleItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    var sale: Sale,
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product,
    
    @Column(nullable = false)
    var quantity: Int,
    
    @Column(nullable = false)
    var unitPrice: BigDecimal,
    
    @Column(nullable = false)
    var subtotal: BigDecimal
)

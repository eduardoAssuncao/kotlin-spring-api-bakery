package com.example.api.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "sales")
data class Sale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @OneToMany(mappedBy = "sale", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableList<SaleItem> = mutableListOf(),
    
    @Column(nullable = false)
    var totalAmount: BigDecimal = BigDecimal.ZERO,
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var paymentMethod: PaymentMethod,
    
    @Column(nullable = false)
    var saleDate: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)

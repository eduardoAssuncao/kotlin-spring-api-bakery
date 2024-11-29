package com.example.api.controller

import com.example.api.dto.SaleRequest
import com.example.api.dto.SaleResponse
import com.example.api.service.SaleService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/sales")
class SaleController(private val saleService: SaleService) {

    @GetMapping
    fun getAllSales(): ResponseEntity<List<SaleResponse>> =
        ResponseEntity.ok(saleService.getAllSales())

    @GetMapping("/{id}")
    fun getSale(@PathVariable id: Long): ResponseEntity<SaleResponse> =
        ResponseEntity.ok(saleService.getSale(id))

    @GetMapping("/by-date-range")
    fun getSalesByDateRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startDate: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endDate: LocalDateTime
    ): ResponseEntity<List<SaleResponse>> =
        ResponseEntity.ok(saleService.getSalesByDateRange(startDate, endDate))

    @PostMapping
    fun createSale(@RequestBody request: SaleRequest): ResponseEntity<SaleResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(saleService.createSale(request))
}

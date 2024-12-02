package com.example.api.controller

import com.example.api.dto.ProductRequest
import com.example.api.dto.ProductResponse
import com.example.api.dto.StockUpdateRequest
import com.example.api.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<ProductResponse>> =
        ResponseEntity.ok(productService.getAllProducts())

    @GetMapping("/{id}")
    fun getProduct(@PathVariable id: Long): ResponseEntity<ProductResponse> =
        ResponseEntity.ok(productService.getProduct(id))

    @GetMapping("/search")
    fun searchProducts(@RequestParam name: String): ResponseEntity<List<ProductResponse>> =
        ResponseEntity.ok(productService.searchProducts(name))

    @GetMapping("/low-stock")
    fun getLowStockProducts(): ResponseEntity<List<ProductResponse>> =
        ResponseEntity.ok(productService.getLowStockProducts())

    @PostMapping
    fun createProduct(@RequestBody request: ProductRequest): ResponseEntity<ProductResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(request))

    @PutMapping("/{id}")
    fun updateProduct(
        @PathVariable id: Long,
        @RequestBody request: ProductRequest
    ): ResponseEntity<ProductResponse> =
        ResponseEntity.ok(productService.updateProduct(id, request))

    @PatchMapping("/{id}/stock")
    fun updateStock(
        @PathVariable id: Long,
        @RequestBody request: StockUpdateRequest
    ): ResponseEntity<ProductResponse> =
        ResponseEntity.ok(productService.updateStock(id, request.quantity, request.minimumStock))

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Unit> {
        productService.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}

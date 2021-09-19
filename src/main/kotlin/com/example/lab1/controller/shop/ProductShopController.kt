package com.example.lab1.controller.shop

import com.example.lab1.dto.ProductRequest
import com.example.lab1.dto.SProductResponse
import com.example.lab1.dto.SellerProductResponseAssembler
import com.example.lab1.entities.SellersProduct
import com.example.lab1.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shop/products")
class ProductShopController(
        private var productService : ProductService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody productRequest: ProductRequest) : SProductResponse {
        val product : SellersProduct = productService.create(productRequest)

        return SellerProductResponseAssembler.buildDto(product)
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateProductById(@RequestBody productRequest: ProductRequest, @PathVariable productId: Int) {
        productService.modify(productRequest, productId)
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProductById(@PathVariable productId: Int) {
        productService.delete(productId)
    }
}
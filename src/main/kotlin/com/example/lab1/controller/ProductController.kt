package com.example.lab1.controller

import com.example.lab1.dto.ProductRequest
import com.example.lab1.dto.SProductResponse
import com.example.lab1.dto.SellerProductResponseAssembler
import com.example.lab1.entities.SellersProduct
import com.example.lab1.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
        private var productService : ProductService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun listProducts(
            @RequestParam(required = false, defaultValue = "") q: String,
            @RequestParam(required = false, defaultValue = "") s: String,
            @RequestParam(required = false, defaultValue = "0") min: Int,
            @RequestParam(required = false, defaultValue = Int.MAX_VALUE.toString()) max: Int
    ) : List<SProductResponse> {
        val all : List<SellersProduct> = productService.getAll()
                .filter {
                    it.product.name.contains(q, true) ||
                            it.product.description.contains(q, true) &&
                            it.cost in min..max &&
                            it.seller.name.contains(s, true)
                }

        return all.map(SellerProductResponseAssembler::buildDto)
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getProductById(@PathVariable productId: Int) : SProductResponse {
        val product : SellersProduct = productService.getProduct(productId)
        return SellerProductResponseAssembler.buildDto(product)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody productRequest: ProductRequest) : SProductResponse {
        val product : SellersProduct = productService.create(productRequest)

        return SellerProductResponseAssembler.buildDto(product)
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateProduct(@RequestBody productRequest: ProductRequest, @PathVariable productId: Int) {
        productService.modify(productRequest, productId)
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProductById(@PathVariable productId: Int) {
        productService.delete(productId)
    }
}
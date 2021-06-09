package com.example.lab1.controller.client

import com.example.lab1.dto.SProductResponse
import com.example.lab1.dto.SellerProductResponseAssembler
import com.example.lab1.entities.SellersProduct
import com.example.lab1.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/all/products")
class ProductClientController(
        private var productService : ProductService
) {

    /**
     * Метод, который вызывается, когда клиенты пытаются найти какой-то продукт
     * используя наш маркетплейс.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun list(
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

    /**
     * Метод, который вызывается, когда клиент хочет получить подробную информацию о
     * конкретном продукте.
     */
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getOne(@PathVariable productId: Int) : SProductResponse {
        val product : SellersProduct = productService.getProduct(productId)
        return SellerProductResponseAssembler.buildDto(product)
    }
}
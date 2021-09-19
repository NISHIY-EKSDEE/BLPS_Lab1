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
        val filteredList = productService.getAllWithFilter(q, s, min, max)

        return filteredList.map(SellerProductResponseAssembler::buildDto)
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

    /**
     * Метод, который доступен только пользователю с ролью SHOP. Позволяет ДОБАВИТЬ
     * новый продукт.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProduct(@RequestBody productRequest: ProductRequest) : SProductResponse {
        val product : SellersProduct = productService.create(productRequest)

        return SellerProductResponseAssembler.buildDto(product)
    }

    /**
     * Метод, который доступен только пользователю с ролью SHOP.
     * Данный метод позволяет пользователю ИЗМЕНИТЬ какой-то продукт, который
     * ему принадлежит.
     */
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@RequestBody productRequest: ProductRequest, @PathVariable productId: Int) {
        productService.modify(productRequest, productId)
    }

    /**
     * Метод, который доступен только пользователю с ролью SHOP.
     * Данный метод позволяет пользователю УДАЛИТЬ какой-то продукт, который
     * ему принадлежит.
     */
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable productId: Int) {
        productService.delete(productId)
    }
}
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
//
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
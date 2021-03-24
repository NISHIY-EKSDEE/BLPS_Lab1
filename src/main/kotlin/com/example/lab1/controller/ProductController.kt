package com.example.lab1.controller

import com.example.lab1.dto.SellerProductAssembler
import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.ProductsEntity
import com.example.lab1.entities.SellerProductsEntity
import com.example.lab1.entities.SellersEntity
import com.example.lab1.service.ProductSellerService
import com.example.lab1.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController {

    @Autowired
    lateinit var productSellerService : ProductSellerService
    @Autowired
    lateinit var productService : ProductService

    //TODO протестить
    //TODO добавить поиск по тегам
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun list(
            @RequestParam(required = false, defaultValue = "") q: String,
            @RequestParam(required = false, defaultValue = "") s: String,
            @RequestParam(required = false, defaultValue = "0") min: Int,
            @RequestParam(required = false, defaultValue = Int.MAX_VALUE.toString()) max: Int
    ) : List<SellerProductDTO> {
        return productSellerService.getAllProducts().filter {
            (it.productName.contains(q, true) || it.productDescription.contains(q, true)) &&
            it.cost in min..max &&
            it.sellerName.contains(s, true)
        }
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getOne(@PathVariable productId: Long) : SellerProductDTO {
        return productSellerService.getProduct(productId)
    }

    @PostMapping
    fun save(@RequestBody orderForm : ProductForm) : ResponseEntity<SellerProductDTO> {
        val productEnt: ProductsEntity = productService.create(
                orderForm.getProductEnt()
        )

        val seller: SellersEntity = getSeller()

        val sellerProduct: SellerProductsEntity = SellerProductsEntity().apply {
            this.productsByProductId = productEnt
            this.sellersBySellerId = seller
            this.cost = orderForm.cost
            this.totalAmount = orderForm.totalAmount
        }

        val dto = productSellerService.create(sellerProduct)

        return ResponseEntity(dto, HttpStatus.CREATED)
    }


    //TODO убрать когда появятся роли
    private fun getSeller() : SellersEntity {
        TODO()
    }



    data class ProductForm(
            val cost : Int,
            val totalAmount : Int,
            val prodName : String,
            val prodDescription : String
    ) {
        fun getProductEnt() : ProductsEntity = ProductsEntity().apply {
            this.description = prodDescription
            this.name = prodName
        }

    }

}
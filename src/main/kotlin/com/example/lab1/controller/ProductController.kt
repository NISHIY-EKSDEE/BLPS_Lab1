package com.example.lab1.controller

import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.ProductEntity
import com.example.lab1.entities.SellerProductsEntity
import com.example.lab1.entities.SellerEntity
import com.example.lab1.entities.TagEntity
import com.example.lab1.exception.WrongRequestException
import com.example.lab1.repo.TagRepo
import com.example.lab1.service.ProductSellerService
import com.example.lab1.service.ProductService
import com.example.lab1.service.ProductTagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.collections.HashSet

@RestController
@RequestMapping("/api/products")
class ProductController {

    @Autowired
    lateinit var productSellerService : ProductSellerService
    @Autowired
    lateinit var productService : ProductService
    @Autowired
    lateinit var productTagService: ProductTagService
    @Autowired
    lateinit var tagRepo: TagRepo

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
                    it.sellerName?.contains(s, true)?:true
                }
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getOne(@PathVariable productId: Int) : SellerProductDTO {
        return productSellerService.getProduct(productId)
    }

    @GetMapping("/bytags")
    fun bytags(@RequestBody tagsForm : TagsForm) : ResponseEntity<Collection<SellerProductDTO>> {
        val listOfTags: MutableSet<TagEntity> = HashSet()

        for (tag in tagsForm.tags) {
            val tagEntities = tagRepo.findAllByName(tag)
            listOfTags.addAll(tagEntities)
        }

        val productsByTags = productTagService.getProductsByTags(listOfTags)
        val productSellerDTOs = productTagService.getSellerProductsDTOByProducts(productsByTags)

        return ResponseEntity(productSellerDTOs, HttpStatus.OK)
    }

    @PostMapping
    fun save(@RequestBody productForm : ProductForm) : ResponseEntity<SellerProductDTO> {
        //TODO вынести логику в отдельный сервис и обернуть в транзакцию
        val productEnt: ProductEntity = productService.create(
                productForm.getProductEnt()
        )

        val seller: SellerEntity = getSeller()

        val sellerProduct: SellerProductsEntity = SellerProductsEntity().apply {
            this.productByProductId = productEnt
            this.sellerBySellerId = seller
            this.cost = productForm.cost
            this.totalAmount = productForm.totalAmount

        }

        sellerProductValidate(sellerProduct)

        val dto = productSellerService.create(sellerProduct)
        //TODO----------------------------------------------------------

        return ResponseEntity(dto, HttpStatus.CREATED)
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@RequestBody productForm: ProductForm, @PathVariable productId: Int) {
        var productSeller = productSellerService.getProduct(productId)


    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable productId: Int) {
        productSellerService.delete(productId)
    }

    private fun sellerProductValidate(ent: SellerProductsEntity) {
        if (ent.cost == null) throw WrongRequestException("The cost can't be null!")
        if (ent.cost!! <= 0)  throw WrongRequestException("The cost can't be less or equal zero!")
        if (ent.totalAmount < 0 ) throw WrongRequestException("The total amount can't be less than zero!")
    }

    //TODO убрать когда появятся роли
    private fun getSeller() : SellerEntity {
        return SellerEntity().apply { id=1 }
    }

    data class ProductForm(
            val cost : Int,
            val totalAmount : Int,
            val prodName : String,
            val prodDescription : String
    ) {
        fun getProductEnt() : ProductEntity = ProductEntity().apply {
            this.description = prodDescription
            this.name = prodName
        }
    }

    data class TagsForm(
            val tags : List<String>
    )



}
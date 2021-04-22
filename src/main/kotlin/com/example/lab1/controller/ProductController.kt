package com.example.lab1.controller

import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.*
import com.example.lab1.exception.WrongRequestException
import com.example.lab1.repo.TagRepo
import com.example.lab1.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
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
    lateinit var userService: UserService

    @Autowired
    lateinit var sellerService: SellerService

    @Autowired
    lateinit var tagRepo: TagRepo

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
    ) : List<SellerProductDTO> {
        return productSellerService.getAllProducts().filter {
                    (it.productName.contains(q, true) || it.productDescription.contains(q, true)) &&
                    it.cost in min..max &&
                    it.sellerName?.contains(s, true)?:true
                }
    }

    /**
     * Метод, который вызывается, когда клиент хочет получить подробную информацию о
     * конкретном продукте.
     */
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun getOne(@PathVariable productId: Int) : SellerProductDTO {
        return productSellerService.getProduct(productId)
    }

    /**
     * Метод, который позволяет пользователю с ролью CLIENT найти информацию о продукте, используя
     * набор тегов.
     */
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

    /**
     * Метод, который доступен только пользователю с ролью SHOP. Позволяет ДОБАВИТЬ
     * новый продукт.
     */
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

    /**
     * Метод, который доступен только пользователю с ролью SHOP.
     * Данный метод позволяет пользователю ИЗМЕНИТЬ какой-то продукт, который
     * ему принадлежит.
     */
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@RequestBody productForm: ProductForm, @PathVariable productId: Int) {
        TODO()
        var productSeller = productSellerService.getProduct(productId)
    }

    /**
     * Метод, который доступен только пользователю с ролью SHOP.
     * Данный метод позволяет пользователю УДАЛИТЬ какой-то продукт, который
     * ему принадлежит.
     */
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

    private fun getSeller() : SellerEntity {
        var userDetails: UserDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user: User = userService.getUserByUsername(userDetails.username) ?:
            throw WrongRequestException("User not found!")

        val seller = sellerService.getSellerByUser(user) ?:
            throw WrongRequestException("Seller not found!")

        return seller
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
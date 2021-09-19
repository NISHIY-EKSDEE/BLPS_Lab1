package com.example.lab1.service

import com.example.lab1.dto.ProductRequest
import com.example.lab1.entities.*
import com.example.lab1.repo.SellersProductProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate

@Service
class ProductService(
        private var sellersProductProvider: SellersProductProvider,
        private var sellerProvider: SellerProvider,
        private var userContextService: UserContextService
) {

    @Autowired
    lateinit var template: TransactionTemplate


    fun getAll(): List<SellersProduct> {
        return sellersProductProvider.getAll()
    }

    fun getAllWithFilter(query: String, sellerName: String, min: Int, max: Int): List<SellersProduct> {
        val filteredList : List<SellersProduct> = getAll()
                .filter {
                    it.product.name.contains(query, true) ||
                    it.product.description.contains(query, true) &&
                    it.cost in min..max &&
                    it.seller.name.contains(sellerName, true)
                }

        return filteredList
    }

    fun getProduct(id: Int): SellersProduct {
        return sellersProductProvider.get(id)
    }

    fun delete(productId: Int) {
        sellersProductProvider.delete(productId)
    }


    fun create(productRequest: ProductRequest): SellersProduct {
        return template.execute{
            val product = productRequest.getProductEnt()
            val user: User =  userContextService.getUser()
            val seller: Seller = sellerProvider.getByUser(user)

            val sproduct = SellersProduct(
                    cost = productRequest.cost,
                    totalAmount = productRequest.totalAmount,
                    product = product,
                    seller = seller
            )

            sellersProductProvider.save(sproduct)
        } ?: throw RuntimeException("Transaction went wrong!")
    }

    fun modify(productRequest: ProductRequest, sProductId: Int): SellersProduct {
        return template.execute{
            val sproduct = sellersProductProvider.get(sProductId)

            sproduct.cost = productRequest.cost
            sproduct.totalAmount = productRequest.totalAmount
            sproduct.product.name = productRequest.prodName
            sproduct.product.description = productRequest.prodDescription

            sellersProductProvider.save(sproduct)
        } ?: throw RuntimeException("Transaction went wrong!")
    }
}
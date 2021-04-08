package com.example.lab1.service

import com.example.lab1.dto.SellerProductAssembler
import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.SellerProductsEntity
import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.repo.ProductRepo
import com.example.lab1.repo.SellerProductRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductSellerServiceImpl: ProductSellerService {

    @Autowired
    private lateinit var sellerProdRepo : SellerProductRepo

    @Autowired
    private lateinit var productRepo : ProductRepo

    override fun getAllProducts(): List<SellerProductDTO> {
        return sellerProdRepo.findAll().map(SellerProductAssembler::buildDto)
    }

    override fun getProduct(id: Int): SellerProductDTO {
        return SellerProductAssembler.buildDto(
                sellerProdRepo.findById(id).orElseThrow{
                    ResourceNotFoundException(message = "Product not found!")
                }
        )
    }

    override fun create(product: SellerProductsEntity): SellerProductDTO {
         return SellerProductAssembler.buildDto(sellerProdRepo.save(product))
    }

    override fun update(product: SellerProductsEntity) {
        create(product)
    }

    override fun delete(id: Int) {
        if (!sellerProdRepo.existsById(id)) throw ResourceNotFoundException(message = "Product not found!")
        sellerProdRepo.deleteById(id)
    }


}
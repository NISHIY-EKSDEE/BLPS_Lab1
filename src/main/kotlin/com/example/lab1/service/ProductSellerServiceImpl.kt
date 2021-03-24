package com.example.lab1.service

import com.example.lab1.dto.SellerProductAssembler
import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.SellerProductsEntity
import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.repo.SellerProductRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductSellerServiceImpl: ProductSellerService {

    @Autowired
    private lateinit var sellerProdRepo : SellerProductRepo

    override fun getAllProducts(): List<SellerProductDTO> {
        return sellerProdRepo.findAll().map(SellerProductAssembler::buildDto)
    }

    override fun getProduct(id: Long): SellerProductDTO {
        return SellerProductAssembler.buildDto(
                sellerProdRepo.findById(id).orElseThrow{
                    ResourceNotFoundException(message = "Product not found!")
                }
        )
    }

    override fun create(product: SellerProductsEntity): SellerProductDTO {
         return SellerProductAssembler.buildDto(sellerProdRepo.save(product))
    }

}
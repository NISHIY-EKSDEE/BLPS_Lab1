package com.example.lab1.service

import com.example.lab1.dto.SellerProductAssembler
import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.ProductsEntity
import com.example.lab1.entities.TagsEntity
import com.example.lab1.repo.ProductTagRepo
import com.example.lab1.repo.SellerProductRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductTagServiceImpl: ProductTagService {

    @Autowired
    lateinit var productTagRepo : ProductTagRepo

    override fun getProductsByTags(tags: Collection<TagsEntity>): Collection<ProductsEntity> {
        val res: MutableSet<ProductsEntity> = HashSet()
        for (tag in tags) {
            val pts = productTagRepo.findAllByTagsByTagId(tag)
            for (pte in pts) {
                res.add(pte.productsByProductId!!)
            }
        }
        return res
    }

    override fun getSellerProductsDTOByProducts(products: Collection<ProductsEntity>): Collection<SellerProductDTO> {
        var res = ArrayList<SellerProductDTO>()
        for (product in products) {
            var sellerProducts = product.sellerProductsById!!
            for (sellerProduct in sellerProducts) {
                res.add(SellerProductAssembler.buildDto(sellerProduct))
            }
        }
        return res
    }


}
package com.example.lab1.service

import com.example.lab1.dto.SellerProductAssembler
import com.example.lab1.dto.SellerProductDTO
import com.example.lab1.entities.ProductEntity
import com.example.lab1.entities.TagEntity
import com.example.lab1.repo.ProductTagRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductTagServiceImpl: ProductTagService {

    @Autowired
    lateinit var productTagRepo : ProductTagRepo

    override fun getProductsByTags(tags: Collection<TagEntity>): Collection<ProductEntity> {
        val res: MutableSet<ProductEntity> = HashSet()
        for (tag in tags) {
            val pts = productTagRepo.findAllByTagByTagId(tag)
            for (pte in pts) {
                res.add(pte.productByProductId!!)
            }
        }
        return res
    }

    override fun getSellerProductsDTOByProducts(products: Collection<ProductEntity>): Collection<SellerProductDTO> {
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
package com.example.lab1.repo

import com.example.lab1.entities.ProductTagEntity
import com.example.lab1.entities.TagEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductTagRepo : CrudRepository<ProductTagEntity, Int> {
    fun findAllByTagByTagId(tagByTagId: TagEntity): Collection<ProductTagEntity>
}
package com.example.lab1.repo

import com.example.lab1.entities.ProductTagsEntity
import com.example.lab1.entities.TagsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductTagRepo : CrudRepository<ProductTagsEntity, Int> {
    fun findAllByTagsByTagId(tagsByTagId: TagsEntity): Collection<ProductTagsEntity>
}
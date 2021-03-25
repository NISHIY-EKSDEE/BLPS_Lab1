package com.example.lab1.repo

import com.example.lab1.entities.TagsEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepo : CrudRepository<TagsEntity, Int> {
    fun findAllByName(name: String) : Iterable<TagsEntity>
}
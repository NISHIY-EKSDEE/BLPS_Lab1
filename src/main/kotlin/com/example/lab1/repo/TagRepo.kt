package com.example.lab1.repo

import com.example.lab1.entities.TagEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepo : CrudRepository<TagEntity, Int> {
    fun findAllByName(name: String) : Iterable<TagEntity>
}
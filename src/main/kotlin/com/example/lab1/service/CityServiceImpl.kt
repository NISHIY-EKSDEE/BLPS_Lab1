package com.example.lab1.service

import com.example.lab1.entities.CityEntity
import com.example.lab1.repo.CityRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityServiceImpl : CityService{

    @Autowired
    lateinit var cityRepo: CityRepo

    override fun findAll(): Iterable<CityEntity> {
        return cityRepo.findAll()
    }

    override fun save(city: CityEntity): CityEntity {
        return cityRepo.save(city)
    }

}
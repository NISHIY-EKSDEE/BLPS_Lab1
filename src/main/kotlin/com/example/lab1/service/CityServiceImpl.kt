package com.example.lab1.service

import com.example.lab1.entities.CitiesEntity
import com.example.lab1.repo.CityRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CityServiceImpl : CityService{

    @Autowired
    lateinit var cityRepo: CityRepo

    override fun findAll(): Iterable<CitiesEntity> {
        return cityRepo.findAll()
    }

    override fun save(city: CitiesEntity): CitiesEntity {
        return cityRepo.save(city)
    }

}
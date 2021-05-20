package com.example.lab1.repo

import com.example.lab1.entities.PickupPoint
import com.example.lab1.exception.ResourceNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class PickupPointProvider(
        private var pickupPointRepository: PickupPointRepository,
) {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun getAll() : List<PickupPoint> {
        return pickupPointRepository
                .findAll()
                .map {it}
    }

    @Transactional
    fun createOrModify(point: PickupPoint) : PickupPoint {
        val savedPoint = pickupPointRepository.save(point)
        refresh(savedPoint)
        return savedPoint
    }

    fun modify(point: PickupPoint): PickupPoint {
        if (!pickupPointRepository.existsById(point.id))
            throw ResourceNotFoundException("This point doesn't exists")

        return pickupPointRepository.save(point)
    }

    fun delete(pointId: Int) {
        if (!pickupPointRepository.existsById(pointId))
            throw ResourceNotFoundException("This point doesn't exists")

        pickupPointRepository.deleteById(pointId)
    }

    fun refresh(point: PickupPoint) {
        entityManager.refresh(point)
    }

    fun get(pointId: Int): PickupPoint {
        return pickupPointRepository.findByIdOrNull(pointId) ?:
            throw ResourceNotFoundException("This point doesn't exists")
    }
}
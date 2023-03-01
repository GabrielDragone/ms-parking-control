package com.ms.parkingcontrol.services

import com.ms.parkingcontrol.models.ParkingSpotModel
import com.ms.parkingcontrol.repositories.ParkingSpotRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ParkingSpotService(private val repository: ParkingSpotRepository) {

    @Transactional // Caso algo dê errado durante a transação, é feito um rollback para não termos dados incompletos, principalmente quando temos relacionamentos
    fun save(parkingSpotModel: ParkingSpotModel): ParkingSpotModel {
        return repository.save(parkingSpotModel)
    }

}
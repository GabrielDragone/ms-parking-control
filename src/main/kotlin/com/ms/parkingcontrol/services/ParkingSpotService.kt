package com.ms.parkingcontrol.services

import com.ms.parkingcontrol.models.ParkingSpotModel
import com.ms.parkingcontrol.repositories.ParkingSpotRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
class ParkingSpotService(private val repository: ParkingSpotRepository) {

    private val LOGGER = LoggerFactory.getLogger(ParkingSpotService::class.java)

    @Transactional // Caso algo dê errado durante a transação, é feito um rollback para não termos dados incompletos, principalmente quando temos relacionamentos
    fun save(parkingSpotModel: ParkingSpotModel): ParkingSpotModel? {
        try {
            LOGGER.info("Salvando novo registro")
            return repository.save(parkingSpotModel)
        } catch (e: Exception) {
            LOGGER.warn("Erro ao salvar novo registro: {}", e.message)
            return null
        }
    }

    fun existsByLicensePlateCar(licensePlateCar: String): Boolean {
        return repository.existsByLicensePlateCar(licensePlateCar)
    }

    fun existsByParkingSpotNumber(parkingSpotNumber: String): Boolean {
        return repository.existsByParkingSpotNumber(parkingSpotNumber)
    }

    fun existsByApartmentAndBlock(apartment: String, block: String): Boolean {
        return repository.existsByApartmentAndBlock(apartment, block)
    }

//    fun findAll(): List<ParkingSpotModel>? {
    fun findAll(pageable: Pageable): Page<ParkingSpotModel> {
        return repository.findAll(pageable)
    }

    fun findById(id: UUID): Optional<ParkingSpotModel> {
        return repository.findById(id)
    }

    @Transactional
    fun deleteById(id: UUID) {
        repository.deleteById(id)
    }

}
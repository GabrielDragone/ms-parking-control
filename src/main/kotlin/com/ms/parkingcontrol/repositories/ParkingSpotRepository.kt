package com.ms.parkingcontrol.repositories

import com.ms.parkingcontrol.models.ParkingSpotModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository // Não é obrigatório, pois JpaRepository já tem a annotation
interface ParkingSpotRepository: JpaRepository<ParkingSpotModel, UUID> {
}
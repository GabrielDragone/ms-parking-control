package com.ms.parkingcontrol.controllers

import com.ms.parkingcontrol.dtos.ParkingSpotDto
import com.ms.parkingcontrol.models.ParkingSpotModel
import com.ms.parkingcontrol.services.ParkingSpotService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.BeanUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import javax.validation.Valid

@RestController
@CrossOrigin(origins = arrayOf("*"), maxAge = 3600) // Permite que seja acessada de qualquer origem
@RequestMapping("/api/v1/parking-spot")
class ParkingSpotController(private val service: ParkingSpotService) {

    @PostMapping
    @Operation(description = "Endpoint para criação de vaga de estacionamento")
    fun createParkingSpot(@RequestBody @Valid parkingSpotDto: ParkingSpotDto): ResponseEntity<Any> { // Any/Object pq poderemos ter diferentes tipos de retorno
        val parkingSpotModel = ParkingSpotModel()
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel)
        parkingSpotModel.registrationDate = LocalDateTime.now(ZoneId.of("UTC"))
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(parkingSpotModel))
    }

}
package com.ms.parkingcontrol.controllers

import com.ms.parkingcontrol.dtos.ParkingSpotDto
import com.ms.parkingcontrol.models.ParkingSpotModel
import com.ms.parkingcontrol.services.ParkingSpotService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Optional
import java.util.UUID
import javax.validation.Valid
import javax.websocket.server.PathParam

@RestController
@CrossOrigin(origins = arrayOf("*"), maxAge = 3600) // Permite que seja acessada de qualquer origem
@RequestMapping("/api/v1/parking-spot")
class ParkingSpotController(private val service: ParkingSpotService) {

    @PostMapping
    @Operation(description = "Endpoint para criação de vaga de estacionamento")
    fun createParkingSpot(@RequestBody @Valid parkingSpotDto: ParkingSpotDto): ResponseEntity<Any> { // Any/Object pq poderemos ter diferentes tipos de retorno
        if (service.existsByParkingSpotNumber(parkingSpotDto.parkingSpotNumber)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Essa vaga já está em uso!")
        }

        if (service.existsByLicensePlateCar(parkingSpotDto.licensePlateCar)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Essa placa de carro já está registrada em uma vaga!")
        }

        if (service.existsByApartmentAndBlock(parkingSpotDto.apartment, parkingSpotDto.block)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Vaga já registrada para esse apartamento/bloco!")
        }

        val parkingSpotModel = ParkingSpotModel()
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel)
        parkingSpotModel.registrationDate = LocalDateTime.now(ZoneId.of("UTC"))
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(parkingSpotModel))
    }

    @GetMapping
    @Operation(description = "Endpoint para buscar todos as vagas")
    fun getAll(
        @PageableDefault(page = 0, size = 10, sort = arrayOf("id"), direction = Sort.Direction.ASC) pageable: Pageable //  "sort": ["registrationDate,DESC"]
//    ): ResponseEntity<List<ParkingSpotModel>> {
    ): ResponseEntity<Page<ParkingSpotModel>> {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable))
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint para buscar vaga especifica")
    fun findById(@PathVariable(value = "id") id: UUID): ResponseEntity<Any> {
        val vagaEncontrada: Optional<ParkingSpotModel> = service.findById(id)
        if (!vagaEncontrada.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!")
        }
        return ResponseEntity.status(HttpStatus.OK).body(vagaEncontrada.get())
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint para remover vaga especifica")
    fun deleteById(@PathVariable(value = "id") id: UUID): ResponseEntity<Any> {
        val vagaEncontrada: Optional<ParkingSpotModel> = service.findById(id)
        if (!vagaEncontrada.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!")
        }
        service.deleteById(id)
        return ResponseEntity.status(HttpStatus.OK).body("Registro de vaga deletada com sucesso!")
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint para atualizar vaga especifica")
    fun update(
        @PathVariable(value = "id") id: UUID,
        @RequestBody @Valid parkingSpotDto: ParkingSpotDto
               ): ResponseEntity<Any> {

        val vagaEncontrada: Optional<ParkingSpotModel> = service.findById(id)
        if (!vagaEncontrada.isPresent) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada!")
        }

        val parkingSpotModel = ParkingSpotModel()
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel)
        parkingSpotModel.id = vagaEncontrada.get().id
        parkingSpotModel.registrationDate = vagaEncontrada.get().registrationDate
        return ResponseEntity.status(HttpStatus.OK).body(service.save(parkingSpotModel))
    }

}
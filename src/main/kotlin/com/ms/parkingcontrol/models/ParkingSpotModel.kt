package com.ms.parkingcontrol.models

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "TB_PARKING_SPOT")
data class ParkingSpotModel (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null, // UUID: Recomendado quando utilizamos de estruturas de MS.
    @Column(nullable = false, unique = true, length = 10)
    var parkingSpotNumber: String? = null,
    @Column(nullable = false, unique = true, length = 7)
    var licensePlateCar: String? = null,
    @Column(nullable = false, length = 70)
    var brandCar: String? = null,
    @Column(nullable = false, length = 70)
    var modelCar: String? = null,
    @Column(nullable = false, length = 70)
    var colorCar: String? = null,
    @Column(nullable = false, length = 70)
    var registrationDate: LocalDateTime? = null,
    @Column(nullable = false, length = 130)
    var responsibleName: String? = null,
    @Column(nullable = false, length = 30)
    var apartment: String? = null,
    @Column(nullable = false, length = 30)
    var block: String? = null

)
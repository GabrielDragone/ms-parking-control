package com.ms.parkingcontrol.dtos

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class ParkingSpotDto (

    @field:NotBlank(message = "O campo parkingSpotNumber é obrigatório") // Não pode ser nulo ou vazio
    var parkingSpotNumber: String,
    @NotBlank(message = "O campo licensePlateCar é obrigatório")
    @Size(max = 7, message = "O campo licensePlateCar deve ter no máximo 7 caracteres")
    var licensePlateCar: String,
    @NotBlank(message = "O campo brandCar é obrigatório")
    var brandCar: String,
    @NotBlank(message = "O campo modelCar é obrigatório")
    var modelCar: String,
    @NotBlank(message = "O campo colorCar é obrigatório")
    var colorCar: String,
    @NotBlank(message = "O campo responsibleName é obrigatório")
    var responsibleName: String,
    @NotBlank(message = "O campo apartment é obrigatório")
    var apartment: String,
    @NotBlank(message = "O campo block é obrigatório")
    var block: String

)
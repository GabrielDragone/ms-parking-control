package com.ms.parkingcontrol.configs

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.format.DateTimeFormatter

@Configuration
class DateConfig {

    final val DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'" // ISO-8601 UTC - O Z é UTC
    final val LOCAL_DATETIME_SERIALIZER = LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT))

    @Bean // Para o Spring reconhecer o ObjectMapper que é uma classe externa como Bean
    @Primary // Questão de prioridade, se depois forem feitas outras configurações com o ObjectMapper
    fun objectMapper(): ObjectMapper {
        val module = JavaTimeModule()
        module.addSerializer(LOCAL_DATETIME_SERIALIZER)
        return ObjectMapper().registerModule(module)
    }

}
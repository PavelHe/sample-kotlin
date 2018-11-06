package com.github.pavelvil

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class MicroserviceKotlinApplication

fun main(args: Array<String>) {
    runApplication<MicroserviceKotlinApplication>(*args)
}

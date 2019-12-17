package com.nenaner.aoc2019

import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Component
class OutputLogger {
    fun debug(message: String) {
        println("${getCurrentTimeStampString()}, debug: $message")
    }

    fun info(message: String) {
        println("${getCurrentTimeStampString()}, info: $message")
    }

    fun warning(message: String) {
        println("${getCurrentTimeStampString()}, warning: $message")
    }

    fun error(message: String) {
        println("${getCurrentTimeStampString()}, ERROR: $message")
    }

    fun getCurrentTimeStampString(): String {
        return DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
    }
}
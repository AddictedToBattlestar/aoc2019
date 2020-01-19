package com.nenaner.aoc2019

import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Component
class OutputLogger {
    var currentLoggingLevel: LoggingLevel = LoggingLevel.INFO

    fun debug(message: String) {
        if (currentLoggingLevel == LoggingLevel.DEBUG)
            println("${getCurrentTimeStampString()}, debug: $message")
    }

    fun info(message: String) {
        if (currentLoggingLevel == LoggingLevel.DEBUG || currentLoggingLevel == LoggingLevel.INFO)
            println("${getCurrentTimeStampString()}, info: $message")
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

    companion object {
        enum class LoggingLevel {
            DEBUG,
            INFO,
            ERROR
        }
    }
}
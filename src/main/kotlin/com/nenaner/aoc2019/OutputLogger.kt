package com.nenaner.aoc2019

import org.springframework.stereotype.Component
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Component
class OutputLogger {
    fun debug(message: String) {
        val timeStamp = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        println("$timeStamp, debug: $message")
    }
    fun info(message: String) {
        val timeStamp = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        println("$timeStamp, info: $message")
    }
    fun warning(message: String) {
        val timeStamp = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
        println("$timeStamp, WARNING: $message")
    }
}
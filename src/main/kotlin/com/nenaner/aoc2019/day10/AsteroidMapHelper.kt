package com.nenaner.aoc2019.day10

import com.nenaner.aoc2019.OutputLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AsteroidMapHelper @Autowired constructor(private val outputLogger: OutputLogger) {
    var spacing = 3

    fun generateAsteroidMap(asteroidMapAsString: String): Array<Array<Char>> {
        val asteroidMapList = mutableListOf<Array<Char>>()
        for (mapLine in asteroidMapAsString.lines()) {
            asteroidMapList.add(mapLine.toCharArray().toTypedArray())
        }
        return asteroidMapList.toTypedArray()
    }

    fun printAsteroidMap(asteroidMap: Array<Array<Int>>) {
        var result = "\n"
        for (rowIndex in asteroidMap.indices) {
            val mapRow = asteroidMap[rowIndex]
            for (columnIndex in mapRow.indices) {
                result += mapRow[columnIndex].toString().padStart(this.spacing, ' ')
            }
            result += "\n"
        }
        outputLogger.info(result)
    }

    fun printAsteroidMap(asteroidMap: Array<Array<Char>>) {
        var result = "\n"
        for (rowIndex in asteroidMap.indices) {
            val mapRow = asteroidMap[rowIndex]
            for (columnIndex in mapRow.indices) {
                result += mapRow[columnIndex].toString().padStart(this.spacing, ' ')
            }
            result += "\n"
        }
        outputLogger.info(result)
    }
}
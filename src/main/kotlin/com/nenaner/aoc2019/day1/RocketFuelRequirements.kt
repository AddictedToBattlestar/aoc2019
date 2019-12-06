package com.nenaner.aoc2019.day1

import com.nenaner.aoc2019.FileManager
import org.springframework.stereotype.Component
import kotlin.math.floor

@Component
class RocketFuelRequirements (private val fileManager: FileManager) {
    fun getFuelRequirements(mass: Long): Long {
        val fuelRequirements = floor(mass / 3.0).toLong() - 2
        if (fuelRequirements <= 0) {
            return 0
        }
        val additionalFuelRequirements = getFuelRequirements(fuelRequirements)
        return fuelRequirements + additionalFuelRequirements
    }

    fun getRocketFuelRequirements(): Long {
        var totalFuelRequirements = 0L
        val fileContent = fileManager.readFileAsLinesUsingGetResourceAsStream(fileNameContainingMassValues)
        fileContent.forEach {
            val mass = it.toLong();
            val fuelRequired = getFuelRequirements(mass)
            totalFuelRequirements += fuelRequired
        }
        return totalFuelRequirements
    }

    companion object {
        const val fileNameContainingMassValues = "day1.massValuesForRocketModules.in"
    }
}


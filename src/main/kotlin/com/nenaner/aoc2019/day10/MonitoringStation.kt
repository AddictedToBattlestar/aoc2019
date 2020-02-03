package com.nenaner.aoc2019.day10

import com.nenaner.aoc2019.OutputLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class MonitoringStation @Autowired constructor(private val outputLogger: OutputLogger,
                                               private val broadeningSweepScan: BroadeningSweepScan,
                                               private val monitoringStationGiantLaser: MonitoringStationGiantLaser,
                                               private val asteroidMapHelper: AsteroidMapHelper) {
    fun findBestLocation(asteroidMapAsString: String): Triple<Int, Int, Int> {
        val asteroidMap = asteroidMapHelper.generateAsteroidMap(asteroidMapAsString)
        asteroidMapHelper.printAsteroidMap(asteroidMap)
        val mapOfLineOfSightCounts = generateMapForLineOfSight(asteroidMap)
        asteroidMapHelper.printAsteroidMap(mapOfLineOfSightCounts)
        val bestLocation = findBestLocationInMap(mapOfLineOfSightCounts)
        outputLogger.info("The best location found in the map provided is $bestLocation")
        return bestLocation
    }

    private fun generateMapForLineOfSight(asteroidMap: Array<Array<Char>>): Array<Array<Int>> {
        val lineOfSightMap = mutableListOf<Array<Int>>()
        for (y in asteroidMap.indices) {
            val asteroidMapLine = asteroidMap[y]
            val lineOfSightLine = mutableListOf<Int>()
            for (x in asteroidMapLine.indices) {
                val totalNumber = broadeningSweepScan.calculateTotalNumberOfAsteroidsVisible(asteroidMap, Pair(x, y))
                lineOfSightLine.add(totalNumber)
            }
            lineOfSightMap.add(lineOfSightLine.toTypedArray())
        }
        return lineOfSightMap.toTypedArray()
    }

    private fun findBestLocationInMap(mapOfLineOfSightCounts: Array<Array<Int>>): Triple<Int, Int, Int> {
        var result: Triple<Int, Int, Int> = Triple(0, 0, mapOfLineOfSightCounts[0][0])
        for (y in mapOfLineOfSightCounts.indices) {
            val mapRow = mapOfLineOfSightCounts[y]
            for (x in mapRow.indices) {
                if (mapRow[x] > result.third)
                    result = Triple(x, y, mapRow[x])
            }
        }
        return result
    }

    fun vaporizeAsteroids(asteroidMapAsString: String): Map<Int, Pair<Int,Int>> {
        val asteroidsVaporizedInOrder = mutableMapOf<Int, Pair<Int,Int>>()

        val asteroidMap = asteroidMapHelper.generateAsteroidMap(asteroidMapAsString)
        asteroidMapHelper.printAsteroidMap(asteroidMap)

        val sequenceForDestructionOfAsteroids: Map<Int, Pair<Int, Int>> = monitoringStationGiantLaser.destroyAllAsteroids(asteroidMap)

        return asteroidsVaporizedInOrder
    }


    companion object {
        const val asteroidMarker = '#'
        const val emptySpaceMarker = '.'
        const val blockedVisibilityMarker = 'B'
        const val currentPositionMarker = 'X'
    }
}
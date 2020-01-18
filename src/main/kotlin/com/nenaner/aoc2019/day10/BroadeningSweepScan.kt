package com.nenaner.aoc2019.day10

import com.nenaner.aoc2019.OutputLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BroadeningSweepScan @Autowired constructor(private val asteroidMapHelper: AsteroidMapHelper,
                                                 private val outputLogger: OutputLogger) {
    fun calculateTotalNumberOfAsteroidsVisible(asteroidMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>): Int {
        if (asteroidMap[currentPosition.second][currentPosition.first] != MonitoringStation.asteroidMarker) return 0
        outputLogger.debug("Evaluating asteroid visibility from coordinates: ${currentPosition.first},${currentPosition.second}")
        val tallyMap = asteroidMap.copy()
        tallyMap[currentPosition.second][currentPosition.first] = MonitoringStation.currentPositionMarker
        val sweepScan = SweepScan(tallyMap, currentPosition, outputLogger)
        var sweepRadius = 1
        do {
            val continueSweeps = sweepScan.performSweep(sweepRadius++)
        } while (continueSweeps)
//        asteroidMapHelper.printAsteroidMap(tallyMap)
        val totalNumberVisible = getAsteroidCount(tallyMap)
        return totalNumberVisible
    }

    private fun Array<Array<Char>>.copy() = map { it.clone() }.toTypedArray()

    private fun getAsteroidCount(tallyMap: Array<Array<Char>>): Int {
        var tally = 0
        for (tallyRow in tallyMap)
            for (coordinateToTally in tallyRow)
                if (coordinateToTally == MonitoringStation.asteroidMarker)
                    tally++
        return tally
    }
}
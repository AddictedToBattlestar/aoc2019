package com.nenaner.aoc2019.day10

import com.nenaner.aoc2019.OutputLogger
import kotlin.math.atan2

class SweepScan(private val tallyMap: Array<Array<Char>>,
                private val currentPosition: Pair<Int, Int>,
                private val outputLogger: OutputLogger) {
    private var continueSweep = false
    private val pastEncounterVectors = mutableListOf<Double>()

    fun performSweep(sweepRadius: Int): Boolean {
        outputLogger.debug("Starting sweep with a radius of $sweepRadius")
        continueSweep = false
        val upperLeftPosition = Pair(currentPosition.first - sweepRadius, currentPosition.second - sweepRadius)
        val upperRightPosition = Pair(currentPosition.first + sweepRadius, currentPosition.second - sweepRadius)
        val lowerLeftPosition = Pair(currentPosition.first - sweepRadius, currentPosition.second + sweepRadius)
        val lowerRightPosition = Pair(currentPosition.first + sweepRadius, currentPosition.second + sweepRadius)

        evaluateMapPosition(upperLeftPosition)
        evaluateMapPosition(upperRightPosition)
        evaluateMapPosition(lowerLeftPosition)
        evaluateMapPosition(lowerRightPosition)

        verticalSweep(upperLeftPosition.first, upperLeftPosition.second, lowerLeftPosition.second)
        horizontalSweep(upperLeftPosition.second, upperLeftPosition.first, upperRightPosition.first)
        verticalSweep(upperRightPosition.first, upperRightPosition.second, lowerRightPosition.second)
        horizontalSweep(lowerLeftPosition.second, lowerLeftPosition.first, lowerRightPosition.first)

        return this.continueSweep
    }

    private fun verticalSweep(columnIndex: Int, startingRowIndex: Int, endingRowIndex: Int) {
        for (rowIndex in startingRowIndex + 1 until endingRowIndex) {
            evaluateMapPosition(Pair(columnIndex, rowIndex))
        }
    }

    private fun horizontalSweep(rowIndex: Int, startingColumnIndex: Int, endingColumnIndex: Int) {
        for (columnIndex in startingColumnIndex + 1 until endingColumnIndex) {
            evaluateMapPosition(Pair(columnIndex, rowIndex))
        }
    }

    private fun evaluateMapPosition(positionToEvaluate: Pair<Int, Int>) {
        if (doesPointExist(positionToEvaluate)) {
            continueSweep = true
            if (tallyMap[positionToEvaluate.second][positionToEvaluate.first] == MonitoringStation.asteroidMarker) {
                val angleToAsteroid = getAngleBetweenPoints(currentPosition, positionToEvaluate)
                outputLogger.debug("Asteroid detected in sweep.  Coordinates: ${positionToEvaluate.first},${positionToEvaluate.second}. Angle: $angleToAsteroid")
                if (pastEncounterVectors.contains(angleToAsteroid)) {
                    tallyMap[positionToEvaluate.second][positionToEvaluate.first] = MonitoringStation.blockedVisibilityMarker
                } else {
                    pastEncounterVectors.add(angleToAsteroid)
                }
            }
        }
    }

    private fun doesPointExist(positionToEvaluate: Pair<Int, Int>): Boolean {
        val isInVertical = tallyMap.size > positionToEvaluate.second && positionToEvaluate.second >= 0
        val isInHorizontal = tallyMap[0].size > positionToEvaluate.first && positionToEvaluate.first >= 0
        return isInVertical && isInHorizontal
    }

    private fun getAngleBetweenPoints(currentPosition: Pair<Int, Int>, positionToEvaluate: Pair<Int, Int>): Double {
        val verticalDifference = (positionToEvaluate.second - currentPosition.second).toDouble()
        val horizontalDifference = (positionToEvaluate.first - currentPosition.first).toDouble()
        return atan2(verticalDifference, horizontalDifference)
    }
}
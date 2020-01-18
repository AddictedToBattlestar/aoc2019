package com.nenaner.aoc2019.day10

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/*
<-. (`-')_             _  (`-') (`-')  _
   \( OO) )     .->    \-.(OO ) ( OO).-/
,--./ ,--/ (`-')----.  _.'    \(,------.
|   \ |  | ( OO).-.  '(_...--'' |  .---'
|  . '|  |)( _) | |  ||  |_.' |(|  '--.
|  |\    |  \|  |)|  ||  .___.' |  .--'
|  | \   |   '  '-'  '|  |      |  `---.
`--'  `--'    `-----' `--'      `------'
 */

@Component
class SpokeScan @Autowired constructor(private val asteroidMapHelper: AsteroidMapHelper) {
    fun calculateTotalNumberOfAsteroidsVisible(asteroidMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>): Int {
        if (asteroidMap[currentPosition.first][currentPosition.second] == MonitoringStation.emptySpaceMarker) return 0
        val tallyMap = asteroidMap.copy()
        tallyMap[currentPosition.first][currentPosition.second] = MonitoringStation.currentPositionMarker
        markAsteroidsBlockedLeft(tallyMap, currentPosition)
        markAsteroidsBlockedRight(tallyMap, currentPosition)
        markAsteroidsBlockedUp(tallyMap, currentPosition)
        markAsteroidsBlockedDown(tallyMap, currentPosition)
        markAsteroidsBlockedUpperLeft(tallyMap, currentPosition)
        markAsteroidsBlockedUpperRight(tallyMap, currentPosition)
        markAsteroidsBlockedLowerLeft(tallyMap, currentPosition)
        markAsteroidsBlockedLowerRight(tallyMap, currentPosition)
        asteroidMapHelper.printAsteroidMap(tallyMap)
        val totalNumberVisible = getAsteroidCount(tallyMap)
        return totalNumberVisible
    }

    private fun getAsteroidCount(tallyMap: Array<Array<Char>>): Int {
        var tally = 0
        for (tallyRow in tallyMap)
            for (coordinateToTally in tallyRow)
                if (coordinateToTally == MonitoringStation.asteroidMarker)
                    tally++
        return tally
    }

    private fun Array<Array<Char>>.copy() = map { it.clone() }.toTypedArray()

    private fun markAsteroidsBlockedDown(tallyMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>) {
        if (currentPosition.first == tallyMap.size - 1) return
        var asteroidFound = false
        for (rowIndex in currentPosition.first + 1 until tallyMap.size) {
            if (asteroidFound) {
                tallyMap[rowIndex][currentPosition.second] = MonitoringStation.blockedVisibilityMarker
            } else {
                if (tallyMap[rowIndex][currentPosition.second] == MonitoringStation.asteroidMarker) {
                    asteroidFound = true
                }
            }
        }
    }

    private fun markAsteroidsBlockedUp(tallyMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>) {
        if (currentPosition.first == 0) return
        var asteroidFound = false
        for (rowIndex in currentPosition.first - 1 downTo 0) {
            if (asteroidFound) {
                tallyMap[rowIndex][currentPosition.second] = MonitoringStation.blockedVisibilityMarker
            } else {
                if (tallyMap[rowIndex][currentPosition.second] == MonitoringStation.asteroidMarker) {
                    asteroidFound = true
                }
            }
        }
    }

    private fun markAsteroidsBlockedLeft(tallyMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>) {
        if (currentPosition.second == 0) return
        var asteroidFound = false
        for (columnIndex in currentPosition.second - 1 downTo 0) {
            if (asteroidFound) {
                tallyMap[currentPosition.first][columnIndex] = MonitoringStation.blockedVisibilityMarker
            } else {
                if (tallyMap[currentPosition.first][columnIndex] == MonitoringStation.asteroidMarker) {
                    asteroidFound = true
                }
            }
        }
    }

    private fun markAsteroidsBlockedRight(tallyMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>) {
        if (currentPosition.second == tallyMap[0].size - 1) return
        var asteroidFound = false
        for (columnIndex in currentPosition.second + 1 until tallyMap[0].size) {
            if (asteroidFound) {
                tallyMap[currentPosition.first][columnIndex] = MonitoringStation.blockedVisibilityMarker
            } else {
                if (tallyMap[currentPosition.first][columnIndex] == MonitoringStation.asteroidMarker) {
                    asteroidFound = true
                }
            }
        }
    }

    private fun markAsteroidsBlockedUpperRight(tallyMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>) {
        if (currentPosition.second == tallyMap[0].size - 1) return
        var asteroidFound = false
        var rowIndex = currentPosition.first - 1
        var columnIndex = currentPosition.second + 1
        while (rowIndex >= 0 && columnIndex < tallyMap[0].size) {
            if (asteroidFound) {
                tallyMap[rowIndex][columnIndex] = MonitoringStation.blockedVisibilityMarker
            } else {
                if (tallyMap[rowIndex][columnIndex] == MonitoringStation.asteroidMarker) {
                    asteroidFound = true
                }
            }
            rowIndex--
            columnIndex++
        }
    }

    private fun markAsteroidsBlockedUpperLeft(tallyMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>) {
        if (currentPosition.second == tallyMap[0].size - 1) return
        var asteroidFound = false
        var rowIndex = currentPosition.first - 1
        var columnIndex = currentPosition.second - 1
        while (rowIndex >= 0 && columnIndex >= 0) {
            if (asteroidFound) {
                tallyMap[rowIndex][columnIndex] = MonitoringStation.blockedVisibilityMarker
            } else {
                if (tallyMap[rowIndex][columnIndex] == MonitoringStation.asteroidMarker) {
                    asteroidFound = true
                }
            }
            rowIndex--
            columnIndex--
        }
    }

    private fun markAsteroidsBlockedLowerRight(tallyMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>) {
        if (currentPosition.second == tallyMap[0].size - 1) return
        var asteroidFound = false
        var rowIndex = currentPosition.first + 1
        var columnIndex = currentPosition.second + 1
        while (rowIndex < tallyMap.size && columnIndex < tallyMap[0].size) {
            if (asteroidFound) {
                tallyMap[rowIndex][columnIndex] = MonitoringStation.blockedVisibilityMarker
            } else {
                if (tallyMap[rowIndex][columnIndex] == MonitoringStation.asteroidMarker) {
                    asteroidFound = true
                }
            }
            rowIndex++
            columnIndex++
        }
    }

    private fun markAsteroidsBlockedLowerLeft(tallyMap: Array<Array<Char>>, currentPosition: Pair<Int, Int>) {
        if (currentPosition.second == tallyMap[0].size - 1) return
        var asteroidFound = false
        var rowIndex = currentPosition.first + 1
        var columnIndex = currentPosition.second - 1
        while (rowIndex < tallyMap.size && columnIndex >= 0) {
            if (asteroidFound) {
                tallyMap[rowIndex][columnIndex] = MonitoringStation.blockedVisibilityMarker
            } else {
                if (tallyMap[rowIndex][columnIndex] == MonitoringStation.asteroidMarker) {
                    asteroidFound = true
                }
            }
            rowIndex++
            columnIndex--
        }
    }
}
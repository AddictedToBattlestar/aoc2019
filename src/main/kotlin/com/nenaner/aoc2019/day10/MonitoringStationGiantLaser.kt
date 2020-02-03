package com.nenaner.aoc2019.day10

import org.springframework.stereotype.Component

@Component
class MonitoringStationGiantLaser {

    fun destroyAllAsteroids(asteroidMap: Array<Array<Char>>): Map<Int, Pair<Int, Int>> {
        val destructionMap = mutableListOf<Array<Int>>()
        val mapOfAnglesAndTheirAsteroids = buildMapOfAnglesAndTheirAsteroids(asteroidMap)
        var totalNumberOfAsteroidsDestroyed = 0
        val asteroidsVaporizedInOrder = mutableMapOf<Int, Pair<Int, Int>>()
        do {
            totalNumberOfAsteroidsDestroyed = asteroidsVaporizedInOrder.size
            sweep (mapOfAnglesAndTheirAsteroids, asteroidsVaporizedInOrder)
        } while (totalNumberOfAsteroidsDestroyed != asteroidsVaporizedInOrder.size)
        return asteroidsVaporizedInOrder.toSortedMap()
    }

    private fun buildMapOfAnglesAndTheirAsteroids(asteroidMap: Array<Array<Char>>): Map<Float, AsteroidsAlongGivenBearing> {
        val mapOfAnglesAndTheirAsteroids = mutableMapOf<Float, AsteroidsAlongGivenBearing>()
        // TODO: need to implement
        return mapOfAnglesAndTheirAsteroids.toSortedMap()
    }

    private fun sweep(mapOfAnglesAndTheirAsteroids: Map<Float, AsteroidsAlongGivenBearing>, asteroidsVaporizedInOrder: MutableMap<Int, Pair<Int, Int>>) {
        // TODO: need to implement
    }
}

private class AsteroidsAlongGivenBearing(val bearing: Double) {
    // distance, x, y, isVaporized
    val asteroidsByDistance = mutableMapOf<Float, Triple<Int, Int, Boolean>>()
}
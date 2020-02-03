package com.nenaner.aoc2019.day10

import org.springframework.stereotype.Component

@Component
class MonitoringStationGiantLaser {

    fun destroyAllAsteroids(asteroidMap: Array<Array<Char>>): Array<Array<Int>> {
        val destructionMap = mutableListOf<Array<Int>>()
        do {
            val asteroidsWereDestroyed = sweep()
        } while (asteroidsWereDestroyed)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun sweep(): Boolean {
        var asteroidsWereDestroyed = false
        return asteroidsWereDestroyed
    }

}
package com.nenaner.aoc2019.day3

import com.nenaner.aoc2019.FileManager
import org.springframework.stereotype.Component
import kotlin.math.absoluteValue

@Component
class CrossedWires(private val fileManager: FileManager) {
    fun findShortestDistanceFromFile() {
        val fileContent = fileManager.readFileAsLinesUsingGetResourceAsStream(fileName).toMutableList()
        val firstWirePath = fileContent[0]
        val secondWirePath = fileContent[1]
        val shortestDistance = findShortestDistance(firstWirePath, secondWirePath)
        println("The shortest distance for where the wires are crossed is: $shortestDistance")
    }

    fun findFewestStepsFromFile() {
        val fileContent = fileManager.readFileAsLinesUsingGetResourceAsStream(fileName).toMutableList()
        val firstWirePath = fileContent[0]
        val secondWirePath = fileContent[1]
        val fewestSteps = findFewestSteps(firstWirePath, secondWirePath)
        println("The fewest steps for where the wires are crossed is: $fewestSteps")
    }

    fun findShortestDistance(firstWirePath: String, secondWirePath: String): Int? {
        val firstWire = getLayoutOfWire(firstWirePath)
        val secondWire = getLayoutOfWire(secondWirePath)
        val intersections = firstWire.getSimpleWireLayout() intersect secondWire.getSimpleWireLayout()
        if (intersections.isEmpty()) {
            return null
        }
        val distances = mutableListOf<Int>()
        intersections.forEach {
            val distance = it.first.absoluteValue + it.second.absoluteValue
            val firstWireSteps = firstWire.findStepsForWirePosition(it)
            val secondWireSteps = secondWire.findStepsForWirePosition(it)
            val sumTotalSteps = secondWireSteps?.let { it1 -> firstWireSteps?.plus(it1) }
            distances.add(distance)
            println("intersection x:${it.first}, y:${it.second}, distance: $distance, sum total steps: $sumTotalSteps, first wire total steps: $firstWireSteps, second wire total steps: $secondWireSteps")
        }
        val shortestDistance = distances.min()
        println("The shortest distance is: $shortestDistance")
        return shortestDistance
    }

    fun findFewestSteps(firstWirePath: String, secondWirePath: String): Int? {
        val firstWire = getLayoutOfWire(firstWirePath)
        val secondWire = getLayoutOfWire(secondWirePath)
        val intersections = firstWire.getSimpleWireLayout() intersect secondWire.getSimpleWireLayout()
        if (intersections.isEmpty()) {
            return null
        }
        val steps = mutableListOf<Int>()
        intersections.forEach {
            val distance = it.first.absoluteValue + it.second.absoluteValue
            val firstWireSteps = firstWire.findStepsForWirePosition(it)
            val secondWireSteps = secondWire.findStepsForWirePosition(it)
            val sumTotalSteps = secondWireSteps?.let { it1 -> firstWireSteps?.plus(it1) }
            if (sumTotalSteps != null) steps.add(sumTotalSteps)
            println("intersection x:${it.first}, y:${it.second}, distance: $distance, sum total steps: $sumTotalSteps, first wire total steps: $firstWireSteps, second wire total steps: $secondWireSteps")
        }
        val fewestSteps = steps.min()
        println("The fewest steps are: $fewestSteps")
        return fewestSteps
    }

    private fun getLayoutOfWire(wirePath: String): WireManager {
        val wirePathArray = wirePath.split(',')
        val wireManager = WireManager()
        wirePathArray.forEach {
            val direction = it.substring(0, 1)
            val distance = it.substring(1).toInt()
            when (direction) {
                "L" -> {
                    wireManager.plotLeft(distance)
                }
                "R" -> {
                    wireManager.plotRight(distance)
                }
                "D" -> {
                    wireManager.plotDown(distance)
                }
                "U" -> {
                    wireManager.plotUp(distance)
                }
            }
        }
        return wireManager
    }

    companion object {
        const val fileName = "day3.crossedWires.in"
    }
}

internal class WireManager {
    var wireLayout = mutableSetOf<Triple<Int, Int, Int>>()
    var currentPosition = Position(0, 0)
    var totalSteps = 0

    fun plotLeft(distance: Int) {
        for (i in 1..distance) {
            val wireLocation = Triple(currentPosition.x - i, currentPosition.y, ++totalSteps)
            wireLayout.add(wireLocation)
        }
        currentPosition.x = currentPosition.x - distance
    }

    fun plotRight(distance: Int) {
        for (i in 1..distance) {
            val wireLocation = Triple(currentPosition.x + i, currentPosition.y, ++totalSteps)
            wireLayout.add(wireLocation)
        }
        currentPosition.x = currentPosition.x + distance
    }

    fun plotDown(distance: Int) {
        for (i in 1..distance) {
            val wireLocation = Triple(currentPosition.x, currentPosition.y - i, ++totalSteps)
            wireLayout.add(wireLocation)
        }
        currentPosition.y = currentPosition.y - distance
    }

    fun plotUp(distance: Int) {
        for (i in 1..distance) {
            val wireLocation = Triple(currentPosition.x, currentPosition.y + i, ++totalSteps)
            wireLayout.add(wireLocation)
        }
        currentPosition.y = currentPosition.y + distance
    }

    fun getSimpleWireLayout(): Set<Pair<Int, Int>> {
        val result = mutableSetOf<Pair<Int, Int>>()
        wireLayout.forEach{
            result.add(Pair(it.first, it.second))
        }
        return result
    }

    fun findStepsForWirePosition(wirePosition: Pair<Int, Int>): Int? {
        return wireLayout.firstOrNull { it.first == wirePosition.first && it.second == wirePosition.second }?.third
    }
}

internal class Position(var x: Int, var y: Int)
package com.nenaner.aoc2019.day2

import com.nenaner.aoc2019.FileManager
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class ProgramAlarm(private val fileManager: FileManager) {
    fun processIntCodeFromFileForDesirableOutcome(desiredValue: Int): Pair<Int?, Int?> {
        val fileContent = fileManager.readFileAsLinesUsingGetResourceAsStream(fileNameContainingIntCodes).toMutableList()
        var intCodes = fileContent[0].split(',').map { it.toInt() }.toMutableList()
        for (firstValue in 1..intCodes.size) {
            for (secondValue in 1..intCodes.size) {
                intCodes[1] = firstValue
                intCodes[2] = secondValue
                try {
                    val result = processIntCodeArray(intCodes)
                    println("attempted with the first value of $firstValue and the second value of $secondValue.  Result: $result")
                    if (result[0] == desiredValue) {
                        return Pair(firstValue, secondValue)
                    }
                }
                catch (ex: Exception) {
                    println("failed to process intCode pairing: $firstValue, $secondValue. Exception: ${ex.message}")
                }
                intCodes = fileContent[0].split(',').map { it.toInt() }.toMutableList()
            }
        }
        return Pair(null, null)
    }

    fun processIntCodeFromFile(firstValue: Int, secondValue: Int): MutableList<Int> {
        val fileContent = fileManager.readFileAsLinesUsingGetResourceAsStream(fileNameContainingIntCodes).toMutableList()
        val intCodes = fileContent[0].split(',').map { it.toInt() }.toMutableList()
        intCodes[1] = firstValue
        intCodes[2] = secondValue
        return processIntCodeArray(intCodes)
    }

    fun processIntCode(intCode: String): String {
        val intCodeArray = intCode.split(',').map { it.toInt() }.toMutableList()
        return processIntCodeArray(intCodeArray).joinToString().replace(" ", "")
    }

    fun processIntCodeArray(intCodeArray: MutableList<Int>): MutableList<Int> {
        var instructionPointer = 0
        while (instructionPointer < intCodeArray.size - 1 && TERMINATION_OP_CODE != intCodeArray[instructionPointer]) {
            val (location1, location2, locationForTheResult) = getLocationsOfOperation(intCodeArray, instructionPointer)
            processOpCodeOperation(intCodeArray, instructionPointer, locationForTheResult, location1, location2)
            instructionPointer += 4
        }
        return intCodeArray
    }

    private fun getLocationsOfOperation(intCodeArray: MutableList<Int>, instructionPointer: Int): Triple<Int, Int, Int> {
        val location1 = intCodeArray[instructionPointer + 1]
        val location2 = intCodeArray[instructionPointer + 2]
        val locationForTheResult = intCodeArray[instructionPointer + 3]
        return Triple(location1, location2, locationForTheResult)
    }

    private fun processOpCodeOperation(intCodeArray: MutableList<Int>, instructionPointer: Int, locationForTheResult: Int, location1: Int, location2: Int) {
        when {
            ADDITION_OP_CODE == intCodeArray[instructionPointer] -> {
                intCodeArray[locationForTheResult] = intCodeArray[location1] + intCodeArray[location2]
            }
            MULTIPLICATION_OP_CODE == intCodeArray[instructionPointer] -> {
                intCodeArray[locationForTheResult] = intCodeArray[location1] * intCodeArray[location2]
            }
            else -> {
                throw Exception("Invalid opCodeProvided: ${intCodeArray[instructionPointer]}, position $instructionPointer")
            }
        }
    }

    companion object {
        const val ADDITION_OP_CODE = 1
        const val MULTIPLICATION_OP_CODE = 2
        const val TERMINATION_OP_CODE = 99
        const val fileNameContainingIntCodes = "day2.intCodes.in"
    }
}
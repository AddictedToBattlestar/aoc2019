package com.nenaner.aoc2019.day5

import com.nenaner.aoc2019.OutputLogger
import org.springframework.stereotype.Component
import java.util.*

@Component
class DiagnosticProgram(private val outputLogger: OutputLogger) {
    fun processOpCode(intCode: String): String {
        val intCodeArray = intCode.split(',').map { it.toInt() }.toMutableList()
        return processIntCodeArray(intCodeArray, null).joinToString().replace(" ", "")
    }

    fun processInitialInput(intCode: String, initialInput: String): String {
        val intCodeArray = intCode.split(',').map { it.toInt() }.toMutableList()
        return processIntCodeArray(intCodeArray, initialInput).joinToString().replace(" ", "")
    }

    private fun processIntCodeArray(intCodeArray: MutableList<Int>, initialInput: String?): MutableList<Int> {
        var instructionPointer = 0
        if (initialInput != null) {
            instructionPointer = processInitialInput(intCodeArray, instructionPointer, initialInput)
        }
        while (instructionPointer < intCodeArray.size - 1 && OpCodes.TERMINATION.value != intCodeArray[instructionPointer]) {
            instructionPointer = processOpCode(intCodeArray, instructionPointer)
        }
        return intCodeArray
    }

    private fun processInitialInput(intCodeArray: MutableList<Int>, instructionPointer: Int, initialInput: String): Int {
        if (intCodeArray[instructionPointer] != OpCodes.INPUT.value) {
            outputLogger.debug("The input provided was not used as the intCodes being processed did not require an input field")
            return instructionPointer + 0
        }
        val locationToSaveInitialInput = intCodeArray[instructionPointer + 1]
        val initialInputAsInt = initialInput.toIntOrNull()
                ?: throw InputMismatchException("The input of $initialInput was not a numeric value")
        intCodeArray[locationToSaveInitialInput] = initialInputAsInt
        return instructionPointer + 2
    }

    private fun processOpCode(intCodeArray: MutableList<Int>, instructionPointer: Int): Int {
        val (opCode, firstParameterMode, secondParameterMode) = getOpCodeAndParameters(intCodeArray[instructionPointer])
        when (opCode) {
            OpCodes.ADDITION.value -> {
                val (value1, value2, valueResult) = getValuesOf3InstructionOperation(intCodeArray, instructionPointer)
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeArray[value2]
                val result = firstNumberOfOperation + secondNumberOfOperation
                intCodeArray[valueResult] = result
                return instructionPointer + 4
            }
            OpCodes.MULTIPLICATION.value -> {
                val (value1, value2, valueResult) = getValuesOf3InstructionOperation(intCodeArray, instructionPointer)
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeArray[value2]
                val result = firstNumberOfOperation * secondNumberOfOperation
                intCodeArray[valueResult] = result
                return instructionPointer + 4
            }
            OpCodes.OUTPUT.value -> {
                val location1 = intCodeArray[instructionPointer + 1]
                val outputValue = intCodeArray[location1]
                outputLogger.info(outputValue.toString())
                return instructionPointer + 2
            }
            OpCodes.JUMP_IF_TRUE.value -> {
                val (value1, value2) = getValuesOf2InstructionOperation(intCodeArray, instructionPointer)
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeArray[value2]
                return if (firstNumberOfOperation != 0) secondNumberOfOperation else instructionPointer + 3
            }
            OpCodes.JUMP_IF_FALSE.value -> {
                val (value1, value2) = getValuesOf2InstructionOperation(intCodeArray, instructionPointer)
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeArray[value2]
                return if (firstNumberOfOperation == 0) secondNumberOfOperation else instructionPointer + 3
            }
            OpCodes.LESS_THAN.value -> {
                val (value1, value2, valueResult) = getValuesOf3InstructionOperation(intCodeArray, instructionPointer)
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeArray[value2]
                intCodeArray[valueResult] = if (firstNumberOfOperation < secondNumberOfOperation) 1 else 0
                return instructionPointer + 4
            }
            OpCodes.EQUALS.value -> {
                val (value1, value2, valueResult) = getValuesOf3InstructionOperation(intCodeArray, instructionPointer)
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeArray[value2]
                intCodeArray[valueResult] = if (firstNumberOfOperation == secondNumberOfOperation) 1 else 0
                return instructionPointer + 4
            }
            else -> {
                throw Exception("Invalid opCodeProvided: ${intCodeArray[instructionPointer]}, position $instructionPointer")
            }
        }
    }

    private fun getOpCodeAndParameters(opCodeWithParameterModes: Int): Triple<Int, Int, Int> {
        val secondParameterMode = opCodeWithParameterModes / 1000
        val firstParameterMode = opCodeWithParameterModes / 100 % 10
        val opCode = opCodeWithParameterModes % 100
        return Triple(opCode, firstParameterMode, secondParameterMode)
    }

    private fun getValuesOf3InstructionOperation(intCodeArray: MutableList<Int>, instructionPointer: Int): Triple<Int, Int, Int> {
        val value1 = intCodeArray[instructionPointer + 1]
        val value2 = intCodeArray[instructionPointer + 2]
        val valueResult = intCodeArray[instructionPointer + 3]
        return Triple(value1, value2, valueResult)
    }

    private fun getValuesOf2InstructionOperation(intCodeArray: MutableList<Int>, instructionPointer: Int): Pair<Int, Int> {
        val value1 = intCodeArray[instructionPointer + 1]
        val value2 = intCodeArray[instructionPointer + 2]
        return Pair(value1, value2)
    }

    companion object {
        const val IMMEDIATE_MODE = 1
        const val POSITION_MODE = 0

        enum class OpCodes(val value: Int) {
            ADDITION(1),
            MULTIPLICATION(2),
            INPUT(3),
            OUTPUT(4),
            JUMP_IF_TRUE(5),
            JUMP_IF_FALSE(6),
            LESS_THAN(7),
            EQUALS(8),
            TERMINATION(99)
        }
    }
}

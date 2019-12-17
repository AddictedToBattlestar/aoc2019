package com.nenaner.aoc2019

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class IntCodeProcessor @Autowired constructor(private val outputLogger: OutputLogger) {
    fun processIntCode(intCode: String): String? {
        val intCodeProcessingState = IntCodeProcessingState(mutableListOf(), intCode)
        this.processIntCode(intCodeProcessingState)
        return intCodeProcessingState.getIntCodeAsString()
    }

    fun processInitialInputAgainstProvidedIntCode(intCode: String, initialInput: String?): IntCodeProcessingState {
        val initialInputs = if (initialInput == null) mutableListOf() else mutableListOf(initialInput.toInt())
        val intCodeProcessingState = IntCodeProcessingState(initialInputs, intCode)
        this.processIntCode(intCodeProcessingState)
        return intCodeProcessingState
    }

    fun processIntCode(intCodeProcessingState: IntCodeProcessingState) {
        intCodeProcessingState.outputEncountered = false
        while (!intCodeProcessingState.hasInstructionPointerGoneOutOfBounds()
                && !intCodeProcessingState.terminationEncountered
                && !intCodeProcessingState.outputEncountered) {
            processOpCode(intCodeProcessingState)
        }
    }

    private fun processOpCode(intCodeProcessingState: IntCodeProcessingState) {
        val (opCode, firstParameterMode, secondParameterMode) = getOpCodeAndParameters(intCodeProcessingState.getCurrentInstruction())
        when (opCode) {
            OpCodes.INPUT.value -> {
                val locationToSaveInput = intCodeProcessingState.getValuesOfSingleInstructionOperation()
//                val inputValue = getInputValue()
                val inputValue = intCodeProcessingState.popInputValue()
                if (inputValue == null) {
                    throw IntCodeInputProcessingException("Input opCode encountered with no input received.")
                } else {
                    outputLogger.info("Input opCode encountered.  Automatically inputting $inputValue.")
                    intCodeProcessingState.intCodeArray[locationToSaveInput] = inputValue
                }
                intCodeProcessingState.moveInstructionPointer(2)
            }
            OpCodes.OUTPUT.value -> {
                val location1 = intCodeProcessingState.getValuesOfSingleInstructionOperation()
                val outputValue = intCodeProcessingState.intCodeArray[location1]
                intCodeProcessingState.outputValues.add(outputValue)
                outputLogger.debug("Output opCode encountered.  The value found to output is: $outputValue")
                intCodeProcessingState.moveInstructionPointer(2)
                intCodeProcessingState.outputEncountered = true
            }
            OpCodes.ADDITION.value -> {
                val (value1, value2, valueResult) = intCodeProcessingState.getValuesOfThreeInstructionOperation()
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeProcessingState.intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeProcessingState.intCodeArray[value2]
                val result = firstNumberOfOperation + secondNumberOfOperation
                intCodeProcessingState.intCodeArray[valueResult] = result
                intCodeProcessingState.moveInstructionPointer(4)
            }
            OpCodes.MULTIPLICATION.value -> {
                val (value1, value2, valueResult) = intCodeProcessingState.getValuesOfThreeInstructionOperation()
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeProcessingState.intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeProcessingState.intCodeArray[value2]
                val result = firstNumberOfOperation * secondNumberOfOperation
                intCodeProcessingState.intCodeArray[valueResult] = result
                intCodeProcessingState.moveInstructionPointer(4)
            }
            OpCodes.LESS_THAN.value -> {
                val (value1, value2, valueResult) = intCodeProcessingState.getValuesOfThreeInstructionOperation()
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeProcessingState.intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeProcessingState.intCodeArray[value2]
                intCodeProcessingState.intCodeArray[valueResult] = if (firstNumberOfOperation < secondNumberOfOperation) 1 else 0
                intCodeProcessingState.moveInstructionPointer(4)
            }
            OpCodes.EQUALS.value -> {
                val (value1, value2, valueResult) = intCodeProcessingState.getValuesOfThreeInstructionOperation()
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeProcessingState.intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeProcessingState.intCodeArray[value2]
                intCodeProcessingState.intCodeArray[valueResult] = if (firstNumberOfOperation == secondNumberOfOperation) 1 else 0
                intCodeProcessingState.moveInstructionPointer(4)
            }
            OpCodes.JUMP_IF_TRUE.value -> {
                val (value1, value2) = intCodeProcessingState.getValuesOfTwoInstructionOperation()
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeProcessingState.intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeProcessingState.intCodeArray[value2]
                if (firstNumberOfOperation != 0)
                    intCodeProcessingState.jumpInstructionPointer(secondNumberOfOperation)
                else
                    intCodeProcessingState.moveInstructionPointer(3)
            }
            OpCodes.JUMP_IF_FALSE.value -> {
                val (value1, value2) = intCodeProcessingState.getValuesOfTwoInstructionOperation()
                val firstNumberOfOperation = if (firstParameterMode == IMMEDIATE_MODE) value1 else intCodeProcessingState.intCodeArray[value1]
                val secondNumberOfOperation = if (secondParameterMode == IMMEDIATE_MODE) value2 else intCodeProcessingState.intCodeArray[value2]
                if (firstNumberOfOperation == 0)
                    intCodeProcessingState.jumpInstructionPointer(secondNumberOfOperation)
                else
                    intCodeProcessingState.moveInstructionPointer(3)
            }
            OpCodes.TERMINATION.value -> {
                outputLogger.info("Termination opCode encountered.  Halting intCode processing.")
                intCodeProcessingState.terminationEncountered = true
            }
            else -> {
                throw Exception("Invalid opCodeProvided: $opCode, position ${intCodeProcessingState.instructionPointer}")
            }
        }
    }

    private fun getInputValue(): Int {
        println("A numeric input is needed to process this intCode data: ")
        val enteredString = readLine()
        return enteredString?.toIntOrNull()
                ?: throw InputMismatchException("The input of $enteredString was not a numeric value")
    }

    private fun getOpCodeAndParameters(opCodeWithParameterModes: Int): Triple<Int, Int, Int> {
        val secondParameterMode = opCodeWithParameterModes / 1000
        val firstParameterMode = opCodeWithParameterModes / 100 % 10
        val opCode = opCodeWithParameterModes % 100
        return Triple(opCode, firstParameterMode, secondParameterMode)
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

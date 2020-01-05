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
        val initialInputs = if (initialInput == null) mutableListOf() else mutableListOf(initialInput.toLong())
        val intCodeProcessingState = IntCodeProcessingState(initialInputs, intCode)
        this.processIntCode(intCodeProcessingState)
        return intCodeProcessingState
    }

    fun processIntCode(intCodeProcessingState: IntCodeProcessingState) {
        intCodeProcessingState.outputEncountered = false
        while (!intCodeProcessingState.haltStateEncountered()) {
            processOpCode(intCodeProcessingState)
        }
    }

    private fun processOpCode(intCodeProcessingState: IntCodeProcessingState) {
        val opCodeWithParameterModes = OpCodeWithParameterModes.generateOpCodeWithParameterModes(intCodeProcessingState.getCurrentInstruction())
        when (opCodeWithParameterModes.opCode) {
            OpCode.ADDITION -> {
                val (value1, value2, valueResult) = intCodeProcessingState.getValuesOfThreeInstructionOperation()
                val firstNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.firstParameterMode, value1, intCodeProcessingState)
                val secondNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.secondParameterMode, value2, intCodeProcessingState)
                val locationToSaveResult = getLocationForResult(opCodeWithParameterModes.thirdParameterMode, valueResult, intCodeProcessingState)
                val result = firstNumberOfOperation + secondNumberOfOperation
                intCodeProcessingState.setInstruction(locationToSaveResult, result)
                intCodeProcessingState.moveInstructionPointer(4)
            }
            OpCode.MULTIPLICATION -> {
                val (value1, value2, valueResult) = intCodeProcessingState.getValuesOfThreeInstructionOperation()
                val firstNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.firstParameterMode, value1, intCodeProcessingState)
                val secondNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.secondParameterMode, value2, intCodeProcessingState)
                val locationToSaveResult = getLocationForResult(opCodeWithParameterModes.thirdParameterMode, valueResult, intCodeProcessingState)
                val result = firstNumberOfOperation * secondNumberOfOperation
                intCodeProcessingState.setInstruction(locationToSaveResult, result)
                intCodeProcessingState.moveInstructionPointer(4)
            }
            OpCode.INPUT -> {
                val valueResult = intCodeProcessingState.getValuesOfSingleInstructionOperation()
                val locationToSaveInput = getLocationForResult(opCodeWithParameterModes.firstParameterMode, valueResult, intCodeProcessingState)
                //                val inputValue = getInputValue()
                val inputValue = intCodeProcessingState.popInputValue()
                if (inputValue == null) {
                    throw IntCodeInputProcessingException("Input opCode encountered with no input received.")
                } else {
                    outputLogger.info("Input opCode encountered.  Automatically inputting $inputValue.")
                    intCodeProcessingState.setInstruction(locationToSaveInput, inputValue)
                }
                intCodeProcessingState.moveInstructionPointer(2)
            }
            OpCode.OUTPUT -> {
                val value1 = intCodeProcessingState.getValuesOfSingleInstructionOperation()
                val outputValue = getValueFromParameter(opCodeWithParameterModes.firstParameterMode, value1, intCodeProcessingState)
                intCodeProcessingState.outputValues.add(outputValue)
                outputLogger.debug("Output opCode encountered.  The value found to output is: $outputValue")
                intCodeProcessingState.moveInstructionPointer(2)
                intCodeProcessingState.outputEncountered = true
            }
            OpCode.JUMP_IF_TRUE -> {
                val (value1, value2) = intCodeProcessingState.getValuesOfTwoInstructionOperation()
                val firstNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.firstParameterMode, value1, intCodeProcessingState)
                val secondNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.secondParameterMode, value2, intCodeProcessingState)
                if (firstNumberOfOperation != 0L)
                    intCodeProcessingState.jumpInstructionPointer(secondNumberOfOperation)
                else
                    intCodeProcessingState.moveInstructionPointer(3)
            }
            OpCode.JUMP_IF_FALSE -> {
                val (value1, value2) = intCodeProcessingState.getValuesOfTwoInstructionOperation()
                val firstNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.firstParameterMode, value1, intCodeProcessingState)
                val secondNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.secondParameterMode, value2, intCodeProcessingState)
                if (firstNumberOfOperation == 0L)
                    intCodeProcessingState.jumpInstructionPointer(secondNumberOfOperation)
                else
                    intCodeProcessingState.moveInstructionPointer(3)
            }
            OpCode.LESS_THAN -> {
                val (value1, value2, valueResult) = intCodeProcessingState.getValuesOfThreeInstructionOperation()
                val firstNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.firstParameterMode, value1, intCodeProcessingState)
                val secondNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.secondParameterMode, value2, intCodeProcessingState)
                val locationToSaveResult = getLocationForResult(opCodeWithParameterModes.thirdParameterMode, valueResult, intCodeProcessingState)
                intCodeProcessingState.setInstruction(locationToSaveResult, if (firstNumberOfOperation < secondNumberOfOperation) 1 else 0)
                intCodeProcessingState.moveInstructionPointer(4)
            }
            OpCode.EQUALS -> {
                val (value1, value2, valueResult) = intCodeProcessingState.getValuesOfThreeInstructionOperation()
                val firstNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.firstParameterMode, value1, intCodeProcessingState)
                val secondNumberOfOperation = getValueFromParameter(opCodeWithParameterModes.secondParameterMode, value2, intCodeProcessingState)
                val locationToSaveResult = getLocationForResult(opCodeWithParameterModes.thirdParameterMode, valueResult, intCodeProcessingState)

                intCodeProcessingState.setInstruction(locationToSaveResult, if (firstNumberOfOperation == secondNumberOfOperation) 1 else 0)
                intCodeProcessingState.moveInstructionPointer(4)
            }
            OpCode.RELATIVE_BASE_ADJUSTMENT -> {
                val value1 = intCodeProcessingState.getValuesOfSingleInstructionOperation()
                val offsetValue = getValueFromParameter(opCodeWithParameterModes.firstParameterMode, value1, intCodeProcessingState)
                intCodeProcessingState.adjustBaseOffset(offsetValue)
                intCodeProcessingState.moveInstructionPointer(2)
            }
            OpCode.TERMINATION -> {
                outputLogger.info("Termination opCode encountered.  Halting intCode processing.")
                intCodeProcessingState.terminationEncountered = true
            }
        }
    }

    private fun getLocationForResult(parameterMode: ParameterMode, value1: Long, intCodeProcessingState: IntCodeProcessingState): Long {
        return when (parameterMode) {
            ParameterMode.RELATIVE_MODE -> value1 + intCodeProcessingState.relativeBase
            ParameterMode.POSITION_MODE -> value1
            else -> {
                throw Exception("Invalid parameter mode provided.  parameterMode: $parameterMode")
            }
        }
    }

    private fun getValueFromParameter(parameterMode: ParameterMode, value: Long, intCodeProcessingState: IntCodeProcessingState): Long {
        return when (parameterMode) {
            ParameterMode.RELATIVE_MODE -> intCodeProcessingState.getInstruction(value + intCodeProcessingState.relativeBase)
            ParameterMode.POSITION_MODE -> intCodeProcessingState.getInstruction(value)
            ParameterMode.IMMEDIATE_MODE -> value
        }
    }

    private fun getInputValue(): Int {
        println("A numeric input is needed to process this intCode data: ")
        val enteredString = readLine()
        return enteredString?.toIntOrNull()
                ?: throw InputMismatchException("The input of $enteredString was not a numeric value")
    }
}

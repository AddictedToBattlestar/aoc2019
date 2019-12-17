package com.nenaner.aoc2019

class IntCodeProcessingState(var inputValues: MutableList<Int>) {
    var outputValues = mutableListOf<Int>()
    var intCodeArray = mutableListOf<Int>()
    var instructionPointer: Int = 0
    var terminationEncountered: Boolean = false
    var outputEncountered: Boolean = false

    constructor(inputValues: MutableList<Int>,
                originalIntCodeAsString: String) : this(inputValues) {
        intCodeArray = originalIntCodeAsString.split(',').map { it.toInt() }.toMutableList()
    }

    fun resetEverything() {
        inputValues = mutableListOf()
        outputValues = mutableListOf()
        intCodeArray = mutableListOf()
        instructionPointer = 0
        terminationEncountered = false
        outputEncountered = false
    }

    fun resetIntCode(intCodeAsString: String) {
        intCodeArray = intCodeAsString.split(',').map { it.toInt() }.toMutableList()
    }

    fun pushInputValue(inputValue: Int) {
        inputValues.add(inputValue)
    }

    fun popInputValue(): Int? {
        if (inputValues.isEmpty()) return null
        val inputValue = inputValues[0]
        inputValues.removeAt(0)
        return inputValue
    }

    fun moveInstructionPointer(instructionOffset: Int) {
        instructionPointer += instructionOffset
    }

    fun jumpInstructionPointer(newInstructionPointerLocation: Int) {
        instructionPointer = newInstructionPointerLocation
    }

    fun getIntCodeAsString(): String {
        return intCodeArray.joinToString().replace(" ", "")
    }

    fun getCurrentInstruction(): Int {
        return intCodeArray[instructionPointer]
    }

    fun getLastOutputValue(): Int {
        return outputValues.last()
    }

    fun getValuesOfThreeInstructionOperation(): Triple<Int, Int, Int> {
        val value1 = intCodeArray[instructionPointer + 1]
        val value2 = intCodeArray[instructionPointer + 2]
        val valueResult = intCodeArray[instructionPointer + 3]
        return Triple(value1, value2, valueResult)
    }

    fun getValuesOfTwoInstructionOperation(): Pair<Int, Int> {
        val value1 = intCodeArray[instructionPointer + 1]
        val value2 = intCodeArray[instructionPointer + 2]
        return Pair(value1, value2)
    }

    fun getValuesOfSingleInstructionOperation(): Int {
        return intCodeArray[instructionPointer + 1]
    }

    fun hasInstructionPointerGoneOutOfBounds(): Boolean {
        return instructionPointer >= intCodeArray.size
    }
}

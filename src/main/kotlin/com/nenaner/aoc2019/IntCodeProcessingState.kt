package com.nenaner.aoc2019

class IntCodeProcessingState(var inputValues: MutableList<Long>) {
    var intCodeMap = mutableMapOf<Long, Long>()
    var outputValues = mutableListOf<Long>()
    var instructionPointer: Long = 0
    var terminationEncountered: Boolean = false
    var outputEncountered: Boolean = false
    var haltOnWhenOutputEncountered: Boolean = false
    var relativeBase: Long = 0

    constructor(inputValues: MutableList<Long>,
                originalIntCodeAsString: String) : this(inputValues) {
        resetIntCode(originalIntCodeAsString)
    }

    fun resetEverything() {
        inputValues = mutableListOf()
        outputValues = mutableListOf()
        intCodeMap = mutableMapOf()
        instructionPointer = 0
        terminationEncountered = false
        outputEncountered = false
        relativeBase = 0
    }

    fun resetIntCode(intCodeAsString: String) {
        intCodeMap = mutableMapOf()
        val intCodeList = intCodeAsString.split(',').map { it.toLong() }.toMutableList()
        for ((index, intCode) in intCodeList.withIndex()) {
            intCodeMap[index.toLong()] = intCode
        }
    }
    
    fun haltStateEncountered(): Boolean {
        return !intCodeMap.containsKey(instructionPointer)
                || terminationEncountered
                || (haltOnWhenOutputEncountered && outputEncountered)
    }

    fun pushInputValue(inputValue: Long) {
        inputValues.add(inputValue)
    }

    fun popInputValue(): Long? {
        if (inputValues.isEmpty()) return null
        val inputValue = inputValues[0]
        inputValues.removeAt(0)
        return inputValue
    }

    fun moveInstructionPointer(instructionOffset: Long) {
        if (instructionPointer + instructionOffset < 0) throw Exception("The new instruction offset location provided is not valid. instructionPointer: $instructionPointer, instructionOffset: $instructionOffset")
        instructionPointer += instructionOffset
    }

    fun jumpInstructionPointer(newInstructionPointerLocation: Long) {
        if (newInstructionPointerLocation < 0) throw Exception("The new instruction pointer location provided is not valid. newInstructionPointerLocation: $newInstructionPointerLocation")
        instructionPointer = newInstructionPointerLocation
    }

    fun getIntCodeAsString(): String {
        val intCodeArray = mutableListOf<Long>()
        intCodeMap.forEach { (_, value) ->
            intCodeArray.add(value)
        }
        return intCodeArray.joinToString().replace(" ", "")
    }

    fun getCurrentInstruction(): Long {
        if (instructionPointer < 0) throw Exception("Instruction invalid as the locations of the operation provided are not valid. instructionPointer: $instructionPointer")
        return getInstruction(instructionPointer)
    }

    fun getInstruction(location: Long): Long {
        if (location < 0) throw Exception("Instruction invalid as the locations of the operation provided are not valid. location: $location")
        return if (intCodeMap.containsKey(location)) {
            intCodeMap[location]!!
        } else {
            intCodeMap[location] = 0
            0
        }
    }

    fun setInstruction(location: Long, value: Long) {
        intCodeMap[location] = value
    }

    fun getLastOutputValue(): Long {
        return outputValues.last()
    }

    fun getValuesOfThreeInstructionOperation(): Triple<Long, Long, Long> {
        val value1 = getInstruction(instructionPointer + 1)
        val value2 = getInstruction(instructionPointer + 2)
        val valueResult = getInstruction(instructionPointer + 3)
        return Triple(value1, value2, valueResult)
    }

    fun getValuesOfTwoInstructionOperation(): Pair<Long, Long> {
        val value1 = getInstruction(instructionPointer + 1)
        val value2 = getInstruction(instructionPointer + 2)
        return Pair(value1, value2)
    }

    fun getValuesOfSingleInstructionOperation(): Long {
        return getInstruction(instructionPointer + 1)
    }

    fun adjustBaseOffset(offsetValue: Long) {
        relativeBase += offsetValue
    }
}

package com.nenaner.aoc2019.day7

import com.nenaner.aoc2019.IntCodeInputProcessingException
import com.nenaner.aoc2019.IntCodeProcessingState
import com.nenaner.aoc2019.OutputLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AmplifierArray @Autowired constructor(private var outputLogger: OutputLogger,
                                            private val amplifierA: Amplifier,
                                            private val amplifierB: Amplifier,
                                            private val amplifierC: Amplifier,
                                            private val amplifierD: Amplifier,
                                            private val amplifierE: Amplifier) {

    fun initializeAmplifiers(initialPhaseSettingSequence: List<Int>, intCodeData: String) {
        amplifierA.initializeAmplifier("Amplifier A", initialPhaseSettingSequence[0], intCodeData)
        amplifierB.initializeAmplifier("Amplifier B", initialPhaseSettingSequence[1], intCodeData)
        amplifierC.initializeAmplifier("Amplifier C", initialPhaseSettingSequence[2], intCodeData)
        amplifierD.initializeAmplifier("Amplifier D", initialPhaseSettingSequence[3], intCodeData)
        amplifierE.initializeAmplifier("Amplifier E", initialPhaseSettingSequence[4], intCodeData)
    }

    fun initializeAmplifiers(initialPhaseSettingSequence: List<Int>) {
        amplifierA.initializeAmplifier("Amplifier A", initialPhaseSettingSequence[0], null)
        amplifierB.initializeAmplifier("Amplifier B", initialPhaseSettingSequence[1], null)
        amplifierC.initializeAmplifier("Amplifier C", initialPhaseSettingSequence[2], null)
        amplifierD.initializeAmplifier("Amplifier D", initialPhaseSettingSequence[3], null)
        amplifierE.initializeAmplifier("Amplifier E", initialPhaseSettingSequence[4], null)
    }

    fun processPhaseSettingSequence(): Int? {
        var outputFromAmplifier = 0
        for (amplifier in listOf(amplifierA, amplifierB, amplifierC, amplifierD, amplifierE)) {
            outputFromAmplifier = amplifier.processIntCode(outputFromAmplifier)
        }
        outputLogger.info("Processing amplifier E produced the output of: $outputFromAmplifier")
        return outputFromAmplifier
    }

    fun processPhaseSettingSequenceWithFeedbackLoop(): Int? {
        var intCodeProcessingState: IntCodeProcessingState?
        var intCodeProcessingStateAmplifierE: IntCodeProcessingState? = null
        var outputFromAmplifier = 0
        try {
            var continueProcessing = true
            while (continueProcessing) {
                for (amplifier in listOf(amplifierA, amplifierB, amplifierC, amplifierD, amplifierE)) {
                    intCodeProcessingState = amplifier.processIntCodeReturningState(outputFromAmplifier)
                    if (amplifier.identifier == "Amplifier E") intCodeProcessingStateAmplifierE = intCodeProcessingState
                    if (intCodeProcessingState.terminationEncountered) {
                        continueProcessing = false
                        break
                    }
                    outputFromAmplifier = intCodeProcessingState.getLastOutputValue()
                }
            }
        } catch (e: IntCodeInputProcessingException) {
            outputLogger.error("The provided phase sequence is invalid for current intCode as the amplifier is requiring more input that allowed")
            return null
        }

        outputLogger.info("Processing amplifier E produced the output of: ${intCodeProcessingStateAmplifierE?.getLastOutputValue()}")
        return intCodeProcessingStateAmplifierE?.getLastOutputValue()
    }
}
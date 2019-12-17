package com.nenaner.aoc2019.day7

import com.nenaner.aoc2019.FileManager
import com.nenaner.aoc2019.IntCodeProcessingState
import com.nenaner.aoc2019.IntCodeProcessor
import com.nenaner.aoc2019.OutputLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class Amplifier @Autowired constructor(private var intCodeProcessor: IntCodeProcessor,
                                       private var outputLogger: OutputLogger,
                                       var fileManager: FileManager) {
    private var intCodeProcessingState: IntCodeProcessingState = IntCodeProcessingState(mutableListOf())
    var identifier = "Generic amplifier"

    fun initializeAmplifier(identifier: String, initialPhaseSetting: Int, customIntCode: String?) {
        this.identifier = identifier
        intCodeProcessingState.resetEverything()
        intCodeProcessingState.pushInputValue(initialPhaseSetting)
        if (customIntCode == null) {
            val defaultIntCodeFromFile = fileManager.readFileAsLinesUsingGetResourceAsStream(fileNameContainingIntCodes).toMutableList()[0]
            intCodeProcessingState.resetIntCode(defaultIntCodeFromFile)
            outputLogger.info("$identifier - initialized, initialPhaseSetting: $initialPhaseSetting, intCode initialized with default content from file")
        } else {
            intCodeProcessingState.resetIntCode(customIntCode)
            outputLogger.info("$identifier - initialized, initialPhaseSetting: $initialPhaseSetting, intCode initialized with custom contenct from file")
        }
    }

    fun processIntCode(inputValue: Int?): Int {
        return processIntCodeReturningState(inputValue).getLastOutputValue()
    }

    fun processIntCodeReturningState(inputValue: Int?): IntCodeProcessingState {
        if (inputValue != null) {
            outputLogger.info("$identifier - inputValue: $inputValue")
            intCodeProcessingState.pushInputValue(inputValue)
        } else {
            outputLogger.info("$identifier - no input provided, proceeding regardless")
        }
        intCodeProcessor.processIntCode(intCodeProcessingState)
        outputLogger.info("$identifier - The resulting internal intCode: ${intCodeProcessingState.getIntCodeAsString()}")
        outputLogger.info("$identifier - The last output generated: ${intCodeProcessingState.getLastOutputValue()}")
        return intCodeProcessingState
    }

    companion object {
        const val fileNameContainingIntCodes = "day7.amplifier.in"
    }
}

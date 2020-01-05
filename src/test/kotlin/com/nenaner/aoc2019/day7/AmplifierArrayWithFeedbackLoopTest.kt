package com.nenaner.aoc2019.day7

import com.nenaner.aoc2019.FileManager
import com.nenaner.aoc2019.IntCodeProcessor
import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.shouldBe
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AmplifierArrayWithFeedbackLoopTest {
    @SpyK
    private val outputLoggerSpy = OutputLogger()

    private lateinit var amplifierA: Amplifier
    private lateinit var amplifierB: Amplifier
    private lateinit var amplifierC: Amplifier
    private lateinit var amplifierD: Amplifier
    private lateinit var amplifierE: Amplifier
    private lateinit var subject: AmplifierArray

    @BeforeEach
    internal fun setUp() {
        amplifierA = setupAmplifier()
        amplifierB = setupAmplifier()
        amplifierC = setupAmplifier()
        amplifierD = setupAmplifier()
        amplifierE = setupAmplifier()
        subject = AmplifierArray(outputLoggerSpy, amplifierA, amplifierB, amplifierC, amplifierD, amplifierE)
    }

    @Test
    internal fun `9,8,7,6,5 should generate a result of 43210`() {
        subject.initializeAmplifiersAlongWithCustomIntCode(listOf(9, 8, 7, 6, 5), "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5")
        subject.processPhaseSettingSequenceWithFeedbackLoop().shouldBe(139629729)
    }

    @Test
    internal fun `9,7,8,5,6 should generate a result of 18216`() {
        subject.initializeAmplifiersAlongWithCustomIntCode(listOf(9, 7, 8, 5, 6), "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10")
        subject.processPhaseSettingSequenceWithFeedbackLoop().shouldBe(18216)
    }

    @Test
    @Disabled
    internal fun allPermutations() {
        val results = mutableListOf<Pair<List<Long>, Long>>()
        var highestResult = 0L
        for (a in 5L..9L)
            for (b in 5L..9L)
                for (c in 5L..9L)
                    for (d in 5L..9L)
                        for (e in 5L..9L) {
                            val phaseSetting = listOf(a, b, c, d, e)
                            if (noMoreThanOneOfEach(phaseSetting)) {
                                println("Attempting $a,$b,$c,$d,$e")
                                subject.initializeAmplifiersAlongWithCustomIntCode(phaseSetting)
                                val potentialResult = subject.processPhaseSettingSequenceWithFeedbackLoop()
                                if (potentialResult != null) {
                                    val result: Long = potentialResult
                                    println("Processed $a,$b,$c,$d,$e with a result of $result")
                                    results.add(Pair(phaseSetting, result))
                                    if (result > highestResult) highestResult = result
                                }
                            }
                        }

        println("highest result: $highestResult")
        Assertions.assertTrue(results.isNotEmpty())
    }

    private fun noMoreThanOneOfEach(phaseSetting: List<Long>): Boolean {
        val countOfFives = phaseSetting.filter { it == 5L }.size
        val countOfSixes = phaseSetting.filter { it == 6L }.size
        val countOfSevens = phaseSetting.filter { it == 7L }.size
        val countOfEights = phaseSetting.filter { it == 8L }.size
        val countOfNines = phaseSetting.filter { it == 9L }.size
        return countOfFives < 2 && countOfSixes < 2 && countOfSevens < 2 && countOfEights < 2 && countOfNines < 2
    }

    private fun setupAmplifier(): Amplifier {
        val intCodeProcessor = IntCodeProcessor(outputLoggerSpy)
        val fileManager = FileManager()
        return Amplifier(intCodeProcessor, outputLoggerSpy, fileManager)
    }
}
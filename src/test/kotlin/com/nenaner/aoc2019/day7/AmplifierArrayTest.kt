package com.nenaner.aoc2019.day7

import com.nenaner.aoc2019.FileManager
import com.nenaner.aoc2019.IntCodeProcessor
import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AmplifierArrayTest : StringSpec() {
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
    internal fun `4,3,2,1,0 should generate a result of 43210`() {
        subject.initializeAmplifiersAlongWithCustomIntCode(listOf(4, 3, 2, 1, 0), "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0")
        subject.processPhaseSettingSequence().shouldBe(43210)
    }

    @Test
    internal fun `0,1,2,3,4 should generate a result of 54321`() {
        subject.initializeAmplifiersAlongWithCustomIntCode(listOf(0, 1, 2, 3, 4), "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0")
        subject.processPhaseSettingSequence().shouldBe(54321)
    }

    @Test
    internal fun `1,0,4,3,2 should generate a result of 65210`() {
        subject.initializeAmplifiersAlongWithCustomIntCode(listOf(1, 0, 4, 3, 2), "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0")
        subject.processPhaseSettingSequence().shouldBe(65210)
    }

    @Test
    @Disabled
    internal fun allPermutations() {
        val results = mutableListOf<Pair<List<Long>, Long>>()
        var highestResult = 0L
        for (a in 0L..4L)
            for (b in 0L..4L)
                for (c in 0L..4L)
                    for (d in 0L..4L)
                        for (e in 0L..4L) {
                            val phaseSetting = listOf(a, b, c, d, e)
                            if (noMoreThanOneOfEach(phaseSetting)) {
                                subject.initializeAmplifiersAlongWithCustomIntCode(phaseSetting)
                                val potentialResult = subject.processPhaseSettingSequence()
                                if (potentialResult != null) {
                                    val result: Long = potentialResult
                                    println("Processed $a,$b,$c,$d,$e with a result of $result")
                                    results.add(Pair(phaseSetting, result))
                                    if (result > highestResult) highestResult = result
                                }
                            }
                        }

        println("highest result: $highestResult")
        assertTrue(results.isNotEmpty())
    }

    private fun noMoreThanOneOfEach(phaseSetting: List<Long>): Boolean {
        val countOfZeroes = phaseSetting.filter { it == 0L }.size
        val countOfOnes = phaseSetting.filter { it == 1L }.size
        val countOfTwos = phaseSetting.filter { it == 2L }.size
        val countOfThrees = phaseSetting.filter { it == 3L }.size
        val countOfFours = phaseSetting.filter { it == 4L }.size
        return countOfZeroes < 2 && countOfOnes < 2 && countOfTwos < 2 && countOfThrees < 2 && countOfFours < 2
    }

    private fun setupAmplifier(): Amplifier {
        val intCodeProcessor = IntCodeProcessor(outputLoggerSpy)
        val fileManager = FileManager()
        return Amplifier(intCodeProcessor, outputLoggerSpy, fileManager)
    }
}

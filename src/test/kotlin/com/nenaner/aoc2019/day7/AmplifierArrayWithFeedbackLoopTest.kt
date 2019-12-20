package com.nenaner.aoc2019.day7

import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Spy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AmplifierArrayWithFeedbackLoopTest : StringSpec() {
    override fun listeners(): List<TestListener> {
        return listOf(SpringListener)
    }

    @Spy
    private lateinit var outputLogger: OutputLogger
    @Spy
    private lateinit var amplifierA: Amplifier
    @Spy
    private lateinit var amplifierB: Amplifier
    @Spy
    private lateinit var amplifierC: Amplifier
    @Spy
    private lateinit var amplifierD: Amplifier
    @Spy
    private lateinit var amplifierE: Amplifier

    @InjectMocks
    private lateinit var subject: AmplifierArray

    init {
        "9,8,7,6,5 should generate a result of 43210" {
            subject.initializeAmplifiers(listOf(9, 8, 7, 6, 5), "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5")
            subject.processPhaseSettingSequenceWithFeedbackLoop().shouldBe(139629729)
        }
        "9,7,8,5,6 should generate a result of 43210" {
            subject.initializeAmplifiers(listOf(9, 7, 8, 5, 6), "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10")
            subject.processPhaseSettingSequenceWithFeedbackLoop().shouldBe(18216)
        }
    }

    @Test
    internal fun allPermutations() {
        val results = mutableListOf<Pair<List<Int>, Int>>()
        var highestResult = 0
        for (a in 5..9)
            for (b in 5..9)
                for (c in 5..9)
                    for (d in 5..9)
                        for (e in 5..9) {
                            val phaseSetting = listOf(a, b, c, d, e)
                            if (noMoreThanOneOfEach(phaseSetting)) {
                                println("Attempting $a,$b,$c,$d,$e")
                                subject.initializeAmplifiers(phaseSetting)
                                val potentialResult = subject.processPhaseSettingSequenceWithFeedbackLoop()
                                if (potentialResult != null) {
                                    val result: Int = potentialResult
                                    println("Processed $a,$b,$c,$d,$e with a result of $result")
                                    results.add(Pair(phaseSetting, result))
                                    if (result > highestResult) highestResult = result
                                }
                            }
                        }

        println("highest result: $highestResult")
        Assertions.assertTrue(results.isNotEmpty())
    }

    private fun noMoreThanOneOfEach(phaseSetting: List<Int>): Boolean {
        val countOfFives = phaseSetting.filter { it == 5 }.size
        val countOfSixes = phaseSetting.filter { it == 6 }.size
        val countOfSevens = phaseSetting.filter { it == 7 }.size
        val countOfEights = phaseSetting.filter { it == 8 }.size
        val countOfNines = phaseSetting.filter { it == 9 }.size
        return countOfFives < 2 && countOfSixes < 2 && countOfSevens < 2 && countOfEights < 2 && countOfNines < 2
    }
}
package com.nenaner.aoc2019.day7

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AmplifierArrayTest : StringSpec() {
    override fun listeners() = listOf(SpringListener)

    // TODO: Need to work on changing this to @InjectMocks.
    // The tests are not getting a clean instance each run in the current setup
    @Autowired
    private lateinit var subject: AmplifierArray

    init {
        "4,3,2,1,0 should generate a result of 43210" {
            subject.initializeAmplifiers(listOf(4, 3, 2, 1, 0), "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0")
            subject.processPhaseSettingSequence().shouldBe(43210)
        }
        "0,1,2,3,4 should generate a result of 43210" {
            subject.initializeAmplifiers(listOf(0, 1, 2, 3, 4),"3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0")
            subject.processPhaseSettingSequence().shouldBe(54321)
        }
        "1,0,4,3,2 should generate a result of 65210" {
            subject.initializeAmplifiers(listOf(1, 0, 4, 3, 2),"3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0")
            subject.processPhaseSettingSequence().shouldBe(65210)
        }
    }


    @Test
    internal fun allPermutations() {
        val results = mutableListOf<Pair<List<Int>, Int>>()
        var highestResult = 0
        for (a in 0..4)
            for (b in 0..4)
                for (c in 0..4)
                    for (d in 0..4)
                        for (e in 0..4) {
                            val phaseSetting = listOf(a, b, c, d, e)
                            if (noMoreThanOneOfEach(phaseSetting)) {
                                subject.initializeAmplifiers(phaseSetting)
                                val potentialResult = subject.processPhaseSettingSequence()
                                if (potentialResult != null) {
                                    val result: Int = potentialResult
                                    println("Processed $a,$b,$c,$d,$e with a result of $result")
                                    results.add(Pair(phaseSetting, result))
                                    if (result > highestResult) highestResult = result
                                }
                            }
                        }

        println("highest result: $highestResult")
        assertTrue(results.isNotEmpty())
    }

    private fun noMoreThanOneOfEach(phaseSetting: List<Int>): Boolean {
        val countOfZeroes = phaseSetting.filter { it == 0 }.size
        val countOfOnes = phaseSetting.filter { it == 1 }.size
        val countOfTwos = phaseSetting.filter { it == 2 }.size
        val countOfThrees = phaseSetting.filter { it == 3 }.size
        val countOfFours = phaseSetting.filter { it == 4 }.size
        return countOfZeroes < 2 && countOfOnes < 2 && countOfTwos < 2 && countOfThrees < 2 && countOfFours < 2
    }
}

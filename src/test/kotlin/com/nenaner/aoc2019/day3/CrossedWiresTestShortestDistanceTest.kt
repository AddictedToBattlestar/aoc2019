package com.nenaner.aoc2019.day3

import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class CrossedWiresTestShortestDistanceTest: StringSpec() {
    override fun listeners(): List<TestListener> {
        return listOf(SpringListener)
    }

    @Autowired
    private lateinit var subject: CrossedWires

    init {
        "'R8,U5,L5,D3' and 'U7,R6,D4,L4'" {
            val firstWirePath = "R8,U5,L5,D3"
            val secondWirePath = "U7,R6,D4,L4"
            subject.findShortestDistance(firstWirePath, secondWirePath).shouldBe(6)
        }
        "'R75,D30,R83,U83,L12,D49,R71,U7,L72' and 'U62,R66,U55,R34,D71,R55,D58,R83'" {
            val firstWirePath = "R75,D30,R83,U83,L12,D49,R71,U7,L72"
            val secondWirePath = "U62,R66,U55,R34,D71,R55,D58,R83"
            subject.findShortestDistance(firstWirePath, secondWirePath).shouldBe(159)
        }
        "'R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51' and 'U98,R91,D20,R16,D67,R40,U7,R15,U6,R7'" {
            val firstWirePath = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"
            val secondWirePath = "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
            subject.findShortestDistance(firstWirePath, secondWirePath).shouldBe(135)
        }
        "Process the file data for the shortest distance" {
            subject.findShortestDistanceFromFile()
        }
    }
}
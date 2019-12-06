package com.nenaner.aoc2019.day2

import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ProgramAlarmTest : StringSpec() {
    override fun listeners(): List<TestListener> {
        return listOf(SpringListener)
    }

    @Autowired
    private lateinit var subject: ProgramAlarm

    init {
        "'1,0,0,0,99' becomes '2,0,0,0,99'" {
            subject.processIntCode("1,0,0,0,99").shouldBe("2,0,0,0,99")
        }
        "'2,3,0,3,99' becomes '2,3,0,6,99'" {
            subject.processIntCode("2,3,0,3,99").shouldBe("2,3,0,6,99")
        }
        "'2,4,4,5,99,0' becomes '2,4,4,5,99,9801'" {
            subject.processIntCode("2,4,4,5,99,0").shouldBe("2,4,4,5,99,9801")
        }
        "'1,1,1,4,99,5,6,0,99' becomes '30,1,1,4,2,5,6,0,99'" {
            subject.processIntCode("1,1,1,4,99,5,6,0,99").shouldBe("30,1,1,4,2,5,6,0,99")
        }
        "Process the intCode file (Edited manually)" {
            val result = subject.processIntCodeFromFile(12, 2)
            println("The processed intCode file results: $result")
        }
        "Process the intCode file to find the outcome 19690720" {
            val (firstValue, secondValue) = subject.processIntCodeFromFileForDesirableOutcome(19690720)
            println("The processed intCode file results: first value = $firstValue, second value = $secondValue")
        }
    }

}
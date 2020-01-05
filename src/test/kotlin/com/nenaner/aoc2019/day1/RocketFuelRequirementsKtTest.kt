package com.nenaner.aoc2019.day1

import io.kotlintest.extensions.TestListener
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class RocketFuelRequirementsKtTest : StringSpec() {
    override fun listeners(): List<TestListener> {
        return listOf(SpringListener)
    }

    @Autowired
    private lateinit var subject: RocketFuelRequirements

    init {
        "fuel requirements for a mass of 14" {
            subject.getFuelRequirements(12L).shouldBe(2L)
        }
        "fuel requirements for a mass of 1969" {
            subject.getFuelRequirements(1969L).shouldBe(966L)
        }
        "fuel requirements for a mass of 100756" {
            subject.getFuelRequirements(100756L).shouldBe(50346L)
        }
        "get the total fuel requirements" {
            val result = subject.getRocketFuelRequirements()
            println("The total fuel requirements are: $result")
        }
    }
}



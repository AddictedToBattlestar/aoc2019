package com.nenaner.aoc2019.day4

import io.kotlintest.extensions.TestListener
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SecureContainerPasswordIdentifierTest : StringSpec() {
    override fun listeners(): List<TestListener> {
        return listOf(SpringListener)
    }

    @Autowired
    private lateinit var subject: SecureContainerPasswordVerifier

    init {
        "It is a six digit number" {
            assertTrue(subject.isSixDigitNumber("666666"))
            assertFalse(subject.isSixDigitNumber("55555"))
            assertFalse(subject.isSixDigitNumber("7777777"))
            assertFalse(subject.isSixDigitNumber("abcdef"))
            assertFalse(subject.isSixDigitNumber("123abc"))
            assertFalse(subject.isSixDigitNumber("abc123"))
            assertFalse(subject.isSixDigitNumber("a123bc"))
            assertFalse(subject.isSixDigitNumber("a"))
            assertFalse(subject.isSixDigitNumber(""))
        }
        "Two adjacent digits are the same" {
            assertTrue(subject.areTwoAdjacentDigitsTheSame("1234567899"))
            assertTrue(subject.areTwoAdjacentDigitsTheSame("11"))
            assertTrue(subject.areTwoAdjacentDigitsTheSame("3985205764483"))
            assertTrue(subject.areTwoAdjacentDigitsTheSame("a11a"))
            assertFalse(subject.areTwoAdjacentDigitsTheSame("1aa1"))
            assertFalse(subject.areTwoAdjacentDigitsTheSame("68463867483"))
            assertFalse(subject.areTwoAdjacentDigitsTheSame(""))
        }
        "Going from left to right, the digits never decrease; they only ever increase or stay the same" {
            assertTrue(subject.areDigitsTheSameOrIncreasing("012345"))
            assertTrue(subject.areDigitsTheSameOrIncreasing("6789"))
            assertTrue(subject.areDigitsTheSameOrIncreasing("0123456789"))
            assertTrue(subject.areDigitsTheSameOrIncreasing("1234567899"))
            assertTrue(subject.areDigitsTheSameOrIncreasing("1123456789"))
            assertTrue(subject.areDigitsTheSameOrIncreasing("11111"))
            assertTrue(subject.areDigitsTheSameOrIncreasing("1"))
            assertFalse(subject.areDigitsTheSameOrIncreasing(""))
            assertFalse(subject.areDigitsTheSameOrIncreasing("1234567890"))
            assertFalse(subject.areDigitsTheSameOrIncreasing("987654321"))
            assertFalse(subject.areDigitsTheSameOrIncreasing("987654321"))
            assertFalse(subject.areDigitsTheSameOrIncreasing("12345a"))
            assertFalse(subject.areDigitsTheSameOrIncreasing("a12345"))
        }
        "Clarification (part two), Exactly two adjacent digits are the same somewhere in the password" {
            assertFalse(subject.doesPasswordMeetRequirements("111111"))
            assertFalse(subject.doesPasswordMeetRequirements("223450"))
            assertFalse(subject.doesPasswordMeetRequirements("123789"))
            assertTrue(subject.doesPasswordMeetRequirements("112233"))
            assertFalse(subject.doesPasswordMeetRequirements("123444"))
            assertTrue(subject.doesPasswordMeetRequirements("111122"))
        }
        "The number of passwords valid in numbers between 402328-864247" {
            val tallyAmount = subject.tallyNumberOfPasswordsValidInRange(402328, 864247);
            println("The number of valid passwords in the range of 402328-864247 is: $tallyAmount")
        }
    }
}
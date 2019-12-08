package com.nenaner.aoc2019.day4

import org.springframework.stereotype.Component

@Component
class SecureContainerPasswordVerifier {
    fun tallyNumberOfPasswordsValidInRange(lowValue: Int, highValue: Int): Int {
        var tallyAmount = 0
        for (numberToEvaluate in lowValue..highValue) {
            if (doesPasswordMeetRequirements(numberToEvaluate.toString())) {
                tallyAmount++
            }
        }
        return tallyAmount
    }

    fun doesPasswordMeetRequirements(password: String): Boolean {
        return isSixDigitNumber(password)
                && areTwoAdjacentDigitsTheSame(password)
                && areDigitsTheSameOrIncreasing(password)
    }

    internal fun isSixDigitNumber(password: String): Boolean {
        val regex = "([0-9]){6}".toRegex()
        return password.length == 6 && regex.matches(password)
    }

    internal fun areTwoAdjacentDigitsTheSame(password: String): Boolean {
        var lastCharacter: Char? = null
        var repeatCount = 0
        for (passwordCharacter in password) {
            if (passwordCharacter.isDigit() && passwordCharacter == lastCharacter) {
                repeatCount++
            } else {
                if (repeatCount == 1) {
                    return true
                }
                lastCharacter = passwordCharacter
                repeatCount = 0
            }
        }
        if (repeatCount == 1) {
            return true
        }
        return false
    }

    fun areDigitsTheSameOrIncreasing(password: String): Boolean {
        if (password.toIntOrNull() == null) {
            return false
        }
        var lastValue: Int? = null
        for (passwordCharacter in password) {
            val digit = passwordCharacter.toString().toInt()
            if (lastValue != null && digit < lastValue) {
                return false
            }
            lastValue = digit
        }
        return true
    }

}
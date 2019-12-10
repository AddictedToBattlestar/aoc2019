package com.nenaner.aoc2019.day5

import com.nenaner.aoc2019.OutputLogger
import com.nhaarman.mockitokotlin2.argumentCaptor
import io.kotlintest.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class DiagnosticProgramTest {
    @Spy
    private lateinit var outputLoggerSpy: OutputLogger

    @InjectMocks
    private lateinit var subject: DiagnosticProgram

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    @DisplayName("'1,0,0,0,99' becomes '2,0,0,0,99'")
    internal fun variation1() {
        subject.processOpCode("1,0,0,0,99").shouldBe("2,0,0,0,99")
    }

    @Test
    @DisplayName("'2,3,0,3,99' becomes '2,3,0,6,99'")
    internal fun variation2() {
        subject.processOpCode("2,3,0,3,99").shouldBe("2,3,0,6,99")
    }

    @Test
    @DisplayName("'2,4,4,5,99,0' becomes '2,4,4,5,99,9801'")
    internal fun variation3() {
        subject.processOpCode("2,4,4,5,99,0").shouldBe("2,4,4,5,99,9801")
    }

    @Test
    @DisplayName("'1,1,1,4,99,5,6,0,99' becomes '30,1,1,4,2,5,6,0,99'")
    internal fun variation4() {
        subject.processOpCode("1,1,1,4,99,5,6,0,99").shouldBe("30,1,1,4,2,5,6,0,99")
    }

    @Test
    @DisplayName("'1002,4,3,4,33' becomes '1002,4,3,4,99'")
    internal fun variation5() {
        subject.processOpCode("1002,4,3,4,33").shouldBe("1002,4,3,4,99")
    }

    @Test
    @DisplayName("'1101,100,-1,4,0' becomes '1101,100,-1,4,0'")
    internal fun variation7() {
        subject.processOpCode("1101,100,-1,4,0").shouldBe("1101,100,-1,4,99")
    }

    @Test
    @DisplayName("'3,3,1,0,32,7,99,0 (with an input entered of 5)' becomes '3,3,1,5,5,7,99,12'")
    internal fun variation8() {
        subject.processInitialInput("3,3,1,0,5,7,99,0", "5").shouldBe("3,3,1,5,5,7,99,14")
    }

    @Test
    @DisplayName("'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9 (with an input entered of 0)' generates the output '0'")
    internal fun variation9() {
        subject.processInitialInput("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "42")

        argumentCaptor<String>().apply {
            verify(outputLoggerSpy).info(capture())
            firstValue.shouldBe("1")
        }
    }

    @Test
    @DisplayName("'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9 (with an input entered of 42)' generates the output '1'")
    internal fun variation10() {
        subject.processInitialInput("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "42")

        argumentCaptor<String>().apply {
            verify(outputLoggerSpy).info(capture())
            firstValue.shouldBe("1")
        }
    }

    @Test
    @DisplayName("'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9 (with an input entered of -5)' generates the output '1'")
    internal fun variation11() {
        subject.processInitialInput("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "-5")

        argumentCaptor<String>().apply {
            verify(outputLoggerSpy).info(capture())
            firstValue.shouldBe("1")
        }
    }

//    @Test
//    @DisplayName("The actual problem from Advent of Code, part 1")
//    internal fun variation12() {
//        subject.processIntCodeWithInitialInput(intCodeToProcess, "1")
//    }

//    @Test
//    @DisplayName("The actual problem from Advent of Code, part 2")
//    internal fun variation13() {
//        subject.processIntCodeWithInitialInput(intCodeToProcess, "5")
//    }

//    init {
//        "'1,0,0,0,99' becomes '2,0,0,0,99'" {
//            subject.processIntCode("1,0,0,0,99").shouldBe("2,0,0,0,99")
//        }
//        "'2,3,0,3,99' becomes '2,3,0,6,99'" {
//            subject.processIntCode("2,3,0,3,99").shouldBe("2,3,0,6,99")
//        }
//        "'2,4,4,5,99,0' becomes '2,4,4,5,99,9801'" {
//            subject.processIntCode("2,4,4,5,99,0").shouldBe("2,4,4,5,99,9801")
//        }
//        "'1,1,1,4,99,5,6,0,99' becomes '30,1,1,4,2,5,6,0,99'" {
//            subject.processIntCode("1,1,1,4,99,5,6,0,99").shouldBe("30,1,1,4,2,5,6,0,99")
//        }
//        "'1002,4,3,4,33' becomes '1002,4,3,4,99'" {
//            subject.processIntCode("1002,4,3,4,33").shouldBe("1002,4,3,4,99")
//        }
//        "'1101,100,-1,4,0' becomes '1101,100,-1,4,0'" {
//            subject.processIntCode("1101,100,-1,4,0").shouldBe("1101,100,-1,4,99")
//        }
//        "'3,3,1,0,32,7,99,0 (with an input entered of 5)' becomes '3,3,1,5,5,7,99,12'" {
//            subject.processIntCodeWithInitialInput("3,3,1,0,5,7,99,0", "5").shouldBe("3,3,1,5,5,7,99,14")
//        }
//        "'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9 (with an input entered of 0)' generates the output '0'" {
//            subject.processIntCodeWithInitialInput("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "0")
//
//            argumentCaptor<String>().apply {
//                verify(outputLoggerSpy).info(capture())
//                firstValue.shouldBe("0")
//            }
//        }
//        "'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9 (with an input entered of 42)' generates the output '1'" {
//            subject.processIntCodeWithInitialInput("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "42")
//
//            argumentCaptor<String>().apply {
//                verify(outputLoggerSpy).info(capture())
//                firstValue.shouldBe("1")
//            }
//        }
//        "'3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9 (with an input entered of -5)' generates the output '1'" {
//            subject.processIntCodeWithInitialInput("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", "-5")
//
//            argumentCaptor<String>().apply {
//                verify(outputLoggerSpy).info(capture())
//                firstValue.shouldBe("1")
//            }
//        }
//        "The actual problem from Advent of Code, part 1" {
//            subject.processIntCodeWithInitialInput(intCodeToProcess, "1")
//        }
//        "The actual problem from Advent of Code, part 2" {
//            subject.processIntCodeWithInitialInput(intCodeToProcess, "5")
//        }
//    }

    companion object {
        const val intCodeToProcess = "3,225,1,225,6,6,1100,1,238,225,104,0,1101,34,7,225,101,17,169,224,1001,224,-92,224,4,224,1002,223,8,223,1001,224,6,224,1,224,223,223,1102,46,28,225,1102,66,83,225,2,174,143,224,1001,224,-3280,224,4,224,1002,223,8,223,1001,224,2,224,1,224,223,223,1101,19,83,224,101,-102,224,224,4,224,102,8,223,223,101,5,224,224,1,223,224,223,1001,114,17,224,1001,224,-63,224,4,224,1002,223,8,223,1001,224,3,224,1,223,224,223,1102,60,46,225,1101,7,44,225,1002,40,64,224,1001,224,-1792,224,4,224,102,8,223,223,101,4,224,224,1,223,224,223,1101,80,27,225,1,118,44,224,101,-127,224,224,4,224,102,8,223,223,101,5,224,224,1,223,224,223,1102,75,82,225,1101,40,41,225,1102,22,61,224,1001,224,-1342,224,4,224,102,8,223,223,1001,224,6,224,1,223,224,223,102,73,14,224,1001,224,-511,224,4,224,1002,223,8,223,101,5,224,224,1,224,223,223,4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247,1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999,1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999,1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0,1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0,1105,1,99999,1008,677,677,224,1002,223,2,223,1006,224,329,1001,223,1,223,1007,226,226,224,1002,223,2,223,1005,224,344,101,1,223,223,1008,226,226,224,1002,223,2,223,1006,224,359,101,1,223,223,8,226,677,224,102,2,223,223,1006,224,374,101,1,223,223,1107,677,226,224,1002,223,2,223,1005,224,389,101,1,223,223,1008,677,226,224,102,2,223,223,1006,224,404,1001,223,1,223,1108,677,677,224,102,2,223,223,1005,224,419,1001,223,1,223,1107,677,677,224,102,2,223,223,1006,224,434,1001,223,1,223,1108,226,677,224,1002,223,2,223,1006,224,449,101,1,223,223,8,677,226,224,1002,223,2,223,1005,224,464,101,1,223,223,108,226,677,224,102,2,223,223,1005,224,479,1001,223,1,223,1107,226,677,224,102,2,223,223,1005,224,494,101,1,223,223,108,677,677,224,1002,223,2,223,1005,224,509,1001,223,1,223,7,677,226,224,1002,223,2,223,1006,224,524,101,1,223,223,1007,677,677,224,1002,223,2,223,1006,224,539,1001,223,1,223,107,226,226,224,102,2,223,223,1006,224,554,101,1,223,223,107,677,677,224,102,2,223,223,1006,224,569,1001,223,1,223,1007,226,677,224,1002,223,2,223,1006,224,584,101,1,223,223,108,226,226,224,102,2,223,223,1006,224,599,1001,223,1,223,7,226,226,224,102,2,223,223,1006,224,614,1001,223,1,223,8,226,226,224,1002,223,2,223,1006,224,629,1001,223,1,223,7,226,677,224,1002,223,2,223,1005,224,644,101,1,223,223,1108,677,226,224,102,2,223,223,1006,224,659,101,1,223,223,107,226,677,224,102,2,223,223,1006,224,674,1001,223,1,223,4,223,99,226"
    }
}
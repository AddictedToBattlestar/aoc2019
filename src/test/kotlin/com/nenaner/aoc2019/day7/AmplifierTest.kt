package com.nenaner.aoc2019.day7

import com.nenaner.aoc2019.FileManager
import com.nenaner.aoc2019.IntCodeProcessor
import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.extensions.TestListener
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.junit.jupiter.api.Assertions.assertNotNull
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AmplifierTest : StringSpec() {
    override fun listeners(): List<TestListener> {
        return listOf(SpringListener)
    }

    @Spy
    private lateinit var fileManager: FileManager
    @Mock
    private lateinit var intCodeProcessor: IntCodeProcessor
    @Mock
    private lateinit var outputLogger: OutputLogger

    @InjectMocks
    private lateinit var subject: Amplifier

    init {
        "basic existence assertion" {
            assertNotNull(subject)

        }
    }
}
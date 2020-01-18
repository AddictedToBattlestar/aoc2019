package com.nenaner.aoc2019.day7

import com.nenaner.aoc2019.FileManager
import com.nenaner.aoc2019.IntCodeProcessor
import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.extensions.TestListener
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Spy
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class AmplifierTest {
    @SpyK
    private var fileManager = FileManager()
    @MockK
    private lateinit var intCodeProcessor: IntCodeProcessor
    @MockK
    private lateinit var outputLogger: OutputLogger

    @InjectMockKs
    private lateinit var subject: Amplifier

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    internal fun `basic existence assertion`() {
        assertNotNull(subject)
    }
}
package com.nenaner.aoc2019.day8

import com.nenaner.aoc2019.FileManager
import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SpaceImageProcessorTest {
    @SpyK
    private var fileManagerSpy = FileManager()
    @SpyK
    private var outputLoggerSpy = OutputLogger()

    @InjectMockKs
    private lateinit var subject: SpaceImageProcessor

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    internal fun `it can retrieve the stored image layers from the input file`() {
        subject.findLayerWithTheFewestZeroesFromFile()
    }

    @Test
    internal fun `part2, it can decode an image`() {
        subject.decodeImageToString("0222112222120000", 2, 2).shouldBe("01\n10\n")
    }

    @Test
    internal fun `part2, it can decode an image from the input file`() {
        subject.decodeImageFromFile().shouldNotBeNull()
    }
}
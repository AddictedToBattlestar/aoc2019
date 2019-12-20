package com.nenaner.aoc2019.day8

import com.nenaner.aoc2019.FileManager
import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.spring.SpringListener
import org.mockito.InjectMocks
import org.mockito.Spy

internal class SpaceImageProcessorTest : StringSpec() {
    override fun listeners() = listOf(SpringListener)

    @Spy
    private lateinit var fileManager: FileManager
    @Spy
    private lateinit var outputLogger: OutputLogger

    @InjectMocks
    private lateinit var spaceImageProcessor: SpaceImageProcessor

    init {
        "it can retrieve the stored image layers from the input file" {
            spaceImageProcessor.findLayerWithTheFewestZeroesFromFile()
        }
        "part2, it can decode an image" {
            spaceImageProcessor.decodeImageToString("0222112222120000", 2, 2).shouldBe("01\n10\n")
        }
        "part2, it can decode an image from the input file" {
            spaceImageProcessor.decodeImageFromFile().shouldNotBeNull()
        }
    }
}
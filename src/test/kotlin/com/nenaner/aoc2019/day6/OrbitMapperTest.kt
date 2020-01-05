package com.nenaner.aoc2019.day6

import com.nenaner.aoc2019.FileManager
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class OrbitMapperTest {
    @SpyK
    private var fileManager = FileManager()

    @InjectMockKs
    private lateinit var subject: OrbitMapper

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    @DisplayName("It returns 42 from the sample orbit map (part 1)")
    internal fun part1Test() {
        subject.getTotalNumberOfOrbits(sampleOrbitMapPart1).shouldBe(42)
    }

    @Test
    internal fun part1() {
        subject.getTotalNumberOfOrbitsFromFile()
    }

    @Test
    @DisplayName("It returns 4 for the minimum number of orbital transfers from the sample orbit map (part2)")
    internal fun part2Test() {
        subject.getMinimumNumberOfOrbitalTransfers(sampleOrbitMapPart2, "YOU", "SAN").shouldBe(4)
    }

    @Test
    internal fun part2() {
        subject.getMinimumNumberOfOrbitalTransfersFromFile()
    }

    companion object {
        private const val sampleOrbitMapPart1 = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L"

        private const val sampleOrbitMapPart2 = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L\n" +
                "K)YOU\n" +
                "I)SAN"
    }
}

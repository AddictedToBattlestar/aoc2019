package com.nenaner.aoc2019.day10

import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MonitoringStationTest {
    @SpyK
    private var outputLoggerSpy = OutputLogger()
    @SpyK
    private var asteroidMapHelperSpy = AsteroidMapHelper(outputLoggerSpy)
    @SpyK
    private var broadeningSweepScan = BroadeningSweepScan(asteroidMapHelperSpy, outputLoggerSpy)

    @InjectMockKs
    private lateinit var subject: MonitoringStation

    @BeforeEach
    fun setUp() = MockKAnnotations.init(this)

    @Test
    internal fun `vertical test`() {
        val asteroidMap =
                        "..#..\n" +
                        ".....\n" +
                        "..#..\n" +
                        ".....\n" +
                        "..#.."
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(2)
        result.second.shouldBe(2)
        result.third.shouldBe(2)
    }

    @Test
    internal fun `horizontal test`() {
        val asteroidMap =
                        ".....\n" +
                        ".....\n" +
                        "#.#.#\n" +
                        ".....\n" +
                        "....."
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(2)
        result.second.shouldBe(2)
        result.third.shouldBe(2)
    }

    @Test
    internal fun `diagonal test 1`() {
        val asteroidMap =
                        "#....\n" +
                        ".....\n" +
                        "..#..\n" +
                        ".....\n" +
                        "....#"
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(2)
        result.second.shouldBe(2)
        result.third.shouldBe(2)
    }

    @Test
    internal fun `diagonal test 2`() {
        val asteroidMap =
                        "....#\n" +
                        ".....\n" +
                        "..#..\n" +
                        ".....\n" +
                        "#...."
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(2)
        result.second.shouldBe(2)
        result.third.shouldBe(2)
    }

    @Test
    internal fun `part 1, example 1`() {
        val asteroidMap =
                ".#..#\n" +
                        ".....\n" +
                        "#####\n" +
                        "....#\n" +
                        "...##"
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(3)
        result.second.shouldBe(4)
        result.third.shouldBe(8)
    }

    @Test
    internal fun `part 1, example 2`() {
        val asteroidMap =
                "......#.#.\n" +
                        "#..#.#....\n" +
                        "..#######.\n" +
                        ".#.#.###..\n" +
                        ".#..#.....\n" +
                        "..#....#.#\n" +
                        "#..#....#.\n" +
                        ".##.#..###\n" +
                        "##...#..#.\n" +
                        ".#....####"
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(5)
        result.second.shouldBe(8)
        result.third.shouldBe(33)
    }

    @Test
    internal fun `part 1, example 3`() {
        val asteroidMap =
                "#.#...#.#.\n" +
                        ".###....#.\n" +
                        ".#....#...\n" +
                        "##.#.#.#.#\n" +
                        "....#.#.#.\n" +
                        ".##..###.#\n" +
                        "..#...##..\n" +
                        "..##....##\n" +
                        "......#...\n" +
                        ".####.###."
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(1)
        result.second.shouldBe(2)
        result.third.shouldBe(35)
    }

    @Test
    internal fun `part 1, example 4`() {
        val asteroidMap =
                ".#..#..###\n" +
                        "####.###.#\n" +
                        "....###.#.\n" +
                        "..###.##.#\n" +
                        "##.##.#.#.\n" +
                        "....###..#\n" +
                        "..#.#..#.#\n" +
                        "#..#.#.###\n" +
                        ".##...##.#\n" +
                        ".....#.#.."
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(6)
        result.second.shouldBe(3)
        result.third.shouldBe(41)
    }

    @Test
    internal fun `part 1, example 5`() {
        val asteroidMap =
                ".#..##.###...#######\n" +
                        "##.############..##.\n" +
                        ".#.######.########.#\n" +
                        ".###.#######.####.#.\n" +
                        "#####.##.#.##.###.##\n" +
                        "..#####..#.#########\n" +
                        "####################\n" +
                        "#.####....###.#.#.##\n" +
                        "##.#################\n" +
                        "#####.##.###..####..\n" +
                        "..######..##.#######\n" +
                        "####.##.####...##..#\n" +
                        ".#####..#.######.###\n" +
                        "##...#.##########...\n" +
                        "#.##########.#######\n" +
                        ".####.#.###.###.#.##\n" +
                        "....##.##.###..#####\n" +
                        ".#.#.###########.###\n" +
                        "#.#.#.#####.####.###\n" +
                        "###.##.####.##.#..##"
        val result = subject.findBestLocation(asteroidMap)
        result.first.shouldBe(11)
        result.second.shouldBe(13)
        result.third.shouldBe(210)
    }

    @Test
    internal fun `part 1`() {
        val asteroidMap =
                ".##.#.#....#.#.#..##..#.#.\n" +
                        "#.##.#..#.####.##....##.#.\n" +
                        "###.##.##.#.#...#..###....\n" +
                        "####.##..###.#.#...####..#\n" +
                        "..#####..#.#.#..#######..#\n" +
                        ".###..##..###.####.#######\n" +
                        ".##..##.###..##.##.....###\n" +
                        "#..#..###..##.#...#..####.\n" +
                        "....#.#...##.##....#.#..##\n" +
                        "..#.#.###.####..##.###.#.#\n" +
                        ".#..##.#####.##.####..#.#.\n" +
                        "#..##.#.#.###.#..##.##....\n" +
                        "#.#.##.#.##.##......###.#.\n" +
                        "#####...###.####..#.##....\n" +
                        ".#####.#.#..#.##.#.#...###\n" +
                        ".#..#.##.#.#.##.#....###.#\n" +
                        ".......###.#....##.....###\n" +
                        "#..#####.#..#..##..##.#.##\n" +
                        "##.#.###..######.###..#..#\n" +
                        "#.#....####.##.###....####\n" +
                        "..#.#.#.########.....#.#.#\n" +
                        ".##.#.#..#...###.####..##.\n" +
                        "##...###....#.##.##..#....\n" +
                        "..##.##.##.#######..#...#.\n" +
                        ".###..#.#..#...###..###.#.\n" +
                        "#..#..#######..#.#..#..#.#"
        val result = subject.findBestLocation(asteroidMap)
        println("Result: $result")
    }
}
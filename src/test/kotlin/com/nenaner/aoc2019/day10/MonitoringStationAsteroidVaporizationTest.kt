package com.nenaner.aoc2019.day10

import com.nenaner.aoc2019.OutputLogger
import io.kotlintest.matchers.types.shouldNotBeNull
import io.kotlintest.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.SpyK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MonitoringStationAsteroidVaporizationTest {
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
    internal fun `Simple succession test`() {
        val asteroidMap =
                "..X..\n" +
                        ".....\n" +
                        "..#..\n" +
                        ".....\n" +
                        "..#.."
        val asteroidsVaporizedInOrder = subject.vaporizeAsteroids(asteroidMap)

        asteroidsVaporizedInOrder.size.shouldBe(2)

        val firstAsteroidVaporized = asteroidsVaporizedInOrder[1]
        firstAsteroidVaporized.shouldNotBeNull()
        firstAsteroidVaporized.first.shouldBe(2)
        firstAsteroidVaporized.second.shouldBe(2)

        val secondAsteroidVaporized = asteroidsVaporizedInOrder[2]
        secondAsteroidVaporized.shouldNotBeNull()
        secondAsteroidVaporized.first.shouldBe(2)
        secondAsteroidVaporized.second.shouldBe(4)
    }
}
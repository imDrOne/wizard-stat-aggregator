package xyz.candycrawler.wizardstataggregator.domain.stat.limited.model

import org.junit.jupiter.api.Test
import xyz.candycrawler.wizardstataggregator.domain.stat.limited.exception.InvalidCardLimitedStatsException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CardLimitedStatsTest {

    @Test
    fun `valid entity is created successfully`() {
        val stats = validStats()

        assertEquals("Lightning Bolt", stats.name)
        assertEquals(1, stats.mtgaId)
    }

    // ---- name ----

    @Test
    fun `throws when name is blank`() {
        val ex = assertFailsWith<InvalidCardLimitedStatsException> {
            validStats(name = "   ")
        }
        assertEquals("CardLimitedStats is invalid: name must not be blank", ex.message)
    }

    @Test
    fun `throws when name is empty`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(name = "") }
    }

    // ---- mtgaId ----

    @Test
    fun `throws when mtgaId is zero`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(mtgaId = 0) }
    }

    @Test
    fun `throws when mtgaId is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(mtgaId = -1) }
    }

    // ---- matchType ----

    @Test
    fun `throws when matchType is blank`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(matchType = "  ") }
    }

    // ---- url ----

    @Test
    fun `throws when url is blank`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(url = "") }
    }

    // ---- types ----

    @Test
    fun `throws when types is empty`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(types = emptyList()) }
    }

    @Test
    fun `accepts multiple types`() {
        val stats = validStats(types = listOf("Creature", "Artifact"))
        assertEquals(listOf("Creature", "Artifact"), stats.types)
    }

    // ---- counters (non-negative) ----

    @Test
    fun `throws when seenCount is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(seenCount = -1) }
    }

    @Test
    fun `accepts seenCount of zero`() {
        val stats = validStats(seenCount = 0)
        assertEquals(0, stats.seenCount)
    }

    @Test
    fun `throws when pickCount is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(pickCount = -1) }
    }

    @Test
    fun `throws when gameCount is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(gameCount = -1) }
    }

    @Test
    fun `throws when poolCount is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(poolCount = -1) }
    }

    @Test
    fun `throws when openingHandGameCount is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(openingHandGameCount = -1) }
    }

    @Test
    fun `throws when drawnGameCount is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(drawnGameCount = -1) }
    }

    @Test
    fun `throws when everDrawnGameCount is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(everDrawnGameCount = -1) }
    }

    @Test
    fun `throws when neverDrawnGameCount is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(neverDrawnGameCount = -1) }
    }

    // ---- rate fields [0, 1] ----

    @Test
    fun `throws when playRate is below 0`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(playRate = -0.01) }
    }

    @Test
    fun `throws when playRate exceeds 1`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(playRate = 1.01) }
    }

    @Test
    fun `accepts playRate at boundaries 0 and 1`() {
        val atZero = validStats(playRate = 0.0)
        val atOne = validStats(playRate = 1.0)
        assertEquals(0.0, atZero.playRate)
        assertEquals(1.0, atOne.playRate)
    }

    @Test
    fun `throws when winRate is out of range`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(winRate = 1.1) }
    }

    @Test
    fun `throws when openingHandWinRate is out of range`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(openingHandWinRate = -0.1) }
    }

    @Test
    fun `throws when drawnWinRate is out of range`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(drawnWinRate = 1.5) }
    }

    @Test
    fun `throws when everDrawnWinRate is out of range`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(everDrawnWinRate = -1.0) }
    }

    @Test
    fun `throws when neverDrawnWinRate is out of range`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(neverDrawnWinRate = 2.0) }
    }

    // ---- nullable avgSeen / avgPick ----

    @Test
    fun `accepts null avgSeen`() {
        val stats = validStats(avgSeen = null)
        assertEquals(null, stats.avgSeen)
    }

    @Test
    fun `throws when avgSeen is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(avgSeen = -0.01) }
    }

    @Test
    fun `accepts avgSeen of zero`() {
        val stats = validStats(avgSeen = 0.0)
        assertEquals(0.0, stats.avgSeen)
    }

    @Test
    fun `accepts null avgPick`() {
        val stats = validStats(avgPick = null)
        assertEquals(null, stats.avgPick)
    }

    @Test
    fun `throws when avgPick is negative`() {
        assertFailsWith<InvalidCardLimitedStatsException> { validStats(avgPick = -0.01) }
    }

    // ---- drawnImprovementWinRate (no bounds) ----

    @Test
    fun `accepts negative drawnImprovementWinRate`() {
        val stats = validStats(drawnImprovementWinRate = -0.15)
        assertEquals(-0.15, stats.drawnImprovementWinRate)
    }

    @Test
    fun `accepts drawnImprovementWinRate above 1`() {
        val stats = validStats(drawnImprovementWinRate = 1.5)
        assertEquals(1.5, stats.drawnImprovementWinRate)
    }

    // ---- copy ----

    @Test
    fun `copy triggers validation`() {
        val stats = validStats()
        assertFailsWith<InvalidCardLimitedStatsException> {
            stats.copy(winRate = 99.0)
        }
    }

    // ---- fixture ----

    private fun validStats(
        name: String = "Lightning Bolt",
        mtgaId: Int = 1,
        matchType: String = "QuickDraft",
        url: String = "https://example.com/card/1",
        types: List<String> = listOf("Instant"),
        seenCount: Int = 1000,
        avgSeen: Double? = 2.34,
        pickCount: Int = 800,
        avgPick: Double? = 3.12,
        gameCount: Int = 600,
        poolCount: Int = 820,
        openingHandGameCount: Int = 120,
        drawnGameCount: Int = 300,
        everDrawnGameCount: Int = 420,
        neverDrawnGameCount: Int = 180,
        playRate: Double = 0.73,
        winRate: Double = 0.58,
        openingHandWinRate: Double = 0.61,
        drawnWinRate: Double = 0.59,
        everDrawnWinRate: Double = 0.60,
        neverDrawnWinRate: Double = 0.54,
        drawnImprovementWinRate: Double = 0.06,
    ): CardLimitedStats = CardLimitedStats(
        id = null,
        name = name,
        mtgaId = mtgaId,
        matchType = matchType,
        color = "R",
        rarity = "common",
        url = url,
        urlBack = "https://example.com/card/1/back",
        types = types,
        layout = "normal",
        seenCount = seenCount,
        avgSeen = avgSeen,
        pickCount = pickCount,
        avgPick = avgPick,
        gameCount = gameCount,
        poolCount = poolCount,
        playRate = playRate,
        winRate = winRate,
        openingHandGameCount = openingHandGameCount,
        openingHandWinRate = openingHandWinRate,
        drawnGameCount = drawnGameCount,
        drawnWinRate = drawnWinRate,
        everDrawnGameCount = everDrawnGameCount,
        everDrawnWinRate = everDrawnWinRate,
        neverDrawnGameCount = neverDrawnGameCount,
        neverDrawnWinRate = neverDrawnWinRate,
        drawnImprovementWinRate = drawnImprovementWinRate,
    )
}

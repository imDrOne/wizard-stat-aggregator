package xyz.candycrawler.wizardstataggregator.domain.repository

import xyz.candycrawler.wizardstataggregator.domain.model.CardLimitedStats

interface CardLimitedStatsRepository {
    fun saveAll(cardStats: List<CardLimitedStats>)
    fun findByMatchType(matchType: String): List<CardLimitedStats>
    fun findByMtgaIdAndMatchType(mtgaId: Int, matchType: String): CardLimitedStats?
}

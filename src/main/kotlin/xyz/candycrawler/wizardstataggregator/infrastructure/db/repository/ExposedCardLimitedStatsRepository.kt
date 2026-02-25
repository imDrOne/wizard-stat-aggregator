package xyz.candycrawler.wizardstataggregator.infrastructure.db.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import xyz.candycrawler.wizardstataggregator.domain.model.CardLimitedStats
import xyz.candycrawler.wizardstataggregator.domain.repository.CardLimitedStatsRepository
import xyz.candycrawler.wizardstataggregator.infrastructure.db.entity.CardLimitedStatsRecord
import xyz.candycrawler.wizardstataggregator.infrastructure.db.mapper.CardLimitedStatsSqlMapper

@Repository
@Transactional
class ExposedCardLimitedStatsRepository(
    private val sqlMapper: CardLimitedStatsSqlMapper,
) : CardLimitedStatsRepository {

    override fun saveAll(cardStats: List<CardLimitedStats>) {
        sqlMapper.insertBatch(cardStats.map { it.toRecord() })
    }

    override fun findByMatchType(matchType: String): List<CardLimitedStats> =
        sqlMapper.selectByMatchType(matchType).map { it.toDomain() }

    override fun findByMtgaIdAndMatchType(mtgaId: Int, matchType: String): CardLimitedStats? =
        sqlMapper.selectByMtgaIdAndMatchType(mtgaId, matchType)?.toDomain()

    private fun CardLimitedStats.toRecord(): CardLimitedStatsRecord = CardLimitedStatsRecord(
        id = id,
        name = name,
        mtgaId = mtgaId,
        matchType = matchType,
        color = color,
        rarity = rarity,
        url = url,
        urlBack = urlBack,
        types = types,
        layout = layout,
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

    private fun CardLimitedStatsRecord.toDomain(): CardLimitedStats = CardLimitedStats(
        id = id,
        name = name,
        mtgaId = mtgaId,
        matchType = matchType,
        color = color,
        rarity = rarity,
        url = url,
        urlBack = urlBack,
        types = types,
        layout = layout,
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

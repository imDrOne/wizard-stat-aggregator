package xyz.candycrawler.wizardstataggregator.infrastructure.client.lands17.mapper

import org.springframework.stereotype.Component
import xyz.candycrawler.wizardstataggregator.domain.stat.limited.model.CardLimitedStats
import xyz.candycrawler.wizardstataggregator.infrastructure.client.lands17.dto.response.CardStatsResponse

@Component
class CardStatsResponseMapper {

    fun toDomain(response: CardStatsResponse, setCode: String, matchType: String): CardLimitedStats = CardLimitedStats(
        name = response.name,
        mtgaId = response.mtgaId,
        setCode = setCode,
        matchType = matchType,
        color = response.color,
        rarity = response.rarity,
        url = response.url,
        urlBack = response.urlBack,
        types = response.types,
        layout = response.layout,
        seenCount = response.seenCount,
        avgSeen = response.avgSeen,
        pickCount = response.pickCount,
        avgPick = response.avgPick,
        gameCount = response.gameCount,
        poolCount = response.poolCount,
        playRate = response.playRate,
        winRate = response.winRate,
        openingHandGameCount = response.openingHandGameCount,
        openingHandWinRate = response.openingHandWinRate,
        drawnGameCount = response.drawnGameCount,
        drawnWinRate = response.drawnWinRate,
        everDrawnGameCount = response.everDrawnGameCount,
        everDrawnWinRate = response.everDrawnWinRate,
        neverDrawnGameCount = response.neverDrawnGameCount,
        neverDrawnWinRate = response.neverDrawnWinRate,
        drawnImprovementWinRate = response.drawnImprovementWinRate,
    )
}

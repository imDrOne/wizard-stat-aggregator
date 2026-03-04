package xyz.candycrawler.wizardstataggregator.application.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import xyz.candycrawler.wizardstataggregator.domain.stat.limited.repository.CardLimitedStatsRepository
import xyz.candycrawler.wizardstataggregator.infrastructure.client.lands17.Lands17ApiClient.MatchType
import xyz.candycrawler.wizardstataggregator.infrastructure.client.lands17.Lands17ApiClientFacade
import xyz.candycrawler.wizardstataggregator.infrastructure.client.lands17.mapper.CardStatsResponseMapper

@Service
class CardLimitedStatsCollectionService(
    private val lands17ApiClientFacade: Lands17ApiClientFacade,
    private val cardLimitedStatsRepository: CardLimitedStatsRepository,
    private val mapper: CardStatsResponseMapper,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun collectAll(setCode: String) {
        log.info("Starting card limited stats collection for set={}", setCode)

        supervisorScope {
            launch(Dispatchers.IO) { collectForMatchType(setCode, MatchType.QUICK_DRAFT) }
            launch(Dispatchers.IO) { collectForMatchType(setCode, MatchType.SEALED) }
        }

        log.info("Card limited stats collection finished for set={}", setCode)
    }

    private suspend fun collectForMatchType(setCode: String, matchType: MatchType) {
        try {
            log.info("Fetching {} stats for set={}", matchType.value, setCode)

            val responses = when (matchType) {
                MatchType.QUICK_DRAFT -> lands17ApiClientFacade.getDraftStatistic(setCode)
                MatchType.SEALED -> lands17ApiClientFacade.getSealedStatistic(setCode)
            }

            val domain = responses.map { mapper.toDomain(it, setCode, matchType.value) }
            cardLimitedStatsRepository.saveAll(domain)

            log.info("Saved {} {} records for set={}", domain.size, matchType.value, setCode)
        } catch (e: Exception) {
            log.error("Failed to collect {} stats for set={}: {}", matchType.value, setCode, e.message, e)
        }
    }
}

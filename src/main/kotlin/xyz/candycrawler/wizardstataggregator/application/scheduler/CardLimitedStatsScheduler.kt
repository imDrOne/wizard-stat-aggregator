package xyz.candycrawler.wizardstataggregator.application.scheduler

import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import xyz.candycrawler.wizardstataggregator.application.service.CardLimitedStatsCollectionService
import xyz.candycrawler.wizardstataggregator.configuration.client.property.CardLimitedStatsSchedulerProperties

@Component
@ConditionalOnProperty(name = ["scheduler.card-limited-stats.enabled"], havingValue = "true")
class CardLimitedStatsScheduler(
    private val collectionService: CardLimitedStatsCollectionService,
    private val props: CardLimitedStatsSchedulerProperties,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "\${scheduler.card-limited-stats.cron}")
    fun collect() {
        log.info("Scheduler ${javaClass.simpleName} triggered for set=${props.setCode}")
        runBlocking { collectionService.collectAll(props.setCode) }
    }
}

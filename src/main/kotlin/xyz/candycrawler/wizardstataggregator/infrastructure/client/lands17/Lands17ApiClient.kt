package xyz.candycrawler.wizardstataggregator.infrastructure.client.lands17

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import xyz.candycrawler.wizardstataggregator.infrastructure.client.lands17.dto.response.CardStatsResponse
import java.time.LocalDate
import java.time.Year

interface Lands17ApiClient {

    @GetExchange("/card_ratings/data")
    fun getStatistic(
        @RequestParam("event_type", required = true) matchType: String,
        @RequestParam("expansion", required = true) setCode: String,
        @RequestParam(
            "start_date",
            required = true
        ) @DateTimeFormat(pattern = "yyyy-MM-dd") startDate: LocalDate = Year.now().atDay(1),
        @RequestParam(
            "end_date",
            required = true
        ) @DateTimeFormat(pattern = "yyyy-MM-dd") endDate: LocalDate = LocalDate.now()
    ): List<CardStatsResponse>

    fun getDraftStatistic(setCode: String): List<CardStatsResponse> {
        return getStatistic(
            setCode = setCode,
            matchType = MatchType.QUICK_DRAFT.value,
        )
    }

    fun getSealedStatistic(setCode: String): List<CardStatsResponse> {
        return getStatistic(
            setCode = setCode,
            matchType = MatchType.SEALED.value,
        )
    }

    enum class MatchType(val value: String) {
        QUICK_DRAFT("QuickDraft"),
        SEALED("Sealed")
    }
}

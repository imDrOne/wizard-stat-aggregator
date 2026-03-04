package xyz.candycrawler.wizardstataggregator.domain.stat.limited.exception

class CardLimitedStatsNotFoundException(msg: String) : RuntimeException(msg) {
    constructor(id: Long) : this(message("with id=$id"))

    companion object {
        fun message(details: String): String = "CardLimitedStats not found: $details"
    }
}

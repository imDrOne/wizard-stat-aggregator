package xyz.candycrawler.wizardstataggregator.domain.model

data class CardLimitedStats(
    val id: Long? = null,
    val name: String,
    val mtgaId: Int,
    val matchType: String,
    val color: String,
    val rarity: String,
    val url: String,
    val urlBack: String,
    val types: List<String>,
    val layout: String,
    val seenCount: Int,
    val avgSeen: Double?,
    val pickCount: Int,
    val avgPick: Double?,
    val gameCount: Int,
    val poolCount: Int,
    val playRate: Double,
    val winRate: Double,
    val openingHandGameCount: Int,
    val openingHandWinRate: Double,
    val drawnGameCount: Int,
    val drawnWinRate: Double,
    val everDrawnGameCount: Int,
    val everDrawnWinRate: Double,
    val neverDrawnGameCount: Int,
    val neverDrawnWinRate: Double,
    val drawnImprovementWinRate: Double,
) {
    init {
        require(name.isNotBlank()) { "name must not be blank" }
        require(mtgaId > 0) { "mtgaId must be positive" }
        require(matchType.isNotBlank()) { "matchType must not be blank" }
        require(url.isNotBlank()) { "url must not be blank" }
        require(types.isNotEmpty()) { "types must not be empty" }
        require(seenCount >= 0) { "seenCount must be non-negative" }
        require(pickCount >= 0) { "pickCount must be non-negative" }
        require(gameCount >= 0) { "gameCount must be non-negative" }
        require(poolCount >= 0) { "poolCount must be non-negative" }
        require(openingHandGameCount >= 0) { "openingHandGameCount must be non-negative" }
        require(drawnGameCount >= 0) { "drawnGameCount must be non-negative" }
        require(everDrawnGameCount >= 0) { "everDrawnGameCount must be non-negative" }
        require(neverDrawnGameCount >= 0) { "neverDrawnGameCount must be non-negative" }
        require(playRate in 0.0..1.0) { "playRate must be in [0, 1]" }
        require(winRate in 0.0..1.0) { "winRate must be in [0, 1]" }
        require(openingHandWinRate in 0.0..1.0) { "openingHandWinRate must be in [0, 1]" }
        require(drawnWinRate in 0.0..1.0) { "drawnWinRate must be in [0, 1]" }
        require(everDrawnWinRate in 0.0..1.0) { "everDrawnWinRate must be in [0, 1]" }
        require(neverDrawnWinRate in 0.0..1.0) { "neverDrawnWinRate must be in [0, 1]" }
        avgSeen?.let { require(it >= 0.0) { "avgSeen must be non-negative" } }
        avgPick?.let { require(it >= 0.0) { "avgPick must be non-negative" } }
    }
}

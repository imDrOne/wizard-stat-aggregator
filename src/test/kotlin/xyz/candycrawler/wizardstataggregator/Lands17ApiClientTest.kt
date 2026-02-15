package xyz.candycrawler.wizardstataggregator

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import xyz.candycrawler.wizardstataggregator.infrastructure.client.lands17.Lands17ApiClient

@SpringBootTest
class Lands17ApiClientTest @Autowired constructor(
    private val apiClient: Lands17ApiClient
) {

    @Test
    fun contextLoads() {
        val result = apiClient.getDraftStatistic("ECL")
        println(result)
    }

}

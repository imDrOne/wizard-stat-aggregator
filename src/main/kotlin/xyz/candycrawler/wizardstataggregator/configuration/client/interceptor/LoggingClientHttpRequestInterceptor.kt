package xyz.candycrawler.wizardstataggregator.configuration.client.interceptor

import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse

class LoggingClientHttpRequestInterceptor : ClientHttpRequestInterceptor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution,
    ): ClientHttpResponse {
        val startMs = System.currentTimeMillis()

        log.debug("--> {} {}", request.method, request.uri)

        val response = execution.execute(request, body)

        val elapsedMs = System.currentTimeMillis() - startMs
        log.debug("<-- {} {} ({}ms)", response.statusCode, request.uri, elapsedMs)

        return response
    }
}

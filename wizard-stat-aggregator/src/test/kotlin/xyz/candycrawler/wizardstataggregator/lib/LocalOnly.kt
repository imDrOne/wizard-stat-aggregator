package xyz.candycrawler.wizardstataggregator.lib

import org.junit.jupiter.api.Disabled

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Disabled("Local only")
annotation class LocalOnly

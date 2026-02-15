package xyz.candycrawler.wizardstataggregator

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<WizardStatAggregatorApplication>().with(TestcontainersConfiguration::class).run(*args)
}

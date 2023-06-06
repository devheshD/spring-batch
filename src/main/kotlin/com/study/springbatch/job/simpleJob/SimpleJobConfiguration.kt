package com.study.springbatch.job.simpleJob

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleJobConfiguration(
    private val job: JobBuilderFactory,
    private val steps: StepBuilderFactory,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun simpleJob(): Job =
        job.get("simpleJob")
        .start(simpleStep())
        .build()

    @Bean
    fun simpleStep(): Step =
        steps["simpleStep"]
        .tasklet { _: StepContribution, _: ChunkContext ->
            logger.info("======= Start spring batch!! =======")

            RepeatStatus.FINISHED
        }.build()
}

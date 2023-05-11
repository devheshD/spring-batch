package com.study.springbatch.job.simpleParameterJob

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
class SimpleParameterJobConfiguration(
    private val job: JobBuilderFactory,
    private val steps: StepBuilderFactory,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun simpleParameterJob(): Job {
        return job["simpleParameterJob"]
            .start(parameterStep1())
            .next(parameterStep2())
            .build()
    }

    @Bean
    fun parameterStep1(): Step =
        steps["parameterStep1"].tasklet { stepContribution: StepContribution, chunkContext: ChunkContext ->
            val stepContributionJobParameters = stepContribution.stepExecution.jobExecution.jobParameters
            logger.info("jobParameter(String) : ${stepContributionJobParameters.getString("name")}")
            logger.info("jobParameter(Long) : ${stepContributionJobParameters.getLong("seq")}")
            logger.info("jobParameter(Date) : ${stepContributionJobParameters.getDate("date")}")
            logger.info("jobParameter(Double) : ${stepContributionJobParameters.getDouble("age")}")

            logger.info("======================")

            val chunkContextJobParameters = chunkContext.stepContext.jobParameters
            logger.info("jobParameter(String) : ${chunkContextJobParameters["name"]}")
            logger.info("jobParameter(Long) : ${chunkContextJobParameters["seq"]}")
            logger.info("jobParameter(Date) : ${chunkContextJobParameters["date"]}")
            logger.info("jobParameter(Double) : ${chunkContextJobParameters["age"]}")

            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun parameterStep2(): Step =
        steps["parameterStep2"].tasklet { _: StepContribution, _: ChunkContext ->
            RepeatStatus.FINISHED
        }.build()
}

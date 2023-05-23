package com.study.springbatch.job.executionContext

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
import java.lang.RuntimeException

@Configuration
class ExecutionContextConfiguration(
    private val job: JobBuilderFactory,
    private val steps: StepBuilderFactory,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Bean
    fun executionContextJob(): Job {
        return job["executionContextJob"]
            .start(step1())
            .next(step2())
            .next(step3())
            .next(step4())
            .build()
    }

    @Bean
    fun step1(): Step =
        steps["step1"].tasklet { stepContribution: StepContribution, chunkContext: ChunkContext ->
            logger.info("Step1 was executed")
            val jobExecutionContext = stepContribution.stepExecution.jobExecution.executionContext
            val stepExecutionContext = stepContribution.stepExecution.executionContext

            val jobName = chunkContext.stepContext.stepExecution.jobExecution.jobInstance.jobName
            val stepName = chunkContext.stepContext.stepExecution.stepName

            if (jobExecutionContext["jobName"] == null) {
                jobExecutionContext.put("jobName", jobName)
            }

            if (stepExecutionContext["stepName"] == null) {
                stepExecutionContext.put("stepName", stepName)
            }

            logger.info("jobName : ${jobExecutionContext[jobName]}")
            logger.info("stepName : ${stepExecutionContext[stepName]}")

            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun step2(): Step =
        steps["step2"].tasklet { stepContribution: StepContribution, chunkContext: ChunkContext ->
            logger.info("Step2 was executed")

            val jobExecutionContext = chunkContext.stepContext.stepExecution.jobExecution.executionContext
            val stepExecutionContext = chunkContext.stepContext.stepExecution.executionContext

            logger.info("jobName : ${jobExecutionContext["jobName"]}")
            logger.info("stepName : ${stepExecutionContext["stepName"]}")

            val stepName = chunkContext.stepContext.stepExecution.stepName
            if (stepExecutionContext["stepName"] == null) {
                stepExecutionContext.put("stepName", stepName)
            }

            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun step3(): Step =
        steps["step3"].tasklet { _: StepContribution, chunkContext: ChunkContext ->
            logger.info("Step3 was executed")

            val name = chunkContext.stepContext.stepExecution.jobExecution.executionContext["name"]
            if (name == null) {
                chunkContext.stepContext.stepExecution.jobExecution.executionContext.put("name", "user1")
                throw RuntimeException("step2 was failed")
            }
            RepeatStatus.FINISHED
        }.build()

    @Bean
    fun step4(): Step =
        steps["step4"].tasklet { _: StepContribution, chunkContext: ChunkContext ->
            logger.info("Step4 was executed")
            val name = chunkContext.stepContext.stepExecution.jobExecution.executionContext["name"]
            logger.info("name : $name")

            RepeatStatus.FINISHED
        }.build()
}

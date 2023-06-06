package com.study.springbatch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import java.util.*
import kotlin.system.exitProcess

@EnableBatchProcessing
@SpringBootApplication
class SpringBatchApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchApplication>(*args).runBatch(args)
}

private fun ConfigurableApplicationContext.runBatch(args: Array<String>) {
    val jobName = args[0]
    val jobParameter = args[1]
    val job = getBean(jobName, Job::class.java)
    val jobParametersBuilder = JobParametersBuilder()
            .addLong("jobParameter", jobParameter.toLong())
            .toJobParameters()

    getBean(JobLauncher::class.java).run(job, jobParametersBuilder)
}

package com.study.springbatch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersIncrementer
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext
import java.util.Date

@EnableBatchProcessing
@SpringBootApplication
class SpringBatchApplication : JobParametersIncrementer {
    override fun getNext(parameters: JobParameters?): JobParameters =
            JobParametersBuilder()
                    .addDate("jobParameterDate", Date())
                    .toJobParameters()
}

fun main(args: Array<String>) {
    runApplication<SpringBatchApplication>(*args).runBatch(args)
}

private fun ConfigurableApplicationContext.runBatch(args: Array<String>) {
    val jobName = args[0]
    val job = getBean(jobName, Job::class.java)

    getBean(JobLauncher::class.java).run(job, SpringBatchApplication().getNext(null))
}

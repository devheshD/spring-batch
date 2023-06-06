//package com.study.springbatch
//
//import org.springframework.batch.core.Job
//import org.springframework.batch.core.JobParametersBuilder
//import org.springframework.batch.core.launch.JobLauncher
//import org.springframework.boot.ApplicationArguments
//import org.springframework.boot.ApplicationRunner
//import org.springframework.stereotype.Component
//import java.util.*
//
//@Component
//class JobRunner(
//    private val jobLauncher: JobLauncher,
//    private val job: Job,
//): ApplicationRunner {
//    override fun run(args: ApplicationArguments?) {
//        val jobParametersBuilder = JobParametersBuilder()
//            .addString("name", "user2")
//            .addLong("seq", 1)
//            .addDate("date", Date())
//            .addDouble("age", 11.2)
//            .toJobParameters()
//
//        jobLauncher.run(job, jobParametersBuilder)
//    }
//}

package com.example.lab1.config

import com.example.lab1.jobs.CheckOrdersJob
import com.example.lab1.jobs.CleanOrdersJob
import org.quartz.Scheduler
import org.quartz.JobBuilder
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.quartz.SimpleScheduleBuilder.*

@Configuration
class QuartzConfig {

    @Bean
    fun scheduler() : Scheduler {
        val scheduler : Scheduler = StdSchedulerFactory().scheduler
        val checkOrdersJob = JobBuilder.newJob().ofType(CheckOrdersJob::class.java)
                                                .storeDurably()
                                                .withIdentity("CheckOrdersJob")
                                                .withDescription("Check orders' product")
                                                .build()
        val cleanOrdersJob = JobBuilder.newJob().ofType(CleanOrdersJob::class.java)
                                                .storeDurably()
                                                .withIdentity("CleanOrdersJob_Job_Detail")
                                                .withDescription("Clean pending orders")
                                                .build()

        val trigger1 = TriggerBuilder.newTrigger().forJob(checkOrdersJob)
                                        .withIdentity("Qrtz_Trigger1")
                                        .withDescription("Sample trigger1")
                                        .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(5))
                                        .build()

        val trigger2 = TriggerBuilder.newTrigger().forJob(cleanOrdersJob)
                .withIdentity("Qrtz_Trigger2")
                .withDescription("Sample trigger2")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(5))
                .build()
        scheduler.scheduleJob(checkOrdersJob, trigger1)
        scheduler.scheduleJob(cleanOrdersJob, trigger2)
        scheduler.start()
        return scheduler
    }
}
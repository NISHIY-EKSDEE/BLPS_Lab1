package com.example.lab1.config

import com.example.lab1.jobs.CheckOrdersJob
import com.example.lab1.jobs.CleanOrdersJob
import org.quartz.Job
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

        scheduleJobWithInterval(scheduler, JobInfo(CheckOrdersJob::class.java, "CheckOrdersJob", "Check orders' product"), TriggerInfo("Qrtz_Trigger1", "Sample trigger1", 1))
        scheduleJobWithInterval(scheduler, JobInfo(CleanOrdersJob::class.java, "CleanOrdersJob_Job_Detail", "Clean pending orders"), TriggerInfo("Qrtz_Trigger2", "Sample trigger2", 24))

        scheduler.start()
        return scheduler
    }

    fun scheduleJobWithInterval(scheduler: Scheduler, jobInfo: JobInfo, triggerInfo: TriggerInfo ) {
        val job = JobBuilder.newJob().ofType(jobInfo.jobClass)
            .storeDurably()
            .withIdentity(jobInfo.identity)
            .withDescription(jobInfo.description)
            .build()

        val trigger = TriggerBuilder.newTrigger().forJob(job)
            .withIdentity(triggerInfo.identity)
            .withDescription(triggerInfo.description)
            .withSchedule(simpleSchedule().repeatForever().withIntervalInHours(triggerInfo.interval))
            .build()

        scheduler.scheduleJob(job, trigger)
    }

    data class JobInfo (val jobClass: Class<out Job>, val identity: String, val description: String)
    data class TriggerInfo (val identity: String, val description: String, val interval: Int)
}
package com.example.lab1.config

import com.example.lab1.config.info.job.CheckOrdersJobInfo
import com.example.lab1.config.info.job.CleanOrdersJobInfo
import com.example.lab1.config.info.job.JobInfo
import com.example.lab1.config.info.trigger.CheckOrdersTriggerInfo
import com.example.lab1.config.info.trigger.CleanOrdersTriggerInfo
import com.example.lab1.config.info.trigger.TriggerInfo
import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzConfig {

    @Autowired
    lateinit var quartzConfigService: QuartzConfigService

    @Bean
    fun scheduler() : Scheduler {
        val scheduler : Scheduler = StdSchedulerFactory().scheduler

        scheduleJob(scheduler, CheckOrdersJobInfo, CheckOrdersTriggerInfo)
        scheduleJob(scheduler, CleanOrdersJobInfo, CleanOrdersTriggerInfo)

        scheduler.start()
        return scheduler
    }

    private fun scheduleJob(scheduler: Scheduler, jobInfo: JobInfo, triggerInfo: TriggerInfo) {
        val jobDetails = quartzConfigService.createJobDetails(jobInfo)
        val trigger = quartzConfigService.createTrigger(jobDetails, triggerInfo)

        scheduler.scheduleJob(jobDetails, trigger)
    }


}
package com.example.lab1.config

import com.example.lab1.config.info.job.JobInfo
import com.example.lab1.config.info.trigger.TriggerInfo
import org.quartz.*
import org.springframework.stereotype.Service

@Service
class QuartzConfigService {

    fun createJobDetails(jobInfo: JobInfo): JobDetail {
        return createJobDetails(jobInfo.getJobClazz(), jobInfo.getIdentityName(), jobInfo.getJobDescription())
    }

    fun createTrigger(jobDetail: JobDetail, triggerInfo: TriggerInfo): SimpleTrigger {
        return createTrigger(
                jobDetail,
                triggerInfo.getIdentityName(),
                triggerInfo.getTriggerDescription(),
                triggerInfo.getIntervalInHours()
        )
    }

    private fun createJobDetails(jobClazz: Class<out Job>, identityName: String, jobDescription: String): JobDetail {
        return JobBuilder.newJob().ofType(jobClazz)
                .storeDurably()
                .withIdentity(identityName)
                .withDescription(jobDescription)
                .build()
    }

    private fun createTrigger(
            jobDetail: JobDetail, identityName: String, triggerDescription: String, intervalInHours: Int
    ): SimpleTrigger {
        return TriggerBuilder.newTrigger().forJob(jobDetail)
                .withIdentity(identityName)
                .withDescription(triggerDescription)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInHours(intervalInHours))
                .build()
    }

}
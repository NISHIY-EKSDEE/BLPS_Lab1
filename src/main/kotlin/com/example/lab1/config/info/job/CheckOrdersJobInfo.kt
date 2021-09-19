package com.example.lab1.config.info.job

import com.example.lab1.jobs.CheckOrdersJob
import org.quartz.Job

object CheckOrdersJobInfo : JobInfo {
    private val jobClazz: Class<out Job> = CheckOrdersJob::class.java
    private val identityName: String = "CheckOrdersJob"
    private val jobDescription: String = "Check orders' product"

    override fun getJobClazz(): Class<out Job> {
        return jobClazz
    }

    override fun getIdentityName(): String {
        return identityName
    }

    override fun getJobDescription(): String {
        return jobDescription
    }
}
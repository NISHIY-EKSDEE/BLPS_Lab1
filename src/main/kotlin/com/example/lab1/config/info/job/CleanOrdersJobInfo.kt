package com.example.lab1.config.info.job

import com.example.lab1.jobs.CleanOrdersJob
import org.quartz.Job

object CleanOrdersJobInfo : JobInfo {
    private val jobClazz: Class<out Job> = CleanOrdersJob::class.java
    private val identityName: String = "CleanOrdersJob_Job_Detail"
    private val jobDescription: String = "Clean pending orders"

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
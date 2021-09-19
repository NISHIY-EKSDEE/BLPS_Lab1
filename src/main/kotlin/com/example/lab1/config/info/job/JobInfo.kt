package com.example.lab1.config.info.job

import org.quartz.Job

interface JobInfo {
    fun getJobClazz() : Class<out Job>
    fun getIdentityName() : String
    fun getJobDescription() : String
}
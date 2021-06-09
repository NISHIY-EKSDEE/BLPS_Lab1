package com.example.lab1.jobs

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
class CheckOrdersJob : Job {
    override fun execute(p0: JobExecutionContext) {
        print("CheckOrdersJob TEEEEEEEEEEEEEEEEEEEEEEEEST")
    }
}
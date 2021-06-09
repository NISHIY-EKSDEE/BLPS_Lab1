package com.example.lab1.jobs

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@Component
class CleanOrdersJob : Job {
    override fun execute(p0: JobExecutionContext) {
        print("CleanOrdersJob lorem ipsum 4 korolya")
    }
}
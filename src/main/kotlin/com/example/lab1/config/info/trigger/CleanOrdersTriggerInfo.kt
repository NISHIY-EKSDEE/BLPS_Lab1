package com.example.lab1.config.info.trigger

object CleanOrdersTriggerInfo : TriggerInfo {
    private val identiryName: String = "Qrtz_Trigger2"
    private val intervalInHours: Int = 24
    private val triggerDescription: String = "Sample trigger2"


    override fun getIdentityName(): String {
        return identiryName
    }

    override fun getIntervalInHours(): Int {
        return intervalInHours
    }

    override fun getTriggerDescription(): String {
        return triggerDescription
    }
}
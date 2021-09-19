package com.example.lab1.config.info.trigger

object CheckOrdersTriggerInfo : TriggerInfo {
    private val identiryName: String = "Qrtz_Trigger1"
    private val intervalInHours: Int = 1
    private val triggerDescription: String = "Sample trigger1"


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
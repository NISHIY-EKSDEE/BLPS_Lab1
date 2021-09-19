package com.example.lab1.config.info.trigger

interface TriggerInfo {
    fun getIdentityName() : String
    fun getIntervalInHours() : Int
    fun getTriggerDescription() : String
}
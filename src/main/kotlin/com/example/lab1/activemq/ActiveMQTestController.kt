package com.example.lab1.activemq

import com.google.gson.Gson
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ActiveMQTestController(var shopNotifier: ShopNotifier) {

    data class Message(val name: String, val text: String)

    @GetMapping("/ping")
    fun ping() {
        shopNotifier.notifyShop(Gson().toJson(Message("header", "text")));
    }

}
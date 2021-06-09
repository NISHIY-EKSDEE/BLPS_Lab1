package com.example.lab1.controller.delivery

import com.example.lab1.dto.OrderResponse
import com.example.lab1.dto.OrderResponseAssembler
import com.example.lab1.dto.StatusRequest
import com.example.lab1.entities.Order
import com.example.lab1.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/delivery/orders")
class OrdersDeliveryController(
        private var orderService: OrderService
) {
    /**
     * Этот метод используется DELIVERY для того, чтобы изменить статус заказа.
     */
    @PutMapping("/{orderId}/status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeStatus(@PathVariable orderId: Int, @RequestBody statusRequest: StatusRequest) {
        orderService.changeStatus(orderId, statusRequest.statusId)
    }

    @GetMapping
    fun getAll() : List<OrderResponse>{
        val all: List<Order> = orderService.getAllOrders()
        return all.map(OrderResponseAssembler::buildDto)
    }

    @GetMapping("/{orderId}")
    fun getOne(@PathVariable orderId: Int) : OrderResponse {
        val order = orderService.getOne(orderId)

        return OrderResponseAssembler.buildDto(order)
    }
}
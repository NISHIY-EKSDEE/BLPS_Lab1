package com.example.lab1.controller

import com.example.lab1.dto.OrderDTO
import com.example.lab1.entities.OrderProductsEntity
import com.example.lab1.entities.ProductsEntity
import com.example.lab1.service.OrderService
import com.sun.org.apache.xpath.internal.operations.Bool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/orders")
class OrdersController {

    @Autowired
    lateinit var orderService: OrderService

    @GetMapping
    fun list() : List<OrderDTO> {
        return orderService.findOrdersByUserId(getUserId())
    }

    @GetMapping("/{orderId}")
    fun getOne(@PathVariable orderId: Long): OrderDTO {
        return orderService.findOrderById(orderId)
    }

    @PostMapping
    fun makeOrder(@RequestBody form : OrderForm) {
        TODO()
    }

    @PutMapping
    fun updateOrder() {
        TODO()
    }

    //TODO изменить когда появятся роли
    private fun getUserId(): Long {
        return 1;
    }

    data class OrderForm(
            val products : List<OrderProductsEntity>,
            val isDelivery : Boolean,
            val pickUpPointId : Int?,
            val deliveryAddress: String?,
            val paymentMethodId: Int
    ) {

    }

}
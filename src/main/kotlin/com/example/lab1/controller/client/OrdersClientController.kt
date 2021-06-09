package com.example.lab1.controller.client

import com.example.lab1.dto.OrderRequest
import com.example.lab1.dto.OrderResponse
import com.example.lab1.dto.OrderResponseAssembler
import com.example.lab1.entities.Order
import com.example.lab1.service.OrderService
import io.swagger.annotations.ApiOperation
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/client/orders")
class OrdersClientController(
        private var orderService: OrderService
) {

    /**
     * Этот метод вызывается, когда CLIENT хочет получить список своих заказов.
     */
    @ApiOperation(value = "\${OrdersController.listByUser()}")
    @GetMapping
    fun list() : List<OrderResponse> {
        val all: List<Order> = orderService.getUsersOrders()
        return all.map(OrderResponseAssembler::buildDto)
    }

    /**
     * Этот метод вызывается, когда CLIENT хочет получить подробную информацию о своём заказе.
     */
    @GetMapping("/{orderId}")
    fun getOne(@PathVariable orderId: Int) : OrderResponse {
        val order = orderService.getUsersOrder(orderId)

        return OrderResponseAssembler.buildDto(order)
    }

    /**
     * Этот метод вызывается, когда CLIENT делает заказ.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun makeOrder(@RequestBody orderRequest: OrderRequest) : OrderResponse {
        val order : Order = orderService.makeOrder(orderRequest)

        return OrderResponseAssembler.buildDto(order)
    }
}
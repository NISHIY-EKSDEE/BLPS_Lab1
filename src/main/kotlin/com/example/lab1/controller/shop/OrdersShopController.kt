package com.example.lab1.controller.shop

import com.example.lab1.dto.OrderForShopResponse
import com.example.lab1.dto.OrderForShopResponseAssembler
import com.example.lab1.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/shop/orders")
class OrdersShopController(
        private var orderService: OrderService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getAll() : List<OrderForShopResponse> {
        return orderService.getAllOrders().map(OrderForShopResponseAssembler::buildDto)
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    fun getOne(@PathVariable orderId: Int) : OrderForShopResponse {
        return OrderForShopResponseAssembler.buildDto(orderService.getShopsOrder(orderId))
    }

    @PutMapping("/{orderId}/{orderProductId}/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun changeProductStatus(
            @PathVariable orderId: Int,
            @PathVariable orderProductId: Int,
            @PathVariable status: String
    ) {
        orderService.changeProductStatusInOrder(orderId, orderProductId, status)
    }
}
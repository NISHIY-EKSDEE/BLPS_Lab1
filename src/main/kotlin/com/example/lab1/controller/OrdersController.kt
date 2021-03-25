package com.example.lab1.controller


import com.example.lab1.dto.OrderDTO
import com.example.lab1.entities.*
import com.example.lab1.exception.WrongRequestException
import com.example.lab1.service.OrderService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
    fun getOne(@PathVariable orderId: Int): OrderDTO {
        return orderService.findOrderById(orderId)
    }

    @PostMapping
    fun makeOrder(@RequestBody form : OrderForm) : ResponseEntity<OrderDTO> {
        val order: OrdersEntity = form.getEntity()
        order.date = java.sql.Date(System.currentTimeMillis())
        order.usersByUserId = UsersEntity().apply { id = getUserId().toInt() } //TODO изменить когдя появятся роли

        validateProductsInOrder(form.products)


        return ResponseEntity(
                orderService.saveOrderEntity(order),
                HttpStatus.CREATED
        )
    }

    @PutMapping
    fun updateOrder() {
        TODO()
    }

    //TODO изменить когда появятся роли
    private fun getUserId(): Long {
        return 1;
    }

    private fun validateProductsInOrder(list: List<OrderProductsEntity>) {
        if (list.isEmpty()) throw WrongRequestException("The bucket can't be empty!")
    }


    data class OrderForm(
            val products : List<OrderProductsEntity>,
            val isDelivery : Boolean,
            val pickUpPointId : Int?,
            val deliveryAddress: String?,
            val paymentMethodId: Int,
            val orderStatusId: Int
    ) {
        fun getEntity() : OrdersEntity {
            val order = OrdersEntity()
            order.deliveryAddress = deliveryAddress
            order.isDelivery = isDelivery

            order.pickupPointsByPickupPointId = if (pickUpPointId != null) PickupPointsEntity().apply {
                this.id = pickUpPointId
            }  else null
            order.paymentMethodsByPaymentMethodId = PaymentMethodsEntity().apply {
                this.id = paymentMethodId
            }
            order.orderProductsById = products
            order.orderStatusByStatusId = OrderStatusEntity().apply {
                this.id = orderStatusId
            }

            return order
        }
    }

}
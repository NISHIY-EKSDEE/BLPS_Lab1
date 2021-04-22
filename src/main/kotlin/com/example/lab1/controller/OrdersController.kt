package com.example.lab1.controller


import com.example.lab1.dto.BucketProductDTO
import com.example.lab1.dto.OrderAssembler
import com.example.lab1.dto.OrderDTO
import com.example.lab1.dto.OrderShortDTO
import com.example.lab1.entities.*
import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.exception.WrongRequestException
import com.example.lab1.service.OrderProductService
import com.example.lab1.service.OrderService
import com.example.lab1.service.UserService
import io.swagger.annotations.ApiOperation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.transaction.support.TransactionTemplate


@RestController
@RequestMapping("/api/orders")
class OrdersController {

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var orderProductsService: OrderProductService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var template: TransactionTemplate

    /**
     * Этот метод вызывается, когда CLIENT хочет получить список своих заказов.
     */
    @ApiOperation(value = "\${OrdersController.listByUser()}")
    @GetMapping
    fun listByUser() : List<OrderDTO> {
        return orderService.findOrdersByUserId(getUserId())
    }

    /**
     * По-хорошему этот метод вызывается DELIVERY для того, чтобы увидеть какие заказы были сделаны.
     * Возможна фильтрация по статусу.
     */
    @GetMapping("/all")
    fun list() : List<OrderDTO> {
        TODO()
    }

    /**
     * Этот метод вызываетсся, когда CLIENT хочет получить подробную информацию о своём заказе.
     */
    @GetMapping("/{orderId}")
    fun getOne(@PathVariable orderId: Int): OrderDTO {
        val orders = orderService.findOrdersByUserId(getUserId())
        val order = orders.firstOrNull { it.id == orderId } ?:
            throw ResourceNotFoundException("У данного пользователя нет товара с таким id!")

        return order
    }

    /**
     * [НЕ ИСПОЛЬЗУЕТСЯ]
     */
    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOne(@PathVariable orderId: Int) {
        orderService.deleteById(orderId)
    }

    /**
     * Этот метод вызывается, когда CLIENT делает заказ.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun makeOrder(@RequestBody form : OrderForm) : OrderShortDTO {
        return template.execute {
            var order: OrderEntity = form.getEntity()
            order.date = java.sql.Date(System.currentTimeMillis())
            order.userByUserId = User().apply { id = getUserId().toInt().toLong() }

            validateProductsInOrder(order.orderProductsById?.toList()!!)
            order = orderService.saveOrderEntityAndRetEntity(order)

            order.orderProductsById!!.map {
                orderProductsService.save(it)
            }

            print("order id: " + order.id)

            OrderAssembler.buildShortDto(order)
        } ?: throw Exception("Makeorder error")
    }

    /**
     * Этот метод используется DELIVERY для того, чтобы изменить статус заказа.
     */
    @PutMapping
    fun updateOrder() {
        TODO()
    }

    private fun getUserId(): Long {
        val userDetails: UserDetails = SecurityContextHolder.getContext().authentication.principal as UserDetails
        val user : User = userService.getUserByUsername(userDetails.username) ?:
            throw ResourceNotFoundException("User not found!")

        return user.id;
    }

    private fun validateProductsInOrder(list: List<OrderProductsEntity>) {
        if (list.isEmpty()) throw WrongRequestException("The bucket can't be empty!")
    }

    data class OrderForm(
            val products : List<BucketProductDTO>,
            val isDelivery : Boolean,
            val pickUpPointId : Int?,
            val deliveryAddress: String?,
            val paymentMethodId: Int,
            val orderStatusId: Int
    ) {
        fun getEntity() : OrderEntity {
            val order = OrderEntity()
            order.deliveryAddress = deliveryAddress
            order.isDelivery = isDelivery

            order.pickupPointsByPickupPointId = if (pickUpPointId != null) PickupPointEntity().apply {
                this.id = pickUpPointId
            }  else null
            order.paymentMethodsByPaymentMethodId = PaymentMethodEntity().apply {
                this.id = paymentMethodId
            }
            order.orderProductsById = products.map {
                OrderProductsEntity().apply {
                    val sellerProduct = SellerProductsEntity()
                    sellerProduct.id = it.sellerProductId
                    val status = OrderProductStatusEntity()
                    status.id = 1


                    this.orderByOrderId = order
                    this.quantity = it.quantity
                    this.sellerProductsBySellerProductId = sellerProduct
                    this.orderProductStatusByStatusId = status
                }
            }
            order.orderStatusByStatusId = OrderStatusEntity().apply {
                this.id = orderStatusId
            }

            return order
        }
    }

}
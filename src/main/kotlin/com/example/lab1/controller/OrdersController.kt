package com.example.lab1.controller


import com.example.lab1.dto.BucketProductDTO
import com.example.lab1.dto.OrderAssembler
import com.example.lab1.dto.OrderDTO
import com.example.lab1.dto.OrderShortDTO
import com.example.lab1.entities.*
import com.example.lab1.exception.WrongRequestException
import com.example.lab1.service.OrderProductService
import com.example.lab1.service.OrderService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


@RestController
@RequestMapping("/api/orders")
class OrdersController {

    @Autowired
    lateinit var orderService: OrderService

    @Autowired
    lateinit var orderProductsService: OrderProductService

    @PersistenceContext
    private val entityManager: EntityManager? = null

    @GetMapping
    fun list() : List<OrderDTO> {
        return orderService.findOrdersByUserId(getUserId())
    }

//    @GetMapping
//    fun getAll() {
//        TODO()
//    }

    @GetMapping("/{orderId}")
    fun getOne(@PathVariable orderId: Int): OrderDTO {
        return orderService.findOrderById(orderId)
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOne(@PathVariable orderId: Int) {
        orderService.deleteById(orderId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun makeOrder(@RequestBody form : OrderForm) : OrderShortDTO {
        var order: OrderEntity = form.getEntity()
        order.date = java.sql.Date(System.currentTimeMillis())
        order.userByUserId = UserEntity().apply { id = getUserId().toInt() } //TODO изменить когдя появятся роли

        validateProductsInOrder(order.orderProductsById?.toList()!!)
        order = orderService.saveOrderEntityAndRetEntity(order)

        order.orderProductsById!!.map {
            orderProductsService.save(it)
        }

        print("order id: " + order.id)
        //Thread.sleep(5000)
        //val dto = getOne(orderId = order.id) //orderService.findOrderById(order.id)

        return OrderAssembler.buildShortDto(order)
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
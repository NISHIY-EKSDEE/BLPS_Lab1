package com.example.lab1.service

import com.example.lab1.dto.BucketProductDTO
import com.example.lab1.dto.OrderRequest
import com.example.lab1.entities.*
import com.example.lab1.exception.ResourceNotFoundException
import com.example.lab1.exception.WrongRequestException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class OrderService(
        private var orderProvider : OrderProvider,
        private var orderStatusProvider: OrderStatusProvider,
        private var orderProductsProvider: OrderProductsProvider,
        private var orderProductStatusProvider: OrderProductStatusProvider,
        private var userContextService: UserContextService
) {

    @Autowired
    lateinit var template: TransactionTemplate

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    fun getAllOrders() : List<Order> {
        return orderProvider.getAll();
    }

    fun getAllUnconfirmedOrders() : List<Order> {
        val unconfirmedStatus = orderStatusProvider.get(4)
        return orderProvider.getAllByStatus(unconfirmedStatus);
    }

    fun getOne(orderId: Int) : Order {
        return orderProvider.get(orderId)
    }

    fun getShopsOrders() : List<Order> {
        val shop : User = userContextService.getUser()
        val orders : List<Order> = orderProvider.getAll()

        val shopsOrders = orders.filter { checkIfOrderContainsAnyShopsProducts(it, shop.id) }

        if (shopsOrders.isNotEmpty()) {
            return shopsOrders
        }
        else {
            throw ResourceNotFoundException("No orders for this shop were found!")
        }
    }

    fun getShopsOrder(orderId : Int): Order {
        val shop : User = userContextService.getUser()
        val order: Order = orderProvider.get(orderId)

        val containsShopsProducts = checkIfOrderContainsAnyShopsProducts(order, shop.id)

        if (containsShopsProducts) {
            return order
        }
        else {
            throw WrongRequestException("This order doesn't contain any shop's goods!")
        }
    }

    private fun checkIfOrderContainsAnyShopsProducts(order: Order, shopId: Int) : Boolean {
        return order.products
                .any { orderProduct -> orderProduct.product.seller.user.id == shopId }
    }

    fun getUsersOrders(): List<Order> {
        val client: User = userContextService.getUser()

        return orderProvider.getAllByUserId(client.id)
    }

    fun getUsersOrder(orderId: Int): Order {
        val client: User = userContextService.getUser()
        val order: Order = orderProvider.get(orderId)

        if (client.id == order.client.id) {
            return order
        } else {
            throw WrongRequestException("This order doesn't belong to this client!")
        }
    }

    fun changeStatus(orderId: Int, statusId: Int): Order {
        val order: Order = orderProvider.get(orderId)
        val status: OrderStatus = orderStatusProvider.get(statusId)

        order.status = status

        return orderProvider.saveOrder(order)
    }

    fun makeOrder(orderRequest: OrderRequest): Order {
        return template.execute {
            val user : User = userContextService.getUser()
            var order : Order = orderRequest.getEntity().apply { client = user }
            order = orderProvider.saveOrder(order)

            val products = saveProductsInOrder(order, orderRequest.products)
            order.products = products

            refreshOrder(order)

            order
        } ?: throw RuntimeException("Order wasn't created! ")
    }

    private fun saveProductsInOrder(order: Order, orders : List<BucketProductDTO>) : List<OrderProducts> {
        return orders.map {
            val prod = orderProductsProvider.save(
                    OrderProducts(
                            quantity = it.quantity,
                            product = SellersProduct(id = it.sellerProductId),
                            order = order,
                            orderStatus = OrderProductStatus(id = 1)
                    )
            )

            refreshOrderProduct(prod)

            prod
        }
    }

    private fun refreshOrder(order: Order) {
        entityManager.refresh(order)
    }

    private fun refreshOrderProduct(product: OrderProducts) {
        entityManager.refresh(product)
    }

    fun changeProductStatusInOrder(orderId: Int, orderProductId: Int, status: String) {
        val status = orderProductStatusProvider.getByName(status)
        val orderProduct = orderProductsProvider.getById(orderProductId)

        if (orderProduct.order.id != orderId) {
            throw WrongRequestException("This products isn't from this order!")
        }

        orderProduct.orderStatus = status

        orderProductsProvider.save(orderProduct)

    }


}
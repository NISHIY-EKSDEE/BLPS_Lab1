package com.example.lab1.entities

import javax.persistence.*

@Entity
@Table(name = "users", schema = "s265098", catalog = "studs")
class UserEntity {
    @get:Column(name = "id")
    @get:Id
    @get:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0

    @get:Column(name = "login")
    @get:Basic
    var login: String? = null

    @get:Column(name = "password")
    @get:Basic
    var password: String? = null

    @get:OneToMany(mappedBy = "userByUserId")
    var orderById: Collection<OrderEntity>? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as UserEntity
        if (id != that.id) return false
        if (if (login != null) login != that.login else that.login != null) return false
        return !if (password != null) password != that.password else that.password != null
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + if (login != null) login.hashCode() else 0
        result = 31 * result + if (password != null) password.hashCode() else 0
        return result
    }
}
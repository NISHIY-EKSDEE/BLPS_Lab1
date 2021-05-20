package com.example.lab1.entities

import org.springframework.security.core.GrantedAuthority


/**
 * ROLE_ADMIN:
 * ROLE_CLIENT:
 * ROLE_DELIVERY:
 * ROLE_SHOP:
 */
enum class Role : GrantedAuthority {
    ROLE_ADMIN,
    ROLE_CLIENT,
    ROLE_DELIVERY,
    ROLE_SHOP;

    override fun getAuthority(): String {
        return name
    }
}

//enum class Role : GrantedAuthority {
//    ROLE_ADMIN,
//    ROLE_CLIENT,
//    ROLE_DELIVERY,
//    ROLE_SHOP;
//
//    override fun getAuthority(): String {
//        return name
//    }
//}
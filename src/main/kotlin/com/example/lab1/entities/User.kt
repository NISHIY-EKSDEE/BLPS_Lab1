package com.example.lab1.entities

import lombok.Data
import javax.persistence.*

@Entity
@Table(name = "usersT")
@Data
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    @Column(unique = true, nullable = false)
    var username: String = ""

    @Column(nullable = false)
    var password: String = ""

    @Enumerated(EnumType.STRING)
    var role: Role = Role.ROLE_CLIENT
}

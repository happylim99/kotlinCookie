package com.sean.auth.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.sean.auth.util.SpringContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.*

@Entity
class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    @Column
    var name = ""
    @Column(unique = true)
    var email = ""
    @Column
    @JsonIgnore
    var password = ""
//        get() = field
//        set(value) {
//            val bcrypt = SpringContext.getBean(BCryptPasswordEncoder::class.java)
//            field = bcrypt.encode(value)
//        }
}
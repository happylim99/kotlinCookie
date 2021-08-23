package com.sean.auth.repo

import com.sean.auth.entity.AppUser
import org.springframework.data.jpa.repository.JpaRepository

interface AppUserRepo: JpaRepository<AppUser, Long> {
    fun findByEmail(email: String): AppUser
}
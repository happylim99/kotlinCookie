package com.sean.auth.controller

import com.sean.auth.dto.LoginDto
import com.sean.auth.entity.AppUser
import com.sean.auth.service.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
@CrossOrigin
@RequestMapping("/auth")
class AuthController @Autowired constructor(
    private val authSrv: AuthService
) {

    @PostMapping("/register")
    fun register(@RequestBody appUser: AppUser): ResponseEntity<AppUser> {
        return ResponseEntity.ok(authSrv.register(appUser))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto, response: HttpServletResponse):
            ResponseEntity<Any> {
        return ResponseEntity.ok(authSrv.login(loginDto, response))
    }

    @GetMapping("verifyUser")
    fun verifyUser(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        return ResponseEntity.ok(authSrv.verifyUser(jwt))
    }

    @GetMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        return ResponseEntity.ok(authSrv.logout(response))
    }
}
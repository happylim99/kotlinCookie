package com.sean.auth.service

import com.sean.auth.dto.LoginDto
import com.sean.auth.entity.AppUser
import com.sean.auth.model.Message
import com.sean.auth.repo.AppUserRepo
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@Service
class AuthService @Autowired constructor(
    private val userRepo: AppUserRepo,
    private val bcrypt: BCryptPasswordEncoder
) {
    fun register(appUser: AppUser): AppUser {
        appUser.password = bcrypt.encode(appUser.password)
        return userRepo.save(appUser)
    }

    fun login(loginDto: LoginDto, response: HttpServletResponse): Any {
        val user = userRepo.findByEmail(loginDto.email)
        if(user != null && bcrypt.matches(loginDto.password, user.password)) {
//            return user
            val issuer = user.id.toString()
            val jwt = Jwts.builder()
                .setIssuer(issuer)
                .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // a day
                .signWith(SignatureAlgorithm.HS512, toBase64("secret"))
                .compact()

            val cookie = Cookie("jwt", jwt)
            var cookie2 = Cookie("aa", "aa")
            cookie.isHttpOnly = true
            response.addCookie(cookie)
            response.addCookie(cookie2)
            return Message("Success")
        }
        return Message("Invalid credential")
    }

    fun verifyUser(jwt: String?): ResponseEntity<Any> {
        try {
            if(jwt == null) {
                return ResponseEntity.status(401).body(Message("Unauthenticated"))
            }
            val body = Jwts.parser().setSigningKey(toBase64("secret"))
                .parseClaimsJws(jwt).body

            return ResponseEntity.ok(findById(body.issuer.toLong()))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("Unauthenticated"))
        }
    }

    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        var cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        response.addCookie(cookie)
        return ResponseEntity.ok(Message("success"))
    }

    private fun findById(id: Long): Any {
        return userRepo.findById(id)
    }

    private fun toBase64(str: String): String {
        return Base64.getEncoder().encodeToString(str.toByteArray())
    }
}
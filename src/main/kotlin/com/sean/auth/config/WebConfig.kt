package com.sean.auth.config

import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WebConfig: WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(
                "http://localhost:3000",//for react js
                "http://localhost:8080",// for vue
                "http://localhost:4200"// for angular
            )
            .allowCredentials(true)
    }
}
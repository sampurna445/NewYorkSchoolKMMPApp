package com.example.newyorkschoolkmmpapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
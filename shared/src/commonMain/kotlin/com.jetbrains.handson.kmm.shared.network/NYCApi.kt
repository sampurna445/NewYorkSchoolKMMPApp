package com.jetbrains.handson.kmm.shared.network

import SchoolSATsData
import SchoolsListData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NYCApi {

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllSchoolNames(): List<SchoolsListData> {
        return httpClient.get("https://data.cityofnewyork.us/resource/s3k6-pzi2.json").body()
    }

    suspend fun getAllSchoolsSATsData(): List<SchoolSATsData> {
        return httpClient.get("https://data.cityofnewyork.us/resource/f9bf-2cp4.json").body()
    }
}
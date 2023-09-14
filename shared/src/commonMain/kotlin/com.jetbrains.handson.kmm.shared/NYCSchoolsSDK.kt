package com.jetbrains.handson.kmm.shared

import SchoolSATsData
import SchoolsListData
import com.jetbrains.handson.kmm.shared.cache.Database
import com.jetbrains.handson.kmm.shared.cache.DatabaseDriverFactory
import com.jetbrains.handson.kmm.shared.network.NYCApi


class NYCSchoolsSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = NYCApi()

    @Throws(Exception::class)
    suspend fun getAllSchoolNames(forceReload: Boolean): List<SchoolsListData> {
        val cachedSchoolData = database.getAllSchoolNames()
        return if (cachedSchoolData.isNotEmpty() && !forceReload) {
            cachedSchoolData
        } else {
            api.getAllSchoolNames().also {
                database.clearDatabase()
                database.createSchoolData(it)
            }
        }
    }


    @Throws(Exception::class)
    suspend fun getSelectedSchoolSATsScoreData(schoolName: String): SchoolSATsData? {

        val satsData = api.getAllSchoolsSATsData()
        return satsData.find { it.schoolName.uppercase().trim() == schoolName.uppercase().trim() }
    }

}

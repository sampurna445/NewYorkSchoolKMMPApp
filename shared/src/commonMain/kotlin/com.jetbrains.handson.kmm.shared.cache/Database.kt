package com.jetbrains.handson.kmm.shared.cache

import SchoolsListData


internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    //function to clear all the tables in the database in a single SQL transaction:
    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllSchoolData()
        }
    }

    //a function to get a list of all the school names:
    internal fun getAllSchoolNames(): List<SchoolsListData> {
        return dbQuery.selectAllSchoolData(::mapSchoolData).executeAsList()
    }

    private fun mapSchoolData(
        schoolName: String?,
        city: String?,
        dbn: String?
    ): SchoolsListData {
        return SchoolsListData(
            schoolName = schoolName,
            city = city,
            dbn = dbn
        )
    }

    //  function to insert data into the database:
    internal fun createSchoolData(schoolData: List<SchoolsListData>) {
        dbQuery.transaction {
            schoolData.forEach { schoolData ->
                insertSchoolData(schoolData)
            }
        }
    }

    //  function to insert data into the database:
    private fun insertSchoolData(schoolData: SchoolsListData) {

        dbQuery.insertSchoolData(
            schoolName = schoolData.schoolName.toString(),
            city = schoolData.city.toString(),
            dbn = schoolData.dbn.toString()
        )
    }


}
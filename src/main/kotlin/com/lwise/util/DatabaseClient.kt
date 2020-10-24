package com.lwise.util

import io.github.cdimascio.dotenv.dotenv
import java.lang.Exception
import java.net.URI
import java.sql.Connection
import java.sql.DriverManager

class DatabaseClient {
    companion object {
        private fun connect(): Connection {
            Class.forName("org.postgresql.Driver") // for debugging
            val dotenv = dotenv {
                ignoreIfMissing = true
            }
            val dbUri = URI(dotenv["DATABASE_URL"])
            val username = dbUri.userInfo.split(":")[0]
            val password = dbUri.userInfo.split(":")[1]
            val dbUrl = "jdbc:postgresql://" + dbUri.host + ":" + dbUri.port + dbUri.path
            return DriverManager.getConnection(dbUrl, username, password)
        }

        fun <T> query(sqlQueryString: String, transformer: ResultTransformer<T>): T? {
            var result: T? = null
            try {
                connect().createStatement().apply {
                    val resultSet = executeQuery(sqlQueryString)
                    result = transformer.transform(resultSet)
                    close()
                }.close()
            } catch (exception: Exception) {
                logException(this::class.java.name, "Failed to query database", exception)
            }
            return result
        }

        fun update(sqlQueryString: String): Int {
            var rowsUpdated: Int = 0
            try {
                connect().createStatement().apply {
                    rowsUpdated = executeUpdate(sqlQueryString)
                    close()
                }.close()
            } catch (exception: Exception) {
                logException(this::class.java.name, "Failed to update database", exception)
            }
            return rowsUpdated
        }
    }
}

package com.lwise.util

import java.sql.ResultSet

interface ResultTransformer<T> {
    fun transform(resultSet: ResultSet): T
}

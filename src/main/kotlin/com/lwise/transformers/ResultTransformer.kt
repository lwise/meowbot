package com.lwise.transformers

import java.sql.ResultSet

interface ResultTransformer<T> {
    fun transform(resultSet: ResultSet): T
}

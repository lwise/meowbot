package com.lwise.transformers

import com.lwise.types.Table
import java.sql.ResultSet

class TableTransformer : ResultTransformer<Table> {
    override fun transform(resultSet: ResultSet): Table {
        resultSet.next()
        return Table(resultSet.getString("tablename"))
    }
}

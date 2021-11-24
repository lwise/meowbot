package com.lwise.transformers

import com.lwise.types.UserData
import java.sql.ResultSet

class UserDataListTransformer : ResultTransformer<List<UserData>> {
    override fun transform(resultSet: ResultSet): List<UserData> {
        val userList = mutableListOf<UserData>()
        while (resultSet.next()) {
            userList.add(
                UserData(
                    id = resultSet.getLong("id"),
                    userName = resultSet.getString("username"),
                    chaoticPoints = resultSet.getInt("chaotic_points"),
                    lawfulPoints = resultSet.getInt("lawful_points"),
                    goodPoints = resultSet.getInt("good_points"),
                    evilPoints = resultSet.getInt("evil_points"),
                    fishPoints = resultSet.getInt("fish_points")
                )
            )
        }
        return userList
    }
}

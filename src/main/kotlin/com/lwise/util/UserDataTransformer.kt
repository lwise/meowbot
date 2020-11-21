package com.lwise.util

import com.lwise.types.UserData
import java.sql.ResultSet

class UserDataTransformer : ResultTransformer<UserData?> {
    override fun transform(resultSet: ResultSet): UserData? {
        return if (!resultSet.next()) null else
            UserData(
                id = resultSet.getLong("id"),
                userName = resultSet.getString("username"),
                chaoticPoints = resultSet.getInt("chaotic_points"),
                lawfulPoints = resultSet.getInt("lawful_points"),
                goodPoints = resultSet.getInt("good_points"),
                evilPoints = resultSet.getInt("evil_points"),
                fishPoints = resultSet.getInt("fish_points")
            )
    }
}

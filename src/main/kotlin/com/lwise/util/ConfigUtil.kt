package com.lwise.util

import java.io.FileInputStream
import java.util.Properties

object ConfigUtil {
    var guildId: String
    var alignmentRoles: MutableMap<String, String>
    init {
        val prop = Properties()
        prop.load(FileInputStream("config.properties"))
        guildId = prop.getProperty("guildId")
        alignmentRoles = mutableMapOf()
        alignmentRoles["Chaotic Evil"] = prop.getProperty("chaoticEvil")
        alignmentRoles["Chaotic Good"] = prop.getProperty("chaoticGood")
        alignmentRoles["Chaotic Neutral"] = prop.getProperty("chaoticNeutral")
        alignmentRoles["True Neutral"] = prop.getProperty("trueNeutral")
        alignmentRoles["Lawful Neutral"] = prop.getProperty("lawfulNeutral")
        alignmentRoles["Lawful Good"] = prop.getProperty("lawfulGood")
        alignmentRoles["Lawful Evil"] = prop.getProperty("lawfulEvil")
        alignmentRoles["Neutral Good"] = prop.getProperty("neutralGood")
        alignmentRoles["Neutral Evil"] = prop.getProperty("neutralEvil")
    }
}

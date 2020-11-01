package com.lwise.util

import java.io.FileInputStream
import java.util.Properties

object ConfigUtil {
    val guildId: String
    val alignmentRoles: MutableMap<String, String>
    val emoji: MutableMap<String, String>
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

        emoji = mutableMapOf()
        emoji["hello"] = prop.getProperty("hello")
        emoji["happyCat"] = prop.getProperty("happyCat")
        emoji["crying"] = prop.getProperty("crying")
    }
}

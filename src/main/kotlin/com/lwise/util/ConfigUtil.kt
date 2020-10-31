package com.lwise.util

import java.io.FileInputStream
import java.util.Properties

object ConfigUtil {
    var guildId: String
    init {
        val prop = Properties()
        prop.load(FileInputStream("config.properties"))
        guildId = prop.getProperty("guildId")
    }
}

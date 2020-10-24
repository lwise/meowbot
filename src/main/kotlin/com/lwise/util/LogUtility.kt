package com.lwise.util

import org.slf4j.LoggerFactory
import java.lang.Exception

fun log(className: String, message: String) {
    val logger = LoggerFactory.getLogger(className)
    logger.debug(message)
}

fun logException(className: String, message: String, exception: Exception) {
    val logger = LoggerFactory.getLogger(className)
    logger.error(className, message, exception)
}

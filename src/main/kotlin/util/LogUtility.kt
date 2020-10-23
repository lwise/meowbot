package util

import org.slf4j.LoggerFactory

fun log(className: String, message: String) {
    val logger = LoggerFactory.getLogger(className)
    logger.debug(message)
}
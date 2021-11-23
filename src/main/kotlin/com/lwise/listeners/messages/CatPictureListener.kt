package com.lwise.listeners.messages

import com.lwise.clients.CatApiClient

class CatPictureListener : MessageListener {

    override val regexString = "m!pic"

    override fun getResponseMessage(): String {
        return CatApiClient.getRandomCatImageUrl()
    }
}

package com.lwise.clients

import io.github.cdimascio.dotenv.dotenv
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import java.net.URL

// We are using Fixie to give Meowbot a static IP so that it isn't constantly banned from Youtube
// https://devcenter.heroku.com/articles/fixie

object FixieClient {
    val dotenv = dotenv {
        ignoreIfMissing = true
    }

    val fixieUser: String
    val fixiePassword: String
    val credentialsProvider: BasicCredentialsProvider
    val proxyHost: HttpHost

    init {
        val fixieUrlString: String = dotenv["FIXIE_URL"] ?: System.getenv("FIXIE_URL")
        val fixieUrl = URL(fixieUrlString)
        fixieUser = fixieUrl.userInfo.split(":")[0]
        fixiePassword = fixieUrl.userInfo.split(":")[1]
        val fixieHost = fixieUrl.host
        val fixiePort = fixieUrl.port
        credentialsProvider = BasicCredentialsProvider().apply {
            setCredentials(AuthScope(fixieHost, fixiePort), UsernamePasswordCredentials(fixieUser, fixiePassword))
        }
        proxyHost = HttpHost(fixieHost, fixiePort)
    }

    class ProxyAuthenticator : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request {
            val credential = Credentials.basic(fixieUser, fixiePassword)
            return response.request.newBuilder().header("Proxy-Authorization", credential).build()
        }
    }
}

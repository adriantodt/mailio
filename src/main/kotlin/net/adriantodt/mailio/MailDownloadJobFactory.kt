package net.adriantodt.mailio

import java.util.*
import javax.mail.Session

class MailDownloadJobFactory(
    private val protocol: Protocol,
    host: String,
    port: String,
    private val username: String,
    private val password: String
) {
    private val properties = Properties().apply {
        this["mail.$protocol.host"] = host
        this["mail.$protocol.port"] = port
        this["mail.$protocol.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
        this["mail.$protocol.socketFactory.fallback"] = "false"
        this["mail.$protocol.socketFactory.port"] = port
    }

    fun create(): MailDownloadJob {
        val session = Session.getDefaultInstance(properties)
        val store = session.getStore(protocol.toString())
        store.connect(username, password)

        return MailDownloadJob(store)
    }

    enum class Protocol {
        POP3, IMAP;

        override fun toString() = super.toString().toLowerCase()
    }
}


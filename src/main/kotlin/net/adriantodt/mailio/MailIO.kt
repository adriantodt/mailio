package net.adriantodt.mailio

import net.adriantodt.mailio.MailDownloadJobFactory.Protocol.IMAP

fun main() {
    MailDownloadJobFactory(
        IMAP, "imap.example.com", "993", "example@example.com", "example_password"
    ).create().run()
}

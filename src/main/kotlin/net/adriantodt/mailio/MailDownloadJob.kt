package net.adriantodt.mailio

import java.io.File
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME
import javax.mail.*
import javax.mail.Flags.Flag.DELETED

class MailDownloadJob(private val store: Store) : Runnable {
    override fun run() {
        val inbox = store.getFolder("INBOX")
        inbox.open(Folder.READ_WRITE)

        for (message in inbox.messages) {
            println(message.from.joinToString())
            val multipart = message.content as? Multipart ?: continue

            for (i in 0 until multipart.count) {
                val part = multipart.getBodyPart(i)

                if (part.disposition equalsIgnoreCase Part.ATTACHMENT && !part.fileName.isNullOrBlank()) {
                    val name = listOfNotNull(
                        OffsetDateTime.now().format(RFC_1123_DATE_TIME),
                        part.fileName?.let { File(it).extension }
                    ).joinToString(".")

                    val file = File("Downloaded/", name)
                    file.parentFile.mkdirs()
                    file.createNewFile()

                    part.inputStream.use { file.outputStream().use(it::transferTo) }
                }
                message.setFlag(DELETED, true)
            }
        }

        inbox.close(true)
        store.close()
    }
}

private infix fun String?.equalsIgnoreCase(other: String?) = equals(other, true)
